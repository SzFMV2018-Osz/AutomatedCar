package hu.oe.nik.szfmv.automatedcar.bus.packets.sensor;

import hu.oe.nik.szfmv.environment.WorldObject;

import java.util.ArrayList;
import java.util.List;

public class SensorPacket implements ReadOnlySensorPacket {

    private List<WorldObject> cameraDetectedObjects;
    private List<WorldObject> radarDetectedObjects;
    private WorldObject detectedRoadSign;
    private double distanceOfRoadSign;

    /**
     * PowertrainPacket consturctor
     */
    public SensorPacket() {
        this.cameraDetectedObjects = new ArrayList<>();
        this.radarDetectedObjects = new ArrayList<>();
        this.distanceOfRoadSign = 0d;
    }
    @Override
    public List<WorldObject> getCameraDetectedObjects() {
        return cameraDetectedObjects;
    }

    @Override
    public void setCameraDetectedObjects(List<WorldObject> worldObjects) {
        cameraDetectedObjects = worldObjects;
    }

    @Override
    public WorldObject getDetectedRoadSign() {
        return this.detectedRoadSign;
    }

    @Override
    public void setDetectedRoadSign(WorldObject detectedRoadSign) {
        this.detectedRoadSign = detectedRoadSign;
    }

    @Override
    public double getDistanceOfRoadSign() {
        return this.distanceOfRoadSign;
    }

    @Override
    public void setDistanceOfRoadSign(double distanceOfRoadSign) {
        this.distanceOfRoadSign = distanceOfRoadSign;
    }

    @Override
    public List<WorldObject> getRadarDetectedObjects() {
        return radarDetectedObjects;
    }

    @Override
    public void setRadarDetectedObjects(List<WorldObject> radarDetectedObjects) {
        this.radarDetectedObjects = radarDetectedObjects;
    }
}
