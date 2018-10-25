package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.powertrain.PowertrainPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.steering.SteeringPacket;
import hu.oe.nik.szfmv.common.Resistences;
import hu.oe.nik.szfmv.common.exceptions.NegativeNumberException;
import hu.oe.nik.szfmv.common.Vector;

/**
 * Powertrain system is responsible for the movement of the car.
 */
public class PowertrainSystem extends SystemComponent {
    public static final int MAX_RPM = 6000;
    public static final int MIN_RPM = 750;
    private static final int SECONDS_IN_HOUR = 3600;
    private static final int METERS_IN_KILOMETER = 1000;

    private static final double GEAR_RATIOS = 1.3;
    private static final int PERCENTAGE_DIVISOR = 100;
    private static final int SAMPLE_WEIGHT = 1000;
    private static final int ENGINE_BRAKE_TORQUE = 70;
    private static final double MAX_BRAKE_DECELERATION = 25;
    private static final double MAX_FORWARD_SPEED = 5.0605;
    private static final double MIN_FORWARD_SPEED = 4.3888;
    private static final double MAX_REVERSE_SPEED = -5.278;
    private static final double MIN_REVERSE_SPEED = -3.3888;
    private static final double MAX_PERCENT = 20d;
    private static final int MAX_SPEED_IN_METER_PER_SECUNDUM = 10;
    private Vector vector;
    private double speed;
    private int currentRPM;
    private int actualRPM;
    private int gasPedal;
    private int brakePedal;
    private String gearState;

    /**
     * Creates a powertrain system that connects the Virtual Function Bus
     *
     * @param virtualFunctionBus {@link VirtualFunctionBus} used to connect {@link SystemComponent}s
     */
    public PowertrainSystem(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);

        this.virtualFunctionBus.powertrainPacket = new PowertrainPacket();
        //this.virtualFunctionBus.steeringPacket = new SteeringPacket();
        this.currentRPM = MIN_RPM;
        this.actualRPM = this.currentRPM;
        vector = new Vector(0,0);
    }

    /**
     * Calculate the magnitude of the given vector
     *
     * @param vector given vector
     * @return the magnitude
     */
    private static double calculateVectorMagnitude(Vector vector) {
        return Math.sqrt(Math.pow(vector.getX(), 2) + Math.pow(vector.getY(), 2));
    }

    @Override
    public void loop() {
        gearState = virtualFunctionBus.samplePacket.getGear();
        try {
            actualRPM = calculateActualRpm(virtualFunctionBus.samplePacket.getGaspedalPosition());
        } catch (NegativeNumberException e) {
            e.printStackTrace();
        }
        doPowerTrain();
    }

    /**
     * Do Power Train
     */
    private void doPowerTrain() {
        double acceleration = calculateSpeedDifference();

        // gearState : R(1), P(2), N(3), D(4)
        switch (gearState) {
            case "R":
                reverse(acceleration);
                break;
            case "P":
                park();
                break;
            case "N":
                noGear(acceleration);
                break;
            case "D":
                drive(acceleration);
                break;
            default:
                break;
        }
    }

    /**
     * Set speed when gearstate is P - park
     */
    private void park() {
        speed = 0;
        calculateNewVector();
    }

    /**
     * Set speed when gearstate is D - drive
     *
     * @param acceleration acceleration
     */
    private void drive(double acceleration) {
        if (brakePedal == 0) {
            if (speed < MAX_SPEED_IN_METER_PER_SECUNDUM){ //|| (speed > MIN_FORWARD_SPEED)) {
                updateChanges(acceleration);
            }
        } else {
            if (speed > 0) {
                updateChanges(acceleration);
            }
            if (speed < 0) {
                speed = 0;
                //calculateNewVector();
            }
        }
    }

    /**
     * Set speed when gearstate is N - no gear
     *
     * @param acceleration acceleration
     */
    private void noGear(double acceleration) {
        if (brakePedal > 0) {
            if (Math.abs(speed) > 0) {
                updateChanges(acceleration);
            }
            if (speed < 0) {
                speed = 0;
                calculateNewVector();
            }
        }
    }

    /**
     * Set speed when gearstate is R - reverse
     *
     * @param acceleration acceleration
     */
    private void reverse(double acceleration) {
        if (brakePedal == 0) {
            if (speed < MAX_REVERSE_SPEED){ // || (acceleration > 0 && speed < MIN_REVERSE_SPEED)) {
                updateChanges(acceleration);
                this.virtualFunctionBus.steeringPacket.setTurningAngle(this.virtualFunctionBus.steeringPacket.getTurningAngle()+180);
            }
        } else {
            if (speed < 0) {
                speed = 0;
            }
            if (speed > 0) {
               // speed = 0;
                updateChanges(acceleration);
                this.virtualFunctionBus.steeringPacket.setTurningAngle(this.virtualFunctionBus.steeringPacket.getTurningAngle()+180);
            }
        }
    }

    public double getSpeed() {
        return speed;
    }

    public Vector getVector() {
        return vector;
    }

    /**
     * Calculate the actual rpm of the engine
     *
     * @param gasPedalPosition position of the gaspedal
     * @return the actual rpm
     * @throws NegativeNumberException the input value must be a non-negative number
     */
    public int calculateActualRpm(int gasPedalPosition) throws NegativeNumberException {
        if (gasPedalPosition < 0) {
            throw new NegativeNumberException("The position of the gas pedal must be a non-negative number");
        }
        if (gasPedalPosition == 0) {
            int actual = MIN_RPM;
            this.virtualFunctionBus.powertrainPacket.setRpm(actual);
            return actual;
        } else {
            double multiplier = ((double) (MAX_RPM - MIN_RPM) / PERCENTAGE_DIVISOR);
            int actual = (int) ((gasPedalPosition * multiplier) + this.currentRPM);
            this.virtualFunctionBus.powertrainPacket.setRpm(actual);
            return actual;
        }
    }

    /**
     * Gets the magnitude of the car's velocity vector
     *
     * @return the magnitude
     */
    private double getVelocityVectorMagnitude() {
        return PowertrainSystem.calculateVectorMagnitude(this.vector);
    }

    /**
     * Gets the magnitude of the air resistance
     *
     * @return the magnitude
     */
    private double getAirResistanceMagnitude() {
        return PowertrainSystem.calculateVectorMagnitude(Resistences.calculateAirResistance(this.vector));
    }

    /**
     * Gets the magnitude of the rolling resistance
     *
     * @return the magnitude
     */
    private double getRollingResistanceMagnitude() {
        return PowertrainSystem.calculateVectorMagnitude(Resistences.calulateRollingResistance(
                this.vector));
    }

    /**
     * Calculate the difference between the actual and the increased speed
     *
     * @return the speed delta
     */
    private double calculateSpeedDifference() {
        double speedDelta;
        int brakePedalPosition = this.virtualFunctionBus.samplePacket.getBreakpedalPosition();
        int gasPedalPosition = this.virtualFunctionBus.samplePacket.getGaspedalPosition();

        speedDelta = (double)gasPedalPosition/100-(double)brakePedalPosition/100;

        if(this.speed + speedDelta < MAX_SPEED_IN_METER_PER_SECUNDUM && this.speed + speedDelta>0)
            return speedDelta;
        return 0;
    }

    /**
     * Change the current speed by the speed delta
     *
     * @param speedDelta The difference between the old and the new speed
     */
    private void updateChanges(double speedDelta) {

        this.speed += speedDelta;
        this.currentRPM = this.actualRPM;

        calculateNewVector();
        this.virtualFunctionBus.powertrainPacket.setSpeed(this.speed);
        this.virtualFunctionBus.powertrainPacket.setVector(this.vector);
    }

    /**
     * Calculate the new velocity vector
     */
    public void calculateNewVector() {
        double newY = Math.sin((double)this.virtualFunctionBus.steeringPacket.getTurningAngle())*calculateSpeedDifference();
        double newX = Math.cos((double)this.virtualFunctionBus.steeringPacket.getTurningAngle())*calculateSpeedDifference();
        Vector vectorWithoutResistence = new Vector(newX, newY);
        this.vector = new Vector(vectorWithoutResistence.getY() +Resistences.calculateAirResistance(vectorWithoutResistence).getY()
                +Resistences.calulateRollingResistance(vectorWithoutResistence).getY(),
                vectorWithoutResistence.getX()+Resistences.calculateAirResistance(vectorWithoutResistence).getX()
                +Resistences.calulateRollingResistance(vectorWithoutResistence).getX());
    }
}

