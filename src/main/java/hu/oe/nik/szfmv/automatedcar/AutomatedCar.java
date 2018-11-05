package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.carpacket.CarPacket;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.PowertrainSystem;
import hu.oe.nik.szfmv.model.Classes.Car;

import java.awt.geom.Point2D;

public class AutomatedCar extends Car {
    private static final int THREE_QUARTER_CIRCLE = 270;

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

    /**
     * Calculates the position and the orientation of the car.
     */
    private void calculatePositionAndOrientation() {
        double carSpeed = this.powertrainSystem.getSpeed();
        double steeringAngle = 0;
        double carHeading = Math.toRadians(THREE_QUARTER_CIRCLE + rotation);
        double halfWheelBase = (double) height / 2;
        
        try {
            steeringAngle = SteeringHelpers.getSteerAngle(-this.virtualFunctionBus.samplePacket.getWheelPosition());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Point2D position = calculateNewPosition(carSpeed, steeringAngle, carHeading);

        this.setX((int)Math.round(position.getX() - (double) width / 2));
        this.setY((int)Math.round(position.getY() - halfWheelBase));

        virtualFunctionBus.carPacket.setxPosition(this.getX());
        virtualFunctionBus.carPacket.setyPosition(this.getY());
    }

    /**
     * Calculates the new position based on the speed and steering angle.
     * @param carSpeed Speed of the car.
     * @param steeringAngle Steering angle.
     * @param carHeading Car heading.
     * @return New position of the car.
     */
    private Point2D calculateNewPosition(double carSpeed, double steeringAngle, double carHeading) {
        Point2D position = new Point2D.Double(
            virtualFunctionBus.carPacket.getxPosition(), 
            virtualFunctionBus.carPacket.getyPosition());
        Object[] positionWithHeading = SteeringHelpers.getCarPositionAndCarHead(
            position, carHeading, carSpeed, steeringAngle, new int[] { width, height });

        if (positionWithHeading[0].getClass() == Point2D.Double.class) {
            position = new Point2D.Double(
                ((Point2D) positionWithHeading[0]).getX(), 
                ((Point2D) positionWithHeading[0]).getY());
        }

        if (positionWithHeading[1].getClass() == Double.class) {
            carHeading = (double) positionWithHeading[1];
        }

        rotation = (float) (-Math.toDegrees(Math.toRadians(THREE_QUARTER_CIRCLE) - carHeading));

        return position;
    }
}