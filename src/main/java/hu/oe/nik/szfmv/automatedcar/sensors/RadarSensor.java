package hu.oe.nik.szfmv.automatedcar.sensors;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.environment.WorldObject;

import java.awt.*;
import java.util.List;

public class RadarSensor extends SystemComponent implements ISensor {

    /**
     * @param virtualFunctionBus This Bus help to communicate with other SystemComponent
     * Create Radar sensor
     */
    public RadarSensor(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
    }

    @Override
    public Polygon locateSensorTriangle(Point sensorPosition, double visualRange,
                                        double angelOfView, double sensorRotation) {
        return null;
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
