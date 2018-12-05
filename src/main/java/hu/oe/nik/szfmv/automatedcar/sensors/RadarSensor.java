package hu.oe.nik.szfmv.automatedcar.sensors;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.environment.WorldObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.round;
import static java.lang.Math.tan;

public class RadarSensor extends SystemComponent implements ISensor {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final int TRIANGLE_N = 3;
    private static final int VISUAL_RANGE = 600;
    private static final int ANGLE_OF_VIEW = 60;
    private static final int HALF_CIRCLE = 180;
    private static final int WHOLE_CIRCLE = 360;

    private Point positionOnCar;

    private Polygon radarTriangle;

    /**
     * @param virtualFunctionBus This Bus help to communicate with other SystemComponent
     *                           Create Radar sensor
     */
    public RadarSensor(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
        radarTriangle = new Polygon();
        positionOnCar = new Point();
    }

    public Point getPositionOnCar() {
        return positionOnCar;
    }

    public Polygon getPolygon() {
        return radarTriangle;
    }

    @Override
    public Polygon locateSensorTriangle(Point sensorPosition, double visualRange,
                                        double angleOfView, double sensorRotation) {
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

    private Point rotate(Point point, Point sensorLocation, double rotation) {

        double x = sensorLocation.x + (point.x - sensorLocation.x) * Math.cos(rotation)
                - (point.y - sensorLocation.y) * Math.sin(rotation);
        double y = sensorLocation.y + (point.x - sensorLocation.x) * Math.sin(rotation)
                + (point.y - sensorLocation.y) * Math.cos(rotation);

        return new Point((int) x, (int) y);
    }

    @Override
    public void refreshSensor(Point newSensorPosition, double newSensorRotation) {
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
            if (this.radarTriangle.intersects(item.getX(), item.getY(), item.getWidth(), item.getHeight())) {
                detectedObject.add(item);
            }
        });

        return detectedObject;
    }

    @Override
    public void loop() {

        double radarRotation = HALF_CIRCLE + (virtualFunctionBus.carPacket.getCarRotation() % WHOLE_CIRCLE);

        // LOGGER.info("car rotation"  + virtualFunctionBus.carPacket.getCarRotation());

        sensorPosition(radarRotation);
        radarTriangle = locateSensorTriangle(positionOnCar, VISUAL_RANGE, ANGLE_OF_VIEW, radarRotation);
    }

    private void sensorPosition(double radarRotation) {
        double carWidth = virtualFunctionBus.carPacket.getCarWidth();
        positionOnCar.x = (int) (virtualFunctionBus.carPacket.getxPosition() -
                Math.cos(Math.toRadians(radarRotation)) * (carWidth / 2));
        positionOnCar.y = (int) (virtualFunctionBus.carPacket.getyPosition() -
                Math.sin(Math.toRadians(radarRotation)) * (carWidth / 2));

        // LOGGER.info("radar rotation" + radarRotation);

    }
}
