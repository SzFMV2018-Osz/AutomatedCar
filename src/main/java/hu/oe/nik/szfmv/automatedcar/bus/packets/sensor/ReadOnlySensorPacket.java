package hu.oe.nik.szfmv.automatedcar.bus.packets.sensor;

import hu.oe.nik.szfmv.environment.WorldObject;

import java.util.List;

public interface ReadOnlySensorPacket {
    List<WorldObject> getDetectedObjects();
    void setDetectedObjects(List<WorldObject> worldObjects);
}
