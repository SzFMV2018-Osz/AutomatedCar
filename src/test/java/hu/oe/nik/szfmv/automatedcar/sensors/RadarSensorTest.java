package hu.oe.nik.szfmv.automatedcar.sensors;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.environment.WorldObject;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RadarSensorTest {

    @Test
    public void locateSensorTriangle() {
        RadarSensor testCamera = new RadarSensor(new VirtualFunctionBus());

        Point sensorLocation = new Point(20, 30);
        double visualRange = 10;
        double angleOfView = 60;
        double rotation = 90;

        Polygon expectedPolygon = new Polygon();
        expectedPolygon.npoints = 3;
        expectedPolygon.xpoints = new int[]{sensorLocation.x, 10, 10};
        expectedPolygon.ypoints = new int[]{sensorLocation.y, 220, 200};

        Polygon actualPolygon = testCamera.locateSensorTriangle(sensorLocation, visualRange, angleOfView, rotation);

        Assert.assertEquals(expectedPolygon.npoints, actualPolygon.npoints);
        Assert.assertArrayEquals(expectedPolygon.xpoints, actualPolygon.xpoints);
        Assert.assertArrayEquals(expectedPolygon.ypoints, actualPolygon.ypoints);
    }

    @Test
    public void detectedObjectsTest(){
        // ARRANGE
        RadarSensor testRadar = new RadarSensor(new VirtualFunctionBus());

        Point sensorLocation = new Point(20, 30);
        double visualRange = 60;
        double angleOfView = 60;
        double rotation = 90;

        testRadar.locateSensorTriangle(sensorLocation, visualRange, angleOfView, rotation);

        WorldObject obj1 = new WorldObject(40,40,"woman.png") ;
        WorldObject obj2 = new WorldObject(10,10,"man.png") ;
        WorldObject obj3 = new WorldObject(100,100,"garage.png") ;

        List<WorldObject> objects = new ArrayList<>();
        objects.add(obj1);
        objects.add(obj2);
        objects.add(obj3);

        // ACT
        List<WorldObject> detectedObjects = testRadar.detectedObjects(objects);

        // ASSERT
        Assert.assertNotNull(detectedObjects);
    }

}