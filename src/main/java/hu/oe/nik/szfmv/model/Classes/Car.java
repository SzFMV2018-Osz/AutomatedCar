package hu.oe.nik.szfmv.model.Classes;


import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.carpacket.CarPacket;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.PowertrainSystem;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SteeringSystem;
import hu.oe.nik.szfmv.common.DynamicMoving;
import hu.oe.nik.szfmv.model.Interfaces.ICollidable;
import java.awt.*;

public abstract class Car extends Dynamic implements ICollidable {
    private final VirtualFunctionBus virtualFunctionBus = new VirtualFunctionBus();
    private PowertrainSystem powertrainSystem;
    private SteeringSystem steeringSystem;
    private DynamicMoving dynamicMoving;

public abstract class Car extends Dynamic implements ICollidable {

    /**
     * Creates an object of the virtual world on the given coordinates with the given image.
     *
     * @param x             the initial x coordinate of the object
     * @param y             the initial y coordinate of the object
     * @param imageFileName the filename of the image representing the object in the virtual world
     */
    public Car(int x, int y, String imageFileName) {
        super(x, y, imageFileName);

        steeringSystem = new SteeringSystem(virtualFunctionBus);
        dynamicMoving = new DynamicMoving(steeringSystem);
        powertrainSystem = new PowertrainSystem(virtualFunctionBus, dynamicMoving);

        new Driver(virtualFunctionBus);

        setCarPacket();
    }

    /**
     *
     * @param x coordinate
     * @param y coordinate
     * @param imageFileName the name of the imagefile used for this type
     * @param m11 transformation
     * @param m12 transformation
     * @param m21 transformation
     * @param m22 transformation
     */
    public Car(int x, int y, String imageFileName, double m11, double m12, double m21, double m22) {
        super(x, y, imageFileName, m11, m12, m21, m22);
        steeringSystem = new SteeringSystem(virtualFunctionBus);
        dynamicMoving = new DynamicMoving(steeringSystem);
        powertrainSystem = new PowertrainSystem(virtualFunctionBus, dynamicMoving);

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
        Point movingVector = powertrainSystem.getDynamicMoving().getVector();
        double angularSpeed = steeringSystem.getTurningCircle();

        x -= movingVector.getY();
        y -= movingVector.getX();

        virtualFunctionBus.carPacket.setxPosition(x);
        virtualFunctionBus.carPacket.setyPosition(y);

        rotation += angularSpeed;
    }

}

