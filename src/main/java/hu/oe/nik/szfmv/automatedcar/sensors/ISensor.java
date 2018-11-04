package hu.oe.nik.szfmv.automatedcar.sensors;

import hu.oe.nik.szfmv.environment.WorldObject;

import java.awt.*;
import java.util.List;


public interface ISensor {
    Polygon locateSensorTriangle(Point sensorPosition, double visualRange, double angelOfView, double sensorRotation);

    void refreshSensor(Point newSensorPosition, double newSensorRotation);

    List<WorldObject> detectedObjects(List<WorldObject> worldObjects);
}
