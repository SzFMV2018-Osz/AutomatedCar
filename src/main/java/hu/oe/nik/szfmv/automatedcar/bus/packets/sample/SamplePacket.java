package hu.oe.nik.szfmv.automatedcar.bus.packets.sample;

public class SamplePacket implements ReadOnlySamplePacket {
    private int gaspedalPosition = 0;

    /**
     * Create a Sample Packet
     */
    public SamplePacket() {
    }

    @Override
    public int getGaspedalPosition() {
        return gaspedalPosition;
    }

    // TODO implement all of the HMI signals
}
