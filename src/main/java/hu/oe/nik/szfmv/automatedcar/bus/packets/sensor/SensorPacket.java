package hu.oe.nik.szfmv.automatedcar.bus.packets.sensor;

import hu.oe.nik.szfmv.environment.WorldObject;

import java.util.ArrayList;
import java.util.List;

public class SensorPacket implements ReadOnlySensorPacket {

    List<WorldObject> detectedObjects;

    /**
     * PowertrainPacket consturctor
     */
    public SensorPacket() {
        detectedObjects = new ArrayList<>();
    }
    @Override
    public List<WorldObject> getDetectedObjects() {
        return detectedObjects;
    }

    @Override
    public void setDetectedObjects(List<WorldObject> worldObjects) {
        detectedObjects = worldObjects;
    }
}
