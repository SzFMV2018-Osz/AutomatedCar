package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.powertrain.PowertrainPacket;
import hu.oe.nik.szfmv.common.Resistences;
import hu.oe.nik.szfmv.common.exceptions.NegativeNumberException;

import java.awt.*;

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
    private static final double SAMPLE_WIND_RESISTANCE = 1.5;
    private static final int ENGINE_BRAKE_TORQUE = 70;
    private static final double MAX_BRAKE_DECELERATION = 25;
    private static final double MAX_FORWARD_SPEED = 10;
    private static final double MIN_FORWARD_SPEED = 4.3888;
    private static final double MAX_REVERSE_SPEED = -10.278;
    private static final double MIN_REVERSE_SPEED = -3.3888;
    private static final double MAX_PERCENT = 20d;
    private static final double MAX_WHEEL_ABS = 100;
    private static final double MAX_TURNING_ANGLE_ABS = 20;
    private double speed;
    private int currentRPM;
    private int actualRPM;
    private int gasPedal;
    private int brakePedal;
    private String gearState;
    private boolean isReverse;
    private Point vector;
    private  int turningAngle;
    private int steeringWheel;

    /**
     * Creates a powertrain system that connects the Virtual Function Bus
     *
     * @param virtualFunctionBus {@link VirtualFunctionBus} used to connect {@link SystemComponent}s
     */
    public PowertrainSystem(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);

        this.virtualFunctionBus.powertrainPacket = new PowertrainPacket();

        this.currentRPM = MIN_RPM;
        this.actualRPM = this.currentRPM;
        this.vector = new Point(0, 0);
    }

    /**
     * Calculate the magnitude of the given vector
     *
     * @param vector given vector
     * @return the magnitude
     */
    private static double calculateVectorMagnitude(Point vector) {
        return Math.sqrt(Math.pow(vector.getX(), 2) + Math.pow(vector.getY(), 2));
    }

    @Override
    public void loop() {
        this.gearState = this.virtualFunctionBus.samplePacket.getGear();
        this.brakePedal = this.virtualFunctionBus.samplePacket.getBreakpedalPosition();
        this.gasPedal = this.virtualFunctionBus.samplePacket.getGaspedalPosition();
        this.steeringWheel = this.virtualFunctionBus.samplePacket.getWheelPosition();

        try {
            this.actualRPM = calculateActualRpm(this.gasPedal);
        } catch (NegativeNumberException e) {
            e.printStackTrace();
        }
 
        calculateTurningAngle(virtualFunctionBus.samplePacket.getWheelPosition());
        doPowerTrain();
    }

    /**
     * Do Power Train
     */
    private void doPowerTrain() {
        double speedDifference = calculateSpeedDifference();

        // gearState : R(1), P(2), N(3), D(4)
        switch (gearState) {
            case "R":
                this.isReverse = true;
                reverse(speedDifference);

                break;
            case "P":
                park();
                
                break;
            case "N":
                noGear(speedDifference);

                break;
            case "D":
                this.isReverse = false;
                drive(speedDifference);

                break;
            default:
                break;
        }
    }

    /**
     * Set speed when gearstate is P - park
     */
    private void park() {
        stopImmediately();
    }

    /**
     * Set speed when gearstate is D - drive
     *
     * @param acceleration acceleration
     */
    private void drive(double acceleration) {
        if (this.brakePedal == 0) {
            if (acceleration > 0 && this.speed < MAX_FORWARD_SPEED ||
                acceleration < 0 && this.speed > MIN_FORWARD_SPEED) {
                updateChanges(acceleration);
            }
        } else {
            if (this.speed > 0) {
                updateChanges(acceleration);
            }

            if (this.speed < 0) {
                stopImmediately();
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
            if (this.speed < 0) {
                stopImmediately();
            }
        }
    }

    /**
     * Set speed when gearstate is R - reverse
     *
     * @param acceleration acceleration
     */
    private void reverse(double acceleration) {
        if (this.brakePedal == 0) {
            if (acceleration < 0 && (this.speed > MAX_REVERSE_SPEED) ||
                acceleration > 0 && this.speed < MIN_REVERSE_SPEED) {
                updateChanges(acceleration);
            }

        } else {
            if (this.speed < 0) {
                updateChanges(acceleration);
            }

            if (this.speed > 0) {
                stopImmediately();
            }
        }
    }

    public double getSpeed() {
        return speed;
    }

    public Point getVector() {
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
        return PowertrainSystem.calculateVectorMagnitude(getVector());
    }

    /**
     * Gets the magnitude of the air resistance
     *
     * @return the magnitude
     */
    private double getAirResistanceMagnitude() {
        return PowertrainSystem.calculateVectorMagnitude(Resistences.calculateAirResistance(
                getVector()));
    }

    /**
     * Gets the magnitude of the rolling resistance
     *
     * @return the magnitude
     */
    private double getRollingResistanceMagnitude() {
        return PowertrainSystem.calculateVectorMagnitude(Resistences.calulateRollingResistance(
                getVector()));
    }

    /**
     * Calculate the difference between the actual and the increased speed
     *
     * @return the speed delta
     */
    private double calculateSpeedDifference() {
        double speedDelta;

        double isReverseDouble = isReverse ? -1 : 1;

        // Acceleration.
        if (this.actualRPM > this.currentRPM) {
            speedDelta = isReverseDouble * (this.actualRPM * GEAR_RATIOS / (SAMPLE_WEIGHT * SAMPLE_WIND_RESISTANCE));
        } 
        // Braking.
        else if (this.brakePedal > 0) {
            speedDelta = -1 * isReverseDouble * ((MAX_BRAKE_DECELERATION / (double) PERCENTAGE_DIVISOR) * this.brakePedal);
        } 
        // Slowing down.
        else {
            speedDelta = -1 * isReverseDouble * (double) ENGINE_BRAKE_TORQUE * SAMPLE_WIND_RESISTANCE / (double) PERCENTAGE_DIVISOR;
        }

        return speedDelta;
    }

    /**
     * Change the current speed by the speed delta
     *
     * @param speedDelta The difference between the old and the new speed
     */
    private void updateChanges(double speedDelta) {
        this.speed += speedDelta;
        this.currentRPM = this.actualRPM;
        calculateNewVelocityVector();

        this.virtualFunctionBus.powertrainPacket.setSpeed(this.speed);
    }

    /**
     * Stops the car immediately.
     */
    private void stopImmediately() {
        this.speed = 0;
        this.currentRPM = this.actualRPM;
        calculateNewVelocityVector();

        this.virtualFunctionBus.powertrainPacket.setSpeed(this.speed);
    }
    
    /**
     * Calculate the new velocity vector
     */
    private void calculateNewVelocityVector() {
        double road = (this.speed / SECONDS_IN_HOUR) * METERS_IN_KILOMETER;
        int newY = (int) (Math.sin((double) this.turningAngle) * road);
        int newX = (int) (Math.cos((double) this.turningAngle) * road);
        vector = new Point(newX, newY);
    }

    public void calculateTurningAngle(int wheelPosition) {
        this.turningAngle = (int) Math.round(((double) this.steeringWheel / MAX_WHEEL_ABS) * MAX_TURNING_ANGLE_ABS);
    }

    public int getTurningAngle() {
        return turningAngle;
    }
}

