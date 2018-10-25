package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.steering.SteeringPacket;

/**
 * Steering system is responsible for the turning of the car.
 */
public class SteeringSystem extends SystemComponent {
    private int turningCircle;
    private  int turningAngle;

    /**
     * Creates a steering system that connects the Virtual Function Bus
     *
     * @param virtualFunctionBus {@link VirtualFunctionBus} used to connect {@link SystemComponent}s
     */
    public SteeringSystem(VirtualFunctionBus virtualFunctionBus) {

        super(virtualFunctionBus);
        this.virtualFunctionBus.steeringPacket = new SteeringPacket();
        turningCircle = 0;
        turningAngle = 0;
    }

    @Override
    public void loop() {
        calculateTurningCircle(virtualFunctionBus.samplePacket.getWheelPosition());
    }


    /**
     * @param turningDegree Degree of the steering wheel
     */
    public void calculateTurningCircle(int turningDegree) {
        if(turningDegree != 0){
            turningAngle += turningDegree;
            this.virtualFunctionBus.steeringPacket.setTurningAngle(turningAngle);
        }
        if(turningAngle >= 360){
            turningAngle = 0;
            this.virtualFunctionBus.steeringPacket.setTurningAngle(turningAngle);
            //this.virtualFunctionBus.carPacket.setxPosition(this.virtualFunctionBus.carPacket.getxPosition()+turningAngle);
        }
    }

    public int getTurningCircle() {
        return turningCircle;
    }

    public int getTurningAngle() {
        return turningAngle;
    }
}
