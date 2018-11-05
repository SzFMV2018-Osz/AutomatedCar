package hu.oe.nik.szfmv.automatedcar.sensors;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.environment.WorldObject;
import hu.oe.nik.szfmv.model.Interfaces.Collidable;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.Vector;

import static java.lang.Math.cos;
import static java.lang.Math.round;
import static java.lang.Math.tan;
import static java.lang.StrictMath.sin;
import static java.lang.StrictMath.toRadians;

public class UltrasonicSensor extends SystemComponent implements ISensor {

    public Polygon getTriangle() {
        return triangle;
    }

    Polygon triangle;
    double visualRange;
    double angleOfView;
    int vertshift;
    int horizshift;
    Point sensorPositon;
    double sensorRotation;
    double sensorViewDirection;




    public UltrasonicSensor(VirtualFunctionBus virtualFunctionBus, int vertShift, int horizShift, double sensorViewDirection) {

        super(virtualFunctionBus);
        visualRange=120;
        angleOfView=90;
        this.vertshift=vertShift;
        this.horizshift=horizShift;
        this.sensorViewDirection=sensorViewDirection;
        this.sensorPositon=new Point();

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
        List<WorldObject> detected = null;

        return detected;
    }

    private void SensorPosCarToGlobal(int vertshift, int horizshift, double sensorRotation) {
        sensorPositon.x = (int) (virtualFunctionBus.carPacket.getxPosition() - Math.cos(Math.toRadians(sensorRotation)) * horizshift + Math.sin(Math.toRadians(sensorRotation)) * vertshift);
        sensorPositon.y = (int) (virtualFunctionBus.carPacket.getyPosition() - Math.sin(Math.toRadians(sensorRotation)) * horizshift - Math.cos(Math.toRadians(sensorRotation)) * vertshift);
    }

    /*public WorldObject closestObject(List<WorldObject> detectedObjects)
    {
        if (detectedObjects!=null)
        {
            WorldObject closest=detectedObjects.get(0);
        }
        for (WorldObject item:detectedObjects
             ) {


            
        }
    }*/

    @Override
    public void loop() {


        SensorPosCarToGlobal(this.vertshift,this.horizshift, sensorRotation);
        sensorRotation = 180 - (virtualFunctionBus.carPacket.getCarRotation() % 360);
        triangle=locateSensorTriangle(sensorPositon,visualRange,angleOfView,sensorRotation+sensorViewDirection);



    }
}
