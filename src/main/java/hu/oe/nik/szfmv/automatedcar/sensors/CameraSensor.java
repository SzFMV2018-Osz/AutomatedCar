package hu.oe.nik.szfmv.automatedcar.sensors;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.environment.WorldObject;

import java.awt.*;
import java.util.List;

public class CameraSensor extends SystemComponent implements ISensor {

    private static final int TRIANGLE_N = 3;
    private static final int VISUAL_RANGE = 80;
    private static final int ANGLE_OF_VIEW = 60;
    private Point positionOnCar;
    private Polygon radarTriangle;

    /**
     * @param virtualFunctionBus This Bus help to communicate with other SystemComponent
     *                           Create Radar sensor
     */
    public CameraSensor(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
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

        Polygon triangle = new Polygon();
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
        radarTriangle = locateSensorTriangle(newSensorPosition, VISUAL_RANGE, ANGLE_OF_VIEW, newSensorRotation);

    }

    @Override
    public List<WorldObject> detectedObjects(List<WorldObject> worldObjects) {
        return null;
    }

    @Override
    public void loop() {
    }

}
