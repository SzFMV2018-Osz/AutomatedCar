package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.carpacket.CarPacket;

import hu.oe.nik.szfmv.automatedcar.sensors.CameraSensor;
import hu.oe.nik.szfmv.automatedcar.sensors.ISensor;
import hu.oe.nik.szfmv.automatedcar.sensors.RadarSensor;

import hu.oe.nik.szfmv.automatedcar.sensors.UltrasonicSensor;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.PowertrainSystem;
import hu.oe.nik.szfmv.model.Classes.Car;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import java.util.List;

public class AutomatedCar extends Car {
    private static final int THREE_QUARTER_CIRCLE = 270;
    private static final double CAMERA_RELATIVE_POSITION_IN_PERCENT = 0.8;
    private static final double RADAR_RELATIVE_POSITION_IN_PERCENT = 0.95;

    private final VirtualFunctionBus virtualFunctionBus = new VirtualFunctionBus();
    private List<ISensor> sensorList;
    private PowertrainSystem powertrainSystem;

    private CameraSensor cameraSensor;

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


        sensorList = new ArrayList<>();
        createSensors();

        powertrainSystem = new PowertrainSystem(virtualFunctionBus);
        cameraSensor = new CameraSensor(virtualFunctionBus);

        AddUltrasonicSensors();
        virtualFunctionBus.ultrasonicSensors = ultrasonicSensors;

        new Driver(virtualFunctionBus);
    }


    /**
     * Create the car's sensors
     */
    private void createSensors() {
        RadarSensor radarSensor = new RadarSensor(virtualFunctionBus);
        radarSensor.getPositionOnCar().x = width / 2;
        radarSensor.getPositionOnCar().y = (int) (height * RADAR_RELATIVE_POSITION_IN_PERCENT);
        sensorList.add(radarSensor);
        CameraSensor cameraSensor = new CameraSensor(virtualFunctionBus);
        cameraSensor.getPositionOnCar().x = width / 2;
        cameraSensor.getPositionOnCar().y = (int) (height * CAMERA_RELATIVE_POSITION_IN_PERCENT);
        sensorList.add(cameraSensor);
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
     * Refresh the positions of the sensors
     */
    private void calculatePositionAndOrientation() {
        double carSpeed = this.powertrainSystem.getSpeed();
        double steeringAngle = 0;
        double carHeading = Math.toRadians(THREE_QUARTER_CIRCLE + rotation);
        double halfWheelBase = (double) height / 2;

        steeringAngle = SteeringHelpers.getSteerAngle(-this.virtualFunctionBus.samplePacket.getWheelPosition());

        Point2D position = calculateNewPosition(carSpeed, steeringAngle, carHeading);

        this.setX((int) Math.round(position.getX() - (double) width / 2));
        this.setY((int) Math.round(position.getY() - halfWheelBase));

        calculateSensorPositions();

        virtualFunctionBus.carPacket.setxPosition(this.getX());
        virtualFunctionBus.carPacket.setyPosition(this.getY());
        virtualFunctionBus.carPacket.setCarRotation(this.getRotation());
    }

    /**
     * Refresh the positions of the sensors
     */
    private void calculateSensorPositions() {
        for (ISensor sensor : sensorList) {
            sensor.refreshSensor(new Point(getX(), getY()), rotation);
        }
    }


    /**
     * Calculates the new position based on the speed and steering angle.
     *
     * @param carSpeed      Speed of the car.
     * @param steeringAngle Steering angle.
     * @param carHeading    Car heading.
     * @return New position of the car.
     */
    private Point2D calculateNewPosition(double carSpeed, double steeringAngle, double carHeading) {
        Point2D position = new Point2D.Double(

                virtualFunctionBus.carPacket.getxPosition(),
                virtualFunctionBus.carPacket.getyPosition());
        Object[] positionWithHeading = SteeringHelpers.getCarPositionAndCarHead(
                position, carHeading, carSpeed, steeringAngle, new int[]{width, height});

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

    public void stopImmediately(){
        this.powertrainSystem.stopImmediately();
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

