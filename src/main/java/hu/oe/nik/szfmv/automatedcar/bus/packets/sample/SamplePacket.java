package hu.oe.nik.szfmv.automatedcar.bus.packets.sample;

public class SamplePacket implements ReadOnlySamplePacket {
    private int gaspedalPosition = 0;
    private int breakpedalPosition = 0;
    private int steeringWheelPosition = 0;

    /**
     * Create a Sample Packet
     */
    public SamplePacket() {
    }

    @Override
    public int getSteeringWheelPosition() {
        return steeringWheelPosition;
    }

    public void setSteeringWheelPosition(int steeringWheelPosition) {
        this.steeringWheelPosition = steeringWheelPosition;
    }

    @Override
    public int getBreakpedalPosition() {
        return breakpedalPosition;
    }

    public void setBreakpedalPosition(int breakpedalPosition) {
        this.breakpedalPosition = breakpedalPosition;
    }

    public int getGaspedalPosition() {
        return this.gaspedalPosition;
    }
    
    public void setGaspedalPosition(int gaspedalPosition) {
        this.gaspedalPosition = gaspedalPosition;
    }

    // TODO implement all of the HMI signals
}
