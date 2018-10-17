package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;

/**
 * Steering system is responsible for the turning of the car.
 */
public class SteeringSystem extends SystemComponent {
    private int turningCircle;

    /**
     * Creates a steering system that connects the Virtual Function Bus
     *
     * @param virtualFunctionBus {@link VirtualFunctionBus} used to connect {@link SystemComponent}s
     */
    public SteeringSystem(VirtualFunctionBus virtualFunctionBus) {

        super(virtualFunctionBus);
        turningCircle = 0;
    }

    @Override
    public void loop() {
        calculateTurningCircle(virtualFunctionBus.inputPositionPacket.getSteeringWheelPosition());
    }


    /**
     * @param turningDegree Degree of the steering wheel
     */
    public void calculateTurningCircle(int turningDegree) {
        turningCircle = (int) (virtualFunctionBus.carPacket.getCarWidth()
                / Math.tan((Math.toRadians((double) turningDegree))
                + virtualFunctionBus.carPacket.getCarHeigth()));
    }

    public int getTurningCircle() {
        return turningCircle;
    }
}
