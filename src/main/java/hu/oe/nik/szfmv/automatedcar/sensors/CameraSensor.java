package hu.oe.nik.szfmv.automatedcar.sensors;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.environment.WorldObject;

import java.awt.*;
import java.util.List;

public class CameraSensor extends SystemComponent implements ISensor {

    private static final int TRIANGLE_N = 3;

    /**
     * @param virtualFunctionBus This Bus help to communicate with other SystemComponent
     *                           Create Radar sensor
     */
    public CameraSensor(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
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

        leftPoint.x = (int) (sensorPosition.x + (leftPoint.x - sensorPosition.x) * Math.cos(sensorRotationInRadian)
                - (leftPoint.y - sensorPosition.y) * Math.sin(sensorRotationInRadian));
        leftPoint.y = (int) (sensorPosition.y + (leftPoint.x - sensorPosition.x) * Math.sin(sensorRotationInRadian)
                + (leftPoint.y - sensorPosition.y) * Math.cos(sensorRotationInRadian));
        rightPoint.x = (int) (sensorPosition.x + (rightPoint.x - sensorPosition.x) * Math.cos(sensorRotationInRadian)
                - (rightPoint.y - sensorPosition.y) * Math.sin(sensorRotationInRadian));
        rightPoint.y = (int) (sensorPosition.y + (rightPoint.x - sensorPosition.x) * Math.sin(sensorRotationInRadian)
                + (rightPoint.y - sensorPosition.y) * Math.cos(sensorRotationInRadian));

        Polygon triangle = new Polygon();
        triangle.npoints = TRIANGLE_N;
        triangle.xpoints = new int[]{sensorPosition.x, leftPoint.x, rightPoint.x};
        triangle.xpoints = new int[]{sensorPosition.y, leftPoint.y, rightPoint.y};

        return triangle;
    }

    @Override
    public void refreshSensor(Point newSensorPosition, double newSensorRotation) {

    }

    @Override
    public List<WorldObject> detectedObjects(List<WorldObject> worldObjects) {
        return null;
    }

    @Override
    public void loop() {

    }
}
