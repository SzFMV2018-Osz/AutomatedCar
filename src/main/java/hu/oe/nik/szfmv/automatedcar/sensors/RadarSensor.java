package hu.oe.nik.szfmv.automatedcar.sensors;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.environment.WorldObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RadarSensor extends SystemComponent implements ISensor {

    private static final int TRIANGLE_N = 3;
    private static final int VISUAL_RANGE = 200;
    private static final int ANGLE_OF_VIEW = 60;
    Point positionOnCar;
    private Polygon radarTriangle;

    /**
     * @param virtualFunctionBus This Bus help to communicate with other SystemComponent
     *                           Create Radar sensor
     */
    public RadarSensor(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
    }

    @Override
    public Polygon locateSensorTriangle(Point sensorPosition, double visualRange,
                                        double angelOfView, double sensorRotation) {
        Point leftPoint = new Point();
        Point rightPoint = new Point();

        double angleInRadian = Math.toRadians(angelOfView);

        leftPoint.x = (int) (Math.round(sensorPosition.x + Math.tan(angleInRadian / 2)) * visualRange);
        leftPoint.y = (int) Math.round(sensorPosition.y + visualRange);
        rightPoint.x = (int) (Math.round(sensorPosition.x - Math.tan(angleInRadian / 2)) * visualRange);
        rightPoint.y = (int) Math.round(sensorPosition.y + visualRange);

        double sensorRotationInRadian = Math.toRadians(sensorRotation);

        leftPoint = rotate(leftPoint, sensorPosition, sensorRotationInRadian);
        rightPoint = rotate(rightPoint, sensorPosition, sensorRotationInRadian);

        Polygon triangle = new Polygon();
        triangle.npoints = TRIANGLE_N;
        triangle.xpoints = new int[]{sensorPosition.x, leftPoint.x, rightPoint.x};
        triangle.ypoints = new int[]{sensorPosition.y, leftPoint.y, rightPoint.y};

        return triangle;
    }

    private Point rotate(Point point, Point sensorLocation, double rotation) {

        double x = sensorLocation.x + (point.x - sensorLocation.x) * Math.cos(rotation)
                - (point.y - sensorLocation.y) * Math.sin(rotation);
        double y = sensorLocation.y + (point.x - sensorLocation.x) * Math.sin(rotation)
                + (point.y - sensorLocation.y) * Math.cos(rotation);

        return new Point((int) x, (int) y);
    }

    @Override
    public void refreshSensor(Point newSensorPosition, double newSensorRotation) {
        radarTriangle = locateSensorTriangle(newSensorPosition, VISUAL_RANGE, ANGLE_OF_VIEW, newSensorRotation);
    }

    /**
     * Returns a list of world objects what sees the radar
     *
     * @param worldObjects all world objects
     * @return detected world objects
     */
    @Override
    public List<WorldObject> detectedObjects(List<WorldObject> worldObjects) {
        List<WorldObject> detectedObject = new ArrayList<>();

        worldObjects.forEach(item -> {
            if (this.radarTriangle.contains(item.getX(), item.getY(), item.getWidth(), item.getHeight())) {
                detectedObject.add(item);
            }
        });

        return detectedObject;
    }

    @Override
    public void loop() {

    }
}
