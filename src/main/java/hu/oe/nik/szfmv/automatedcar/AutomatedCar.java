package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.carpacket.CarPacket;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.PowertrainSystem;
import hu.oe.nik.szfmv.model.Classes.Car;

import java.awt.*;
import java.awt.geom.Point2D;

public class AutomatedCar extends Car {
    private final VirtualFunctionBus virtualFunctionBus = new VirtualFunctionBus();
    private PowertrainSystem powertrainSystem;

    /**
     * Creates an object of the virtual world on the given coordinates with the given image.
     *
     * @param x             the initial x coordinate of the object
     * @param y             the initial y coordinate of the object
     * @param imageFileName the filename of the image representing the object in the virtual world
     */
    public AutomatedCar(int x, int y, String imageFileName) {
        super(x, y, imageFileName);

        setCarPacket();

        powertrainSystem = new PowertrainSystem(virtualFunctionBus);

        new Driver(virtualFunctionBus);
    }

    public VirtualFunctionBus getVirtualFunctionBus() {
        return virtualFunctionBus;
    }

    private void setCarPacket() {
        CarPacket carPacket = new CarPacket();
        carPacket.setCarWidth(width);
        carPacket.setCarHeigth(height);
        carPacket.setRotation(rotation);
        carPacket.setxPosition(x);
        carPacket.setyPosition(y);
        virtualFunctionBus.carPacket = carPacket;
    }

    /**
     * Driving the Car
     */
    public void drive() {
        calculatePositionAndOrientation();
        virtualFunctionBus.loop();
    }

    private void calculatePositionAndOrientation() {
        double carSpeed = virtualFunctionBus.powertrainPacket.getSpeed();
        double angularSpeed = 0;
        final double fps = 1;
        final int threeQuarterCircle = 270;
        try {
            angularSpeed = SteeringHelpers.getSteerAngle(-this.virtualFunctionBus.samplePacket.getWheelPosition());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setCarPositionAndOrientation(carSpeed, angularSpeed);
    }

    private void setCarPositionAndOrientation(double carSpeed, double angularSpeed) {
        final int threeQuarterCircle = 270;
        double carHeading = Math.toRadians(threeQuarterCircle + rotation);
        double halfWheelBase = (double) height / 2;

        Point2D carPosition = new Point2D.Double(virtualFunctionBus.carPacket.getxPosition(), virtualFunctionBus.carPacket.getyPosition());
        Object[] carPositionAndHeading = SteeringHelpers.getCarPositionAndCarHead(carPosition, carHeading, carSpeed,
                angularSpeed, new int[] { width, height });
        if (carPositionAndHeading[0].getClass() == Point2D.Double.class) {
            carPosition = new Point2D.Double(((Point2D) carPositionAndHeading[0]).getX(),
                    ((Point2D) carPositionAndHeading[0]).getY());
        }

        if (carPositionAndHeading[1].getClass() == Double.class) {
            carHeading = (Double) carPositionAndHeading[1];
        }

        this.setX((int)Math.round(carPosition.getX() - (double) width / 2));
        this.setY((int)Math.round(carPosition.getY() - halfWheelBase));
        rotation = (float) -Math.toDegrees(Math.toRadians(threeQuarterCircle) - carHeading);

        virtualFunctionBus.carPacket.setxPosition(this.getX());
        virtualFunctionBus.carPacket.setyPosition(this.getY());
    }
}