package hu.oe.nik.szfmv.automatedcar.bus.packets.sensor;

import hu.oe.nik.szfmv.environment.WorldObject;

import java.util.ArrayList;
import java.util.List;

public class SensorPacket implements ReadOnlySensorPacket {

    private List<WorldObject> detectedObjects;
    private WorldObject detectedRoadSign;

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

    @Override
    public WorldObject getDetectedRoadSign() {
        return this.detectedRoadSign;
    }

    @Override
    public void setDetectedRoadSign(WorldObject detectedRoadSign) {
        this.detectedRoadSign = detectedRoadSign;
    }
}
