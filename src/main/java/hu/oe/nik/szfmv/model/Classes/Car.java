package hu.oe.nik.szfmv.model.Classes;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SteeringSystem;

public class Car extends Dinamic {
    private SteeringSystem steeringSystem;
    /**
     * Creates an object of the virtual world on the given coordinates with the given image.
     *
     * @param x             the initial x coordinate of the object
     * @param y             the initial y coordinate of the object
     * @param imageFileName the filename of the image representing the object in the virtual world
     */
    public Car(int x, int y, String imageFileName) {
        super(x, y, imageFileName);
        steeringSystem = new SteeringSystem(new VirtualFunctionBus());
    }

    public Car(int x, int y, String imageFileName, double m11, double m12, double m21, double m22) {
        super(x, y, imageFileName, m11, m12, m21, m22);
        steeringSystem = new SteeringSystem(new VirtualFunctionBus());
    }

    public SteeringSystem getSteeringSystem() {
        return steeringSystem;
    }
}