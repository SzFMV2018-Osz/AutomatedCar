package hu.oe.nik.szfmv.automatedcar.bus;

import hu.oe.nik.szfmv.automatedcar.bus.packets.carpacket.ReadOnlyCarPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.powertrain.ReadOnlyPowertrainPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.sample.ReadOnlySamplePacket;

import hu.oe.nik.szfmv.automatedcar.bus.packets.sensor.ReadOnlySensorPacket;
import hu.oe.nik.szfmv.automatedcar.sensors.CameraSensor;
import hu.oe.nik.szfmv.automatedcar.sensors.RadarSensor;
import hu.oe.nik.szfmv.automatedcar.sensors.UltrasonicSensor;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.environment.WorldObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the class for the Virtual Function Bus. Components are only
 * allowed to collect sensory data exclusively using the VFB. The VFB stores the
 * input and output signals, inputs only have setters, while outputs only have
 * getters respectively.
 */
public class VirtualFunctionBus {

    public ReadOnlySamplePacket samplePacket;
    public ReadOnlyPowertrainPacket powertrainPacket;
    public ReadOnlyCarPacket carPacket;
    public ReadOnlySensorPacket sensorPacket;

    public List<WorldObject> worldObjects = new ArrayList<>();

    public ArrayList<UltrasonicSensor> ultrasonicSensors = new ArrayList<>();

    public RadarSensor radarSensor;
    public CameraSensor cameraSensor;

    public boolean DangerOfCollision = false;

    private List<SystemComponent> components = new ArrayList<>();


    /**
     * Registers the provided {@link SystemComponent}
     *
     * @param comp a class that implements @{link ISystemComponent}
     */
    public void registerComponent(SystemComponent comp) {
        components.add(comp);
    }

    /**
     * Calls cyclically the registered {@link SystemComponent}s once the virtual function bus has started.
     */
    public void loop() {
        for (SystemComponent comp : components) {
            comp.loop();
        }
    }
}
