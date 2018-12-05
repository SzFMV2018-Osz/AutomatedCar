package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.carpacket.CarPacket;
import hu.oe.nik.szfmv.automatedcar.sensors.CameraSensor;
import hu.oe.nik.szfmv.automatedcar.sensors.ISensor;
import hu.oe.nik.szfmv.automatedcar.sensors.RadarSensor;
import hu.oe.nik.szfmv.automatedcar.sensors.UltrasonicSensor;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.PowertrainSystem;
import hu.oe.nik.szfmv.environment.WorldObject;
import hu.oe.nik.szfmv.model.Classes.Car;
import hu.oe.nik.szfmv.model.Classes.NonPlayableCar;
import hu.oe.nik.szfmv.model.Classes.RoadSign;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class AutomatedCar extends Car {
    private static final int THREE_QUARTER_CIRCLE = 270;
    private static final double CAMERA_RELATIVE_POSITION_IN_PERCENT = 0.5;
    private static final double RADAR_RELATIVE_POSITION_IN_PERCENT = 0.95;
    private static final int BACKFRONT_VERTSHIFT = 10;
    private static final int RIGHTLEFT_VERTSHIFT = 30;
    private static final int BACKFRONT_HORSHIFT = 30;
    private static final int RIGHTLEFT_HORSHIFT = 5;
    private static final int FRONT_VIEWDIRECTION = 0;
    private static final int BACK_VIEWDIRECTION = 180;
    private static final int RIGHT_VIEWDIRECTION = 90;
    private static final int LEFT_VIEWDIRECTION = -90;

    private final VirtualFunctionBus virtualFunctionBus = new VirtualFunctionBus();
    private List<ISensor> sensorList;
    private PowertrainSystem powertrainSystem;

    private ArrayList<UltrasonicSensor> ultrasonicSensors = new ArrayList<>();

    /**
     * Creates an object of the virtual world on the given coordinates with the given image.
     *
     * @param x             the initial x coordinate of the object
     * @param y             the initial y coordinate of the object
     * @param imageFileName the filename of the image representing the object in the virtual world
     * @param worldObjects worldObjects
     */
    public AutomatedCar(int x, int y, String imageFileName, List<WorldObject> worldObjects) {
        super(x, y, imageFileName);

        virtualFunctionBus.worldObjects = worldObjects;

        setCarPacket();

        sensorList = new ArrayList<>();
        createSensors();

        powertrainSystem = new PowertrainSystem(virtualFunctionBus);

        addUltrasonicSensors();
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

        virtualFunctionBus.radarSensor = radarSensor;

        CameraSensor cameraSensor = new CameraSensor(virtualFunctionBus);
        cameraSensor.getPositionOnCar().x = width / 2 ;
        cameraSensor.getPositionOnCar().y = (int) (height * CAMERA_RELATIVE_POSITION_IN_PERCENT);
        sensorList.add(cameraSensor);

        virtualFunctionBus.cameraSensor = cameraSensor;
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
        carPacket.setPolygon(setPolygon(x, y, width, height));
        virtualFunctionBus.carPacket = carPacket;
    }

    /**
     * Driving the Car
     */
    public void drive() {
        calculateSpeed();
        if(virtualFunctionBus.DangerOfCollision) {
            stopImmediately();
            calculatePositionAndOrientation();
        }
        else
            calculatePositionAndOrientation();
        virtualFunctionBus.loop();
    }

    /**
     * Calculates the position and the orientation of the car.
     * Refresh the positions of the sensors
     */
    private void calculatePositionAndOrientation() {
        double carSpeed = this.powertrainSystem.getSpeedWithDirection();
        double steeringAngle;
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

    /**
     * Stops immediately the power systems
     */
    public void stopImmediately() {
        this.powertrainSystem.stopImmediately();
    }

    private void addUltrasonicSensors() {
        int carWidth = virtualFunctionBus.carPacket.getCarWidth();
        int carHeight = virtualFunctionBus.carPacket.getCarHeigth();

        //front sensors
        ultrasonicSensors.add(new UltrasonicSensor(virtualFunctionBus, BACKFRONT_VERTSHIFT,
                carWidth / 2 + BACKFRONT_HORSHIFT, FRONT_VIEWDIRECTION));
        ultrasonicSensors.add(new UltrasonicSensor(virtualFunctionBus, BACKFRONT_VERTSHIFT,
                carWidth / 2 - BACKFRONT_HORSHIFT, FRONT_VIEWDIRECTION));

        //back sensors
        ultrasonicSensors.add(new UltrasonicSensor(virtualFunctionBus, carHeight - BACKFRONT_VERTSHIFT,
                carWidth / 2 + BACKFRONT_HORSHIFT, BACK_VIEWDIRECTION));
        ultrasonicSensors.add(new UltrasonicSensor(virtualFunctionBus, carHeight - BACKFRONT_VERTSHIFT,
                carWidth / 2 - BACKFRONT_HORSHIFT, BACK_VIEWDIRECTION));

        //right sensors
        ultrasonicSensors.add(new UltrasonicSensor(virtualFunctionBus, RIGHTLEFT_VERTSHIFT,
                carWidth - RIGHTLEFT_HORSHIFT, RIGHT_VIEWDIRECTION));
        ultrasonicSensors.add(new UltrasonicSensor(virtualFunctionBus, carHeight - RIGHTLEFT_VERTSHIFT,
                carWidth - RIGHTLEFT_HORSHIFT, RIGHT_VIEWDIRECTION));

        //left sensors
        ultrasonicSensors.add(new UltrasonicSensor(virtualFunctionBus, RIGHTLEFT_VERTSHIFT,
                RIGHTLEFT_HORSHIFT, LEFT_VIEWDIRECTION));
        ultrasonicSensors.add(new UltrasonicSensor(virtualFunctionBus, carHeight - RIGHTLEFT_VERTSHIFT,
                RIGHTLEFT_HORSHIFT, LEFT_VIEWDIRECTION));

    }

    /**
     * set polyigon
     * @param x x
     * @param y y
     * @param width width
     * @param height height
     * @return returns new polygon
     */
    public Polygon setPolygon(int x, int y, int width, int height) {
        Polygon polygon = new Polygon();
        polygon.addPoint(x, y);
        polygon.addPoint(x + width, y);
        polygon.addPoint(x + width, y + height);
        polygon.addPoint(x, y + height);
        return polygon;
    }

    public void calculateSpeed()
    {
        this.virtualFunctionBus.DangerOfCollision = false;
        for(WorldObject worldObject : this.virtualFunctionBus.radarSensor.detectedObjects(virtualFunctionBus.worldObjects))
        {
            if(worldObject instanceof RoadSign){
                int numberBeginning = 6;
                int numberEnding = 4;
                int speedDouble = 2;
                if(worldObject.getImageFileName().contains("0") &&
                        Integer.parseInt(worldObject.getImageFileName().substring(worldObject.getImageFileName().length()-numberBeginning,
                                worldObject.getImageFileName().length()-numberEnding)) < (int)this.powertrainSystem.getSpeed()*speedDouble){
                    //TODO
                }
            }
            if(worldObject instanceof NonPlayableCar)
            {
                if(((NonPlayableCar)worldObject).getSpeed() < this.powertrainSystem.getSpeedWithDirection())
                {
                    virtualFunctionBus.DangerOfCollision = true;
                    //TODO
                    //powertrainSystem.stopImmediately();
                    //this.virtualFunctionBus.powertrainPacket.setSpeed(powertrainSystem.getSpeed()-10);
                   // this.virtualFunctionBus.powertrainPacket.setSpeed(((NonPlayableCar)worldObject).getSpeed());
                }
                else {
                    virtualFunctionBus.DangerOfCollision = false;
                }
            }
        }
    }
}

