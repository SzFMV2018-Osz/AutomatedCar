package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;

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
        //turningCircle = (int) (virtualFunctionBus.carPacket.getCarHeigth()
          //      / Math.tan((Math.toRadians((double) turningDegree))
            //    + virtualFunctionBus.carPacket.getCarWidth()));
        if(turningDegree == 0)
            turningCircle = 0;
       // else turningCircle = (int)(virtualFunctionBus.carPacket.getCarHeigth()/Math.sqrt(2-2*Math.cos(2*(double)turningDegree)/16));
        else{
            turningCircle = turningDegree;
            turningAngle = turningDegree;
        }
    }

    public int getTurningCircle() {
        return turningCircle;
    }

    public int getTurningAngle() {
        return turningAngle;
    }
}
