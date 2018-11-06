package hu.oe.nik.szfmv.automatedcar.sensors;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.sensor.SensorPacket;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.environment.WorldObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CameraSensor extends SystemComponent implements ISensor {

    private static final int TRIANGLE_N = 3;
    private Point sensorPosition;
    Polygon triangle;
    private boolean rightLane;
    private int distanceFromBorder;

    /**
     * @param virtualFunctionBus This Bus help to communicate with other SystemComponent
     *                           Create Radar sensor
     */
    public CameraSensor(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);

        this.virtualFunctionBus.sensorPacket = new SensorPacket();
        this.sensorPosition = new Point(this.virtualFunctionBus.carPacket.getxPosition(),
                this.virtualFunctionBus.carPacket.getyPosition()+this.virtualFunctionBus.carPacket.getCarWidth()/2);
        triangle = new Polygon();
    }

    @Override
    public Polygon locateSensorTriangle(Point sensorPosition, double visualRange,
                                        double angelOfView, double sensorRotation) {
        this.sensorPosition = sensorPosition;
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
        locateSensorTriangle(newSensorPosition,0, 0, newSensorRotation);
    }

    @Override
    public List<WorldObject> detectedObjects(List<WorldObject> worldObjects) {
        List<WorldObject> list = new ArrayList<>();
        for (WorldObject worldObject : worldObjects){
            Rectangle rectangle = new Rectangle(worldObject.getX(), worldObject.getY(), worldObject.getWidth(), worldObject.getHeight());
            if(triangle.intersects(rectangle))
                list.add(worldObject);
        }
        return list;
    }

    @Override
    public void loop() {

    }
}
