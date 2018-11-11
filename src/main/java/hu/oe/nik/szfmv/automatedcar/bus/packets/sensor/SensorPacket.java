package hu.oe.nik.szfmv.automatedcar.bus.packets.sensor;

import hu.oe.nik.szfmv.environment.WorldObject;

import java.util.ArrayList;
import java.util.List;

public class SensorPacket implements ReadOnlySensorPacket {

    private List<WorldObject> detectedObjects;
    private WorldObject detectedRoadSign;
    private double distanceOfRoadSign;
    private Boolean leftLane;
    private double distanceFromBound;

    /**
     * PowertrainPacket consturctor
     */
    public SensorPacket() {
        this.detectedObjects = new ArrayList<>();
        this.distanceOfRoadSign = 0d;
        leftLane = null;
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

    @Override
    public double getDistanceOfRoadSign() {
        return this.distanceOfRoadSign;
    }

    @Override
    public void setDistanceOfRoadSign(double distanceOfRoadSign) {
        this.distanceOfRoadSign = distanceOfRoadSign;
    }

    @Override
    public Boolean getIfWeAreInLeftLane() {
        return leftLane;
    }

    @Override
    public void setIfWeAreInLeftLane(Boolean leftLane) {
        this.leftLane = leftLane;
    }

    @Override
    public double getDistanceFromBound() {
        return distanceFromBound;
    }

    @Override
    public void setDistanceFromBound(double distanceFromBound) {
        this.distanceFromBound = distanceFromBound;
    }
}
