package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.sample.SamplePacket;

public class Driver extends SystemComponent {

    private final SamplePacket inputPositionPacket;

    /**
     * @param virtualFunctionBus Create a Driver with Virtual Function Bus
     */
    public Driver(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
        inputPositionPacket = new SamplePacket();
        virtualFunctionBus.samplePacket = inputPositionPacket;
    }

    @Override
    public void loop() {
    }
}