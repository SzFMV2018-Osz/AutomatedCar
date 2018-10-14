package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.model.Classes.Car;

/**
 * Steering system is responsible for the turning of the car.
 */
public class SteeringSystem extends SystemComponent {
    private double angularSpeed = 0;
    private Car car;
    private int turningCircle;

    /**
     * Creates a steering system that connects the Virtual Function Bus
     *
     * @param virtualFunctionBus {@link VirtualFunctionBus} used to connect {@link SystemComponent}s
     */
    public SteeringSystem(VirtualFunctionBus virtualFunctionBus) {

        super(virtualFunctionBus);
        car = new Car(50, 50, "car_1_red.png");
        turningCircle = 0;
    }

    @Override
    public void loop() {
        // calculateTurningCircle();
    }

    public void calculateTurningCircle(int turningDegree) {
        turningCircle = (int) (car.getHeight() / Math.tan((Math.toRadians((double) turningDegree)) + car.getWidth()));
    }

    public double getAngularSpeed() {
        return this.angularSpeed;
    }

    public Car getCar() {
        return car;
    }

    public int getTurningCircle() {
        return turningCircle;
    }
}
