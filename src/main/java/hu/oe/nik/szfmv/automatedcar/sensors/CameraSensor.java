package hu.oe.nik.szfmv.automatedcar.sensors;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.environment.WorldObject;
import hu.oe.nik.szfmv.model.Classes.RoadSign;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CameraSensor extends SystemComponent implements ISensor {

    private static final int TRIANGLE_N = 3;
    private static final int VISUAL_RANGE = 80;
    private static final int ANGLE_OF_VIEW = 60;
    private Point positionOnCar;
    private Polygon radarTriangle;
    private boolean rightLane;
    private int distanceFromBorder;
    private Polygon triangle;

    /**
     * @param virtualFunctionBus This Bus help to communicate with other SystemComponent
     *                           Create Radar sensor
     */
    public CameraSensor(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);


        positionOnCar = new Point();

        triangle = new Polygon();
    }

    public Point getPositionOnCar() {
        return positionOnCar;
    }

    @Override
    public Polygon locateSensorTriangle(Point sensorPosition, double visualRange,
                                        double angelOfView, double sensorRotation) {
        Point leftPoint = new Point();
        Point rightPoint = new Point();

        double angleInRadian = Math.toRadians(angelOfView);
        double sensorRotationInRadian = Math.toRadians(sensorRotation);

        leftPoint.x = (int) (Math.round(sensorPosition.x + Math.tan(angleInRadian / 2)) * visualRange);
        leftPoint.y = (int) Math.round(sensorPosition.y + visualRange);
        rightPoint.x = (int) (Math.round(sensorPosition.x - Math.tan(angleInRadian / 2)) * visualRange);
        rightPoint.y = (int) Math.round(sensorPosition.y + visualRange);

        leftPoint = rotate(leftPoint, sensorPosition, sensorRotationInRadian);
        rightPoint = rotate(rightPoint, sensorPosition, sensorRotationInRadian);

        triangle = new Polygon();
        triangle.npoints = TRIANGLE_N;
        triangle.xpoints = new int[]{sensorPosition.x, leftPoint.x, rightPoint.x};
        triangle.ypoints = new int[]{sensorPosition.y, leftPoint.y, rightPoint.y};

        return triangle;
    }

    private Point rotate(Point point, Point sennsorLocation, double rotation) {

        double x = sennsorLocation.x + (point.x - sennsorLocation.x) * Math.cos(rotation)
                - (point.y - sennsorLocation.y) * Math.sin(rotation);
        double y = sennsorLocation.y + (point.x - sennsorLocation.x) * Math.sin(rotation)
                + (point.y - sennsorLocation.y) * Math.cos(rotation);

        return new Point((int) x, (int) y);
    }

    @Override
    public void refreshSensor(Point newSensorPosition, double newSensorRotation) {

        Point newPositon = new Point(newSensorPosition.x + positionOnCar.x,
                newSensorPosition.y + positionOnCar.y);

        newPositon = rotate(newPositon, newSensorPosition, newSensorRotation);
        radarTriangle = locateSensorTriangle(newPositon, VISUAL_RANGE, ANGLE_OF_VIEW, newSensorRotation);
    }

    @Override
    public List<WorldObject> detectedObjects(List<WorldObject> worldObjects) {
        List<WorldObject> list = new ArrayList<>();
        for (WorldObject worldObject : worldObjects) {
            Rectangle rectangle = new Rectangle(worldObject.getX(), worldObject.getY(),
                    worldObject.getWidth(), worldObject.getHeight());
            if (triangle.intersects(rectangle)) {
                list.add(worldObject);
            }
        }

        this.virtualFunctionBus.sensorPacket.setCameraDetectedObjects(list);

        return list;
    }

    /**
     * Gets the road signs, which the camera sees
     * @return list of the road signs
     */
    private List<WorldObject> getDetectedRoadSigns() {
        List<WorldObject> detectedRoadSigns = new ArrayList<>();

        for (WorldObject worldObject : this.virtualFunctionBus.sensorPacket.getCameraDetectedObjects()) {
            if (worldObject.getClass().equals(RoadSign.class)) {
                detectedRoadSigns.add(worldObject);
            }
        }

        return detectedRoadSigns;
    }


    /**
     * Search the nearest road sign in the list of the found road signs. If there are not any detected sign, set
     * null.
     */
    private void searchNearestRoadSign() {
        List<WorldObject> detectedRoadSigns = this.getDetectedRoadSigns();

        if (detectedRoadSigns.size() > 0) {
            double minDistance = Double.MAX_VALUE;
            WorldObject nearest = null;

            for (WorldObject sign : detectedRoadSigns) {
                double distance = calculateDistanceFromCamera(sign);

                if (distance < minDistance) {
                    minDistance = distance;
                    nearest = sign;
                }
            }

            this.virtualFunctionBus.sensorPacket.setDetectedRoadSign(nearest);
            this.virtualFunctionBus.sensorPacket.setDistanceOfRoadSign(minDistance);
        } else {
            this.virtualFunctionBus.sensorPacket.setDetectedRoadSign(null);
            this.virtualFunctionBus.sensorPacket.setDistanceOfRoadSign(0d);
        }
    }

    /**
     * Calculate the distance between the camera and the given worldObject
     * @param worldObject world object
     * @return the distance
     */
    private double calculateDistanceFromCamera(WorldObject worldObject) {
        return Math.sqrt(Math.pow(virtualFunctionBus.carPacket.getxPosition() - worldObject.getX(), 2)
                + Math.pow(virtualFunctionBus.carPacket.getyPosition() - worldObject.getY(), 2));
    }

    @Override
    public void loop() {
    }

}
