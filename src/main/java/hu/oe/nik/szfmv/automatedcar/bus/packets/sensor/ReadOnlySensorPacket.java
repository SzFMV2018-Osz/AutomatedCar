package hu.oe.nik.szfmv.automatedcar.bus.packets.sensor;

import hu.oe.nik.szfmv.environment.WorldObject;

import java.util.List;

public interface ReadOnlySensorPacket {

    /**
     * Gets the world object in the angle of view of the camera sensor
     *
     * @return List<WorldObject>
     */
    List<WorldObject> getDetectedObjects();

    /**
     * Sets the objects of the list
     *
     * @param worldObjects in the angle of view of the camera sensor
     */
    void setDetectedObjects(List<WorldObject> worldObjects);

    /**
     * Gets the detedted road sign
     * @return road sign
     */
    WorldObject getDetectedRoadSign();

    /**
     * Sets the detected road sign
     * @param detectedRoadSign detected road sign
     */
    void setDetectedRoadSign(WorldObject detectedRoadSign);
}
