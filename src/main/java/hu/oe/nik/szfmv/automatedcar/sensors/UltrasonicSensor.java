package hu.oe.nik.szfmv.automatedcar.sensors;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.environment.WorldObject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.round;
import static java.lang.Math.tan;
import static java.lang.StrictMath.sin;
import static java.lang.StrictMath.toRadians;

public class UltrasonicSensor extends SystemComponent implements ISensor {

    Polygon poly;

    public UltrasonicSensor(VirtualFunctionBus virtualFunctionBus) {

        super(virtualFunctionBus);
    }

    public Polygon locateSensorTriangle(Point sensorPosition, double visualRange, double angleOfView,
            double sensorRotation) {

        double rotationInRad = toRadians(sensorRotation);
        double angleInRad = toRadians(angleOfView);

        Point a = new Point((int) round(sensorPosition.x - tan(angleInRad / 2) * visualRange),
                (int) round(sensorPosition.y + visualRange));
        Point b = new Point((int) round(sensorPosition.x + tan(angleInRad / 2) * visualRange),
                (int) round(sensorPosition.y + visualRange));

        a = rotate(a, sensorPosition, rotationInRad);
        b = rotate(b, sensorPosition, rotationInRad);

        Polygon triangle = new Polygon(new int[] { sensorPosition.x, a.x, b.x },
                new int[] { sensorPosition.y, a.y, b.y }, 3);

        poly = triangle;

        return triangle;

    }

    public void refreshSensor(Point newSensorPosition, double newSensorRotation) {

    }

    private Point rotate(Point point, Point center, double rotation) {

        double x = center.x + (point.x - center.x) * Math.cos(rotation) - (point.y - center.y) * Math.sin(rotation);
        double y = center.y + (point.x - center.x) * Math.sin(rotation) + (point.y - center.y) * Math.cos(rotation);

        return new Point((int) x, (int) y);
    }

    public List<WorldObject> detectedObjects(List<WorldObject> worldObjects) {
        List<WorldObject> detected = new ArrayList<>();
        for (WorldObject WO : worldObjects) {
            if (poly.intersects(WO.getX(), WO.getY(), WO.getWidth(), WO.getHeight())) {
                detected.add(WO);
            }
        }
        return detected;
    }

    @Override
    public void loop() {

    }
}
