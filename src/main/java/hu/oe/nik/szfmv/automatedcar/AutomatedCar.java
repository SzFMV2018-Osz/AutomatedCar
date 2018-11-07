package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.carpacket.CarPacket;
import hu.oe.nik.szfmv.automatedcar.sensors.UltrasonicSensor;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.PowertrainSystem;
import hu.oe.nik.szfmv.model.Classes.Car;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class AutomatedCar extends Car {
    private static final int THREE_QUARTER_CIRCLE = 270;

    private final VirtualFunctionBus virtualFunctionBus = new VirtualFunctionBus();
    private PowertrainSystem powertrainSystem;
    private ArrayList<UltrasonicSensor> ultrasonicSensors = new ArrayList<UltrasonicSensor>();

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

        AddUltrasonicSensors();
        virtualFunctionBus.ultrasonicSensors = ultrasonicSensors;

        new Driver(virtualFunctionBus);
    }

    public VirtualFunctionBus getVirtualFunctionBus() {
        return virtualFunctionBus;
    }

    private void setCarPacket() {
        CarPacket carPacket = new CarPacket();
        carPacket.setCarWidth(width);
        carPacket.setCarHeigth(height);
        carPacket.setCarRotation(rotation);
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

        steeringAngle = SteeringHelpers.getSteerAngle(-this.virtualFunctionBus.samplePacket.getWheelPosition());

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

    private void AddUltrasonicSensors() {
        int carWidth = virtualFunctionBus.carPacket.getCarWidth();
        int carHeight = virtualFunctionBus.carPacket.getCarHeigth();
        //front sensors
        ultrasonicSensors.add(new UltrasonicSensor(virtualFunctionBus, 10, carWidth / 2 + 30, 0));
        ultrasonicSensors.add(new UltrasonicSensor(virtualFunctionBus, 10, carWidth / 2 - 30, 0));

        //back sensors
        ultrasonicSensors.add(new UltrasonicSensor(virtualFunctionBus, carHeight - 10, carWidth / 2 + 30, 180));
        ultrasonicSensors.add(new UltrasonicSensor(virtualFunctionBus, carHeight - 10, carWidth / 2 - 30, 180));

        //right sensors
        ultrasonicSensors.add(new UltrasonicSensor(virtualFunctionBus, 30, carWidth - 5, 90));
        ultrasonicSensors.add(new UltrasonicSensor(virtualFunctionBus, carHeight - 30, carWidth - 5, 90));

        //left sensors
        ultrasonicSensors.add(new UltrasonicSensor(virtualFunctionBus, 30, 5, -90));
        ultrasonicSensors.add(new UltrasonicSensor(virtualFunctionBus, carHeight - 30, 5, -90));

    }
}

CAR

        package hu.oe.nik.szfmv.automatedcar.bus.packets.carpacket;

public class CarPacket implements ReadOnlyCarPacket {

    private int carHeigth;
    private int carWidth;
    private int xPosition;
    private int yPosition;
    private float carRotation;


    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public float getCarRotation() {
        return carRotation;
    }

    public void setCarRotation(float carRotation) {
        this.carRotation = carRotation;
    }


    @Override
    public int getCarHeigth() {
        return carHeigth;
    }

    public void setCarHeigth(int carHeigth) {
        this.carHeigth = carHeigth;
    }

    @Override
    public int getCarWidth() {
        return carWidth;
    }

    public void setCarWidth(int carWidth) {
        this.carWidth = carWidth;
    }
}
