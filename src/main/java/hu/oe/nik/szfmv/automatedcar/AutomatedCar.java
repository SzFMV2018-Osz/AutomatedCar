package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.carpacket.CarPacket;
import hu.oe.nik.szfmv.automatedcar.sensors.CameraSensor;
import hu.oe.nik.szfmv.automatedcar.sensors.ISensor;
import hu.oe.nik.szfmv.automatedcar.sensors.RadarSensor;
import hu.oe.nik.szfmv.automatedcar.sensors.UltrasonicSensor;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.ParkingPilot;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.PowertrainSystem;
import hu.oe.nik.szfmv.environment.WorldObject;
import hu.oe.nik.szfmv.model.Classes.Car;
import hu.oe.nik.szfmv.model.Classes.Dynamic;
import hu.oe.nik.szfmv.model.Classes.RoadObsticle;
import hu.oe.nik.szfmv.visualization.Dashboard;
import hu.oe.nik.szfmv.visualization.Gui;
import hu.oe.nik.szfmv.visualization.Index;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class AutomatedCar extends Car {
    private static final int THREE_QUARTER_CIRCLE = 270;
    private static final double CAMERA_RELATIVE_POSITION_IN_PERCENT = 0.8;
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
    private ParkingPilot parkingPilot;

    private Dashboard ownDashboardData;

    private ArrayList<UltrasonicSensor> ultrasonicSensors = new ArrayList<>();

    /**
     * Creates an object of the virtual world on the given coordinates with the given image.
     *
     * @param x             the initial x coordinate of the object
     * @param y             the initial y coordinate of the object
     * @param imageFileName the filename of the image representing the object in the virtual world
     */
    public AutomatedCar(int x, int y, String imageFileName, List<WorldObject> worldObjects, Dashboard db) {
        super(x, y, imageFileName);

        virtualFunctionBus.worldObjects = worldObjects;

        setCarPacket();

        sensorList = new ArrayList<>();
        createSensors();

        powertrainSystem = new PowertrainSystem(virtualFunctionBus);

        addUltrasonicSensors();
        virtualFunctionBus.ultrasonicSensors = ultrasonicSensors;

        new Driver(virtualFunctionBus);

        parkingPilot = new ParkingPilot(virtualFunctionBus);

        ownDashboardData = db;
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
        carPacket.setPolygon(setPolygon(x, y, width, height));
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

    public Polygon setPolygon(int x, int y, int width, int height) {
        Polygon polygon = new Polygon();
        polygon.addPoint(x, y);
        polygon.addPoint(x + width, y);
        polygon.addPoint(x + width, y + height);
        polygon.addPoint(x, y + height);
        return polygon;
    }

    public void setGui(Gui gui)
    {
        parkingPilot.setGui(gui);
    }


    public void parkingSpotSeeking(List<WorldObject> worldObjects) {
        //region<ParkingZoneSize>
        int parkingZoneHorizontalStart = 100;
        int parkingZoneHorizontalEnd = 700;

        int parkingZoneVerticalStart = 650;
        int parkingZoneVerticalEnd = 1950;
        //endregion<>

        //region<If we are in the parking zone>
        if(this.y < parkingZoneVerticalEnd && this.y > parkingZoneVerticalStart && this.x < parkingZoneHorizontalEnd && this.x > parkingZoneHorizontalStart ) {

            WorldObject closest = null; //The closest object from the car
            List<WorldObject> detected = new ArrayList<>();
            List<WorldObject> obstacles = new ArrayList<>();

            //1 if left, 2 if right side
            int side = 0;

            //region<side check>

            if(ownDashboardData.getIndex().actIndex == Index.Direction.right) {
                side = 1;
                //"Searching on the right side"
            }
            else if (ownDashboardData.getIndex().actIndex == Index.Direction.left) {
                side = 2;
                //"Searching on the left side"
            }
            else{
                side = 0;
                //"No side"
            }

            for (int i = 4; i < ultrasonicSensors.size(); i++){
                if((side == 1 && (i==4 || i== 5)) || (side == 2 && (i==6 || i== 7))){
                    for (int j = 0; j < ultrasonicSensors.get(i).detectedObjects(worldObjects).size(); j++){
                        WorldObject actual = ultrasonicSensors.get(i).detectedObjects(worldObjects).get(j);
                        if(actual.getX()+actual.getWidth()/2 >= parkingZoneHorizontalStart && actual.getX()-actual.getWidth()/2 <= parkingZoneHorizontalEnd
                                && actual.getY()+actual.getHeight()/2 >= parkingZoneVerticalStart && actual.getY()-actual.getHeight()/2 <= parkingZoneVerticalEnd){
                            detected.add(actual);
                        }
                    }
                }
            }

            //endregion<>

            //region<The closest object from the "detected"
            for(int i = 0; i < detected.size(); i++){
                if(detected.get(i).getClass() == Dynamic.class || detected.get(i).getClass() == RoadObsticle.class){
                    obstacles.add((WorldObject) detected.get(i));
                }
            }

            closest = obstacles.get(0);
            for(int i = 1; i < obstacles.size(); i++){
                int cx = closest.getX();
                int cy = closest.getY();

                int ix = obstacles.get(i).getX();
                int iy = obstacles.get(i).getY();

                int acx = this.getX();
                int acy = this.getY();

                double distanceC = Math.abs(Math.sqrt((cy-acy)*(cy-acy)+(cx-acx)*(cx-acx)));
                double distanceI = Math.abs(Math.sqrt((iy-acy)*(iy-acy)+(ix-acx)*(ix-acx)));

                if(distanceI < distanceC){
                    closest = obstacles.get(i);
                }
            }
            //endregion<>


            //check the parking size
            if(side != 0){

                //Is there any object in the searching area
                boolean thereIs = false;

                //region<If the parking spot is vertical>
                if(parkingZoneVerticalEnd-parkingZoneVerticalStart > parkingZoneHorizontalEnd-parkingZoneHorizontalStart) {

                    //region<vertical check>
                    for (int i = 0; i < detected.size(); i++) {
                        //region<auxiliary variables>
                        int iHeightEnd = detected.get(i).getY() + detected.get(i).getHeight() / 2 + detected.get(i).getHeight() / 10;
                        int iHeightStart = detected.get(i).getLastY() - detected.get(i).getHeight() / 2 - detected.get(i).getHeight() / 10;
                        int closestHeightStart = closest.getY() - closest.getHeight() / 2;
                        int closestHeightEnd = closest.getY() + closest.getHeight() / 2;

                        int acHeight = this.getHeight() + this.getHeight() / 10;

                        int iWidthLeft = detected.get(i).getX() - detected.get(i).getWidth() / 2 - detected.get(i).getWidth() / 10;
                        int iWidthRight = detected.get(i).getX() + detected.get(i).getHeight() / 2 + detected.get(i).getHeight() / 10;
                        int closestWidthLeft = closest.getX() - closest.getWidth() / 2 - closest.getWidth() / 10;
                        int closestWidthRight = closest.getX() + closest.getWidth() / 2 + closest.getWidth() / 10;

                        int acWidth = this.getWidth() + this.getWidth() / 10;
                        //endregion<>

                        //region<Above the closest">
                        if (closest.getY() >= this.getY()) {
                            if ((side == 1 && this.rotation >= -45f && this.rotation < 45f) || (side == 2 && this.rotation >= 135f && this.rotation < 225f )) {
                                if (closestHeightStart-acHeight > parkingZoneVerticalStart || (iHeightEnd > closestHeightStart - acHeight && iHeightStart < closestHeightStart) || (iWidthLeft < closestWidthRight && iWidthRight > closestWidthLeft - acWidth - acWidth / 2)) {
                                    thereIs = true;
                                }
                            }
                            if ((side == 2 && this.rotation >= -45f && this.rotation < 45f) || (side == 1 && this.rotation >= 135f && this.rotation < 225f )) {
                                if (closestHeightStart-acHeight > parkingZoneVerticalStart || (iHeightEnd > closestHeightStart - acHeight && iHeightStart < closestHeightStart) || (iWidthRight > closestWidthLeft && iWidthLeft < closestWidthRight + acWidth + acWidth / 2)) {
                                    thereIs = true;
                                }
                            }
                        }
                        //endregion<>
                        //region<Below the closest>
                        else{
                            if ((side == 1 && this.rotation >= -45f && this.rotation <= 45f) || (side == 2 && this.rotation >= 135f && this.rotation <= 225f )) {
                                if (closestHeightEnd + acHeight < parkingZoneVerticalEnd || (iHeightStart < closestHeightEnd + acHeight && iHeightEnd > closestHeightEnd) || (iWidthLeft < closestWidthRight && iWidthRight > closestWidthLeft - acWidth - acWidth / 2)) {
                                    thereIs = true;
                                }
                            }
                            if ((side == 2 && this.rotation >= -45f && this.rotation <= 45f) || (side == 1 && this.rotation >= 135f && this.rotation <= 225f )) {
                                if (closestHeightEnd+acHeight < parkingZoneVerticalEnd || (iHeightStart > closestHeightEnd + acHeight && iHeightEnd > closestHeightEnd) || (iWidthRight > closestWidthLeft && iWidthLeft < closestWidthRight + acWidth + acWidth / 2)) {
                                    thereIs = true;
                                }
                            }
                        }
                        //endregion<>
                    }
                    //endregion<>
                }
                //endregion<>
                //region<If the parking spot is horizontal>
                if (parkingZoneVerticalEnd - parkingZoneVerticalStart < parkingZoneHorizontalEnd - parkingZoneHorizontalStart) {
                    //region<The left from the closest>
                    if (closest.getX() >= this.getX()) {
                        //size of the spot
                        for (int k = 0; k < detected.size(); k++) {

                            int kHeightEnd = detected.get(k).getX() + detected.get(k).getHeight() / 2 + detected.get(k).getHeight() / 10;
                            int closestHeightStart = closest.getX() - closest.getHeight() / 2;
                            int acHeight = this.getHeight() + this.getHeight() / 10;

                            int kWidthLeft = detected.get(k).getY() - detected.get(k).getWidth() / 2 - detected.get(k).getWidth() / 10;
                            int KWidthRight = detected.get(k).getY() + detected.get(k).getHeight() / 2 + detected.get(k).getHeight() / 10;
                            int closestWidthRight = closest.getY() + closest.getWidth() / 2 + closest.getWidth() / 10;
                            int closestWidthLeft = closest.getY() - closest.getWidth() / 2 - closest.getWidth() / 10;

                            int acWidth = this.getWidth() + this.getWidth() / 10;

                            if((side == 1 && this.rotation >= 225f && this.rotation < 315f) || (side == 2 && this.rotation >= 45f && this.rotation < 135f )){
                                if (kHeightEnd > closestHeightStart - acHeight || (kWidthLeft < closestWidthRight && KWidthRight > closestWidthLeft)) {
                                    thereIs = true;
                                }
                            }

                            if((side == 1 && this.rotation >= 45f && this.rotation <= 135f) || (side == 2 && this.rotation >= 225f && this.rotation <= 315f )){
                                if (kHeightEnd > closestHeightStart - acHeight || (kWidthLeft < closestWidthRight && KWidthRight > closestWidthLeft)) {
                                    thereIs = true;
                                }
                            }
                        }
                    }
                    //endregion<>
                    //region<The left from the closest>
                    else {
                        for (int k = 0; k < detected.size(); k++) {

                            int kHeightStart = detected.get(k).getX() - detected.get(k).getHeight() / 2 - detected.get(k).getHeight() / 10;
                            int closestHeightEnd = closest.getX() + closest.getHeight() / 2;
                            int acHeight = this.getHeight() + this.getHeight() / 10;

                            int kWidthLeft = detected.get(k).getY() - detected.get(k).getWidth() / 2 - detected.get(k).getWidth() / 10;
                            int KWidthRight = detected.get(k).getY() + detected.get(k).getHeight() / 2 + detected.get(k).getHeight() / 10;
                            int closestWidthRight = closest.getY() + closest.getWidth() / 2 + closest.getWidth() / 10;
                            int closestWidthLeft = closest.getY() - closest.getWidth() / 2 - closest.getWidth() / 10;

                            int acWidth = this.getWidth() + this.getWidth() / 10;

                            if((side == 1 && this.rotation >= 225f && this.rotation < 315f) || (side == 2 && this.rotation >= 45f && this.rotation < 135f )) {
                                if (kHeightStart < closestHeightEnd + acHeight || (kWidthLeft < closestWidthRight && KWidthRight > closestWidthLeft)) {
                                    thereIs = true;
                                }
                            }

                            if((side == 2 && this.rotation >= 225f && this.rotation < 315f) || (side == 1 && this.rotation >= 45f && this.rotation < 135f )) {
                                if (kHeightStart < closestHeightEnd + acHeight || (kWidthLeft < closestWidthRight && KWidthRight > closestWidthLeft)) {
                                    thereIs = true;
                                }
                            }
                        }
                    }
                    //endregion<>
                }
                //endregion<>

                //region<"The parking size is suitable?"
                //If the parking size is suitable
                if (!thereIs) {
                    //The parking size is suitable
                    //auto parking can be started
                }
                else {
                    //"The parking size is not suitable"
                }
                //endregion<>
            }
        }
        //endregion<>
        //region<if we are not in the parking zone>
        else{
            //"Not a parking zone"!"
        }
        //endregion<>
    }
}

