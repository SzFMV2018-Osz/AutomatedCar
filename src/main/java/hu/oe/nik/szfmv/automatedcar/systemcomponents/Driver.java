package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.inputposition.InputPositionPacket;

public class Driver extends SystemComponent {

    private final InputPositionPacket inputPositionPacket;

    /**
     * @param virtualFunctionBus Create a Driver with Virtual Function Bus
     */
    public Driver(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
        inputPositionPacket = new InputPositionPacket();
        virtualFunctionBus.readOnlyInputPositionPacket = inputPositionPacket;
    }

    @Override
    public void loop() {
        inputPositionPacket.setGaspedalPosition(5);
    }
}