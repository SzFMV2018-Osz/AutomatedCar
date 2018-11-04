package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.carpacket.CarPacket;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.PowertrainSystem;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SteeringSystem;
import hu.oe.nik.szfmv.model.Classes.Car;

import java.awt.*;

public class AutomatedCar extends Car {
    private final VirtualFunctionBus virtualFunctionBus = new VirtualFunctionBus();
    private PowertrainSystem powertrainSystem;
    private SteeringSystem steeringSystem;

    /**
     * Creates an object of the virtual world on the given coordinates with the given image.
     *
     * @param x             the initial x coordinate of the object
     * @param y             the initial y coordinate of the object
     * @param imageFileName the filename of the image representing the object in the virtual world
     */
    public AutomatedCar(int x, int y, String imageFileName) {
        super(x, y, imageFileName);

        steeringSystem = new SteeringSystem(virtualFunctionBus);
        powertrainSystem = new PowertrainSystem(virtualFunctionBus, steeringSystem);

        new Driver(virtualFunctionBus);

        setCarPacket();
    }

    public VirtualFunctionBus getVirtualFunctionBus() {
        return virtualFunctionBus;
    }

    private void setCarPacket() {
        CarPacket carPacket = new CarPacket();
        carPacket.setCarWidth(width);
        carPacket.setCarHeigth(height);
        virtualFunctionBus.carPacket = carPacket;
    }

    /**
     * Driving the Car
     */
    public void drive() {
        virtualFunctionBus.loop();
        calculatePositionAndOrientation();
    }

    private void calculatePositionAndOrientation() {
        Point movingVector = powertrainSystem.getVector();
        double angularSpeed = steeringSystem.getTurningCircle();

        x -= movingVector.getY();
        y -= movingVector.getX();

        virtualFunctionBus.carPacket.setxPosition(x);
        virtualFunctionBus.carPacket.setyPosition(y);

        rotation += angularSpeed;
    }

}