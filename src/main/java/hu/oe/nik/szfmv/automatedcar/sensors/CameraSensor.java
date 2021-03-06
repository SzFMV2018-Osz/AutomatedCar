package hu.oe.nik.szfmv.automatedcar.sensors;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.sensor.SensorPacket;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.environment.WorldObject;
import hu.oe.nik.szfmv.model.Classes.Road;
import hu.oe.nik.szfmv.model.Classes.RoadSign;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.round;
import static java.lang.Math.tan;

public class CameraSensor extends SystemComponent implements ISensor {

    private static final int TRIANGLE_N = 3;
    private static final int VISUAL_RANGE = 80;
    private static final int ANGLE_OF_VIEW = 60;
    private static final int DEGREE_90 = 90;
    private static final int DEGREE_45 = 45;
    private static final int DEGREE_6 = 6;
    private static final int HALF_CIRCLE = 180;
    private static final int WHOLE_CIRCLE = 360;
    private Point positionOnCar;
    private Boolean leftLane;
    private double distanceFromBorder;
    private Polygon triangle;
    private List<Road> roads;
    private List<WorldObject> worldObjects;
    private List<WorldObject> detectedObjects;
    private Road currentRoad;
    private int angleOfTurning;
    private Boolean leftTurning;
    private int[] degrees;

    /**
     * @param virtualFunctionBus This Bus help to communicate with other SystemComponent
     *                           Create Radar sensor
     */
    public CameraSensor(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);

        this.virtualFunctionBus.sensorPacket = new SensorPacket();

        triangle = new Polygon();
        positionOnCar = new Point();
        roads = new ArrayList<>();
        this.worldObjects = virtualFunctionBus.worldObjects;
        leftLane = null;
        degrees = new int[]{DEGREE_90, DEGREE_45, DEGREE_6};
    }

    public Point getPositionOnCar() {
        return positionOnCar;
    }

    public Polygon getPolygon() {
        return triangle;
    }

    @Override
    public Polygon locateSensorTriangle(Point sensorPosition, double visualRange,
                                        double angelOfView, double sensorRotation) {
        Polygon triangle;

        double angleInRadian = Math.toRadians(ANGLE_OF_VIEW);
        double rotationInRadian = Math.toRadians(sensorRotation);

        Point a = new Point((int) round(sensorPosition.x - tan(angleInRadian / 2) * visualRange),
                (int) round(sensorPosition.y + visualRange));
        Point b =  new Point((int) round(sensorPosition.x + tan(angleInRadian / 2) * visualRange),
                (int) round(sensorPosition.y + visualRange));

        a = rotate(a, sensorPosition, rotationInRadian);
        b = rotate(b, sensorPosition, rotationInRadian);

        triangle = new Polygon(new int[]{sensorPosition.x, a.x, b.x},
                new int[]{sensorPosition.y, a.y, b.y}, TRIANGLE_N);

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

        /*
        Point newPositon = new Point(newSensorPosition.x + positionOnCar.x,
                newSensorPosition.y + positionOnCar.y);
        newPositon = rotate(newPositon, newSensorPosition, newSensorRotation);
        triangle = locateSensorTriangle(newPositon, VISUAL_RANGE, ANGLE_OF_VIEW, newSensorRotation);
        */
    }

    @Override
    public List<WorldObject> detectedObjects(List<WorldObject> worldObjects) {
        List<WorldObject> list = new ArrayList<>();
        roads = new ArrayList<>();
        for (WorldObject worldObject : worldObjects) {
            Rectangle rectangle = new Rectangle(worldObject.getX(), worldObject.getY(),
                    worldObject.getWidth(), worldObject.getHeight());
            if (triangle.intersects(rectangle)) {
                list.add(worldObject);
                if (worldObject instanceof Road) {
                    roads.add((Road)worldObject);
                }
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

    /**
     * Look after road informations
     */
    private void giveRoadInformations() {
        if (onRoad()) {
            leftLane = whichLane();
            if (!currentRoad.getImageFileName().contains("parking")) {
                if (Road.roadPolyMap.containsKey(currentRoad.getImageFileName())) {
                    distanceFromBorder = distanceFromPoly(new Point(virtualFunctionBus.carPacket.getxPosition(),
                                    virtualFunctionBus.carPacket.getyPosition()),
                            Road.roadPolyMap.get(currentRoad.getImageFileName()),
                            new Point(currentRoad.getX(), currentRoad.getY()));
                }
            }
            this.virtualFunctionBus.sensorPacket.setIfWeAreInLeftLane(leftLane);
            this.virtualFunctionBus.sensorPacket.setDistanceFromBound(distanceFromBorder);

        }
    }

    /**
     * Which lane
     * @return boolean
     */
    private boolean whichLane() {
        boolean result = false;

        Point carPosition = new Point(virtualFunctionBus.carPacket.getxPosition(),
                virtualFunctionBus.carPacket.getyPosition());

        Polygon polygon = setPoints(Road.roadPolyMap.get(currentRoad.getImageFileName()),
                new Point(currentRoad.getX(), currentRoad.getY()));

        if (currentRoad.getImageFileName().equals("road_2lane_straight.png")) {
            if (carPosition.x < polygon.xpoints[1] / 2) {
                result = true;
            }
        } else {
            int index = getIndexOfClosestPoint(polygon, carPosition);

            Point half = new Point((polygon.xpoints[index] + polygon.xpoints[polygon.npoints - index - 1]) / 2,
                    (polygon.ypoints[index] + polygon.ypoints[polygon.npoints - index - 1]) / 2);

            if (carPosition.x < half.x && carPosition.y < half.y) {
                result = true;
            }
        }

        return result;
    }

    private int getIndexOfClosestPoint(Polygon polygon, Point carPosition) {
        int index = 0;

        for (int i = 0; i < polygon.npoints / 2; i++) {
            Point oldPoint = new Point(polygon.xpoints[i], polygon.ypoints[i]);

            Point closestPoint = closestPointOnLine(carPosition, new Point(polygon.xpoints[i], polygon.ypoints[i]),
                    new Point(polygon.xpoints[i + 1], polygon.ypoints[i + 1]));

            if (oldPoint.equals(closestPoint)) {
                index = i;
            }
        }

        return index;
    }

    private boolean onRoad() {
        for (Road road : roads) {
            if (Road.roadPolyMap.get(road.getImageFileName()) != null) {
                Polygon polygon = setPoints(Road.roadPolyMap.get(road.getImageFileName()),
                        new Point(road.getX(), road.getY()));
                if (isPolygonIntersectPolygon(virtualFunctionBus.carPacket.getPolygon(), polygon)) {
                    currentRoad = road;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isPolygonIntersectPolygon(Polygon p1, Polygon p2) {
        boolean result = false;

        for (int i = 0; i < p2.npoints; i++) {
            Point p = new Point(p2.xpoints[i], p2.ypoints[i]);

            if (p1.contains(p)) {
                result = true;
                break;
            }
        }

        if (!result) {
            for (int i = 0; i < p1.npoints; i++) {
                Point p = new Point(p1.xpoints[i], p1.ypoints[i]);

                if (p2.contains(p)) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    private double distanceFromLine(Point point, Point line1, Point line2) {

        Point closestPointOnLine = closestPointOnLine(point, line1, line2);

        Point d = new Point(point.x - closestPointOnLine.x, point.y - closestPointOnLine.y);
        return Math.sqrt(d.x * d.x + d.y * d.y);
    }

    private Point closestPointOnLine(Point point, Point line1, Point line2) {
        float xDelta = line2.x - line1.x;
        float yDelta = line2.y - line1.y;

        float u = ((point.x - line1.x) * xDelta + (point.y - line1.y) * yDelta) / (xDelta * xDelta + yDelta * yDelta);

        Point closestPointOnLine;
        if (u < 0) {
            closestPointOnLine = line1;
        } else if (u > 1) {
            closestPointOnLine = line2;
        } else {
            closestPointOnLine = new Point((int)(line1.getX() + u * xDelta), (int)(line1.getY() + u * yDelta));
        }

        return closestPointOnLine;
    }

    private double distanceFromPoly(Point point, Polygon polygon, Point polygonPosition) {
        double result = Double.MAX_VALUE;

        Polygon temp = setPoints(polygon, polygonPosition);

        for (int i = 0; i < temp.npoints; i++) {
            int previousIndex = i - 1;
            if (previousIndex < 0) {
                previousIndex = temp.npoints - 1;
            }

            Point currentPoint = new Point(temp.xpoints[i], temp.ypoints[i]);
            Point previousPoint = new Point(temp.xpoints[previousIndex], temp.ypoints[previousIndex]);

            double segmentDistance = distanceFromLine(point, previousPoint, currentPoint);

            if (segmentDistance < result) {
                result = segmentDistance;
            }
        }

        return result;
    }

    private Polygon setPoints(Polygon polygon, Point point) {
        //set x coordinates
        for (int i = 0; i < polygon.npoints; i++) {
            polygon.xpoints[i] += point.getX();
        }
        //set y coordinates
        for (int i = 0; i < polygon.npoints; i++) {
            polygon.ypoints[i] += point.getY();
        }
        return polygon;
    }

    private void getTurningInformations() {
        if (Road.roadPolyMap.get(currentRoad.getImageFileName()) != null
                && !currentRoad.getImageFileName().contains("straight")) {
            String roadType = currentRoad.getImageFileName()
                    .substring(currentRoad.getImageFileName().lastIndexOf("_"),
                    currentRoad.getImageFileName().lastIndexOf("."));
            for (int degree : degrees) {
                if (roadType.contains(Integer.toString(degree))) {
                    angleOfTurning = degree;
                }
            }

            if (roadType.endsWith("ft")) {
                leftTurning = true;
            } else {
                leftTurning = false;
            }
        }
    }

    @Override
    public void loop() {
        detectedObjects = detectedObjects(worldObjects);
        giveRoadInformations();
        //getTurningInformations();

        double cameraRotation =  HALF_CIRCLE + (virtualFunctionBus.carPacket.getCarRotation() % WHOLE_CIRCLE);

        // LOGGER.info("car rotation"  + virtualFunctionBus.carPacket.getCarRotation());

        sensorPosition(cameraRotation);
        triangle = locateSensorTriangle(positionOnCar, VISUAL_RANGE, ANGLE_OF_VIEW, cameraRotation);
    }


    private void sensorPosition(double cameraRotation) {
        double carWidth = virtualFunctionBus.carPacket.getCarWidth();
        positionOnCar.x = (int) (virtualFunctionBus.carPacket.getxPosition() -
                Math.cos(Math.toRadians(cameraRotation)) * (carWidth / 2));
        positionOnCar.y = (int) (virtualFunctionBus.carPacket.getyPosition() -
                Math.sin(Math.toRadians(cameraRotation)) * (carWidth / 2));

    }

}
