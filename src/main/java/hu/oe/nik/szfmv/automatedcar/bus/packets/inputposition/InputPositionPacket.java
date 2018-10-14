package hu.oe.nik.szfmv.automatedcar.bus.packets.inputposition;

public class InputPositionPacket implements ReadOnlyInputPositionPacket {
    private int gearState;
    private int gaspedalPosition;
    private int breakpedalPosition;
    private int steeringWheelPosition;

    /**
     * Create a Sample Packet
     */
    public InputPositionPacket() {
        this.gearState = 0;
        this.gaspedalPosition = 0;
        this.breakpedalPosition = 0;
        this.steeringWheelPosition = 0;
    }

    @Override
    public int getGaspedalPosition() {
        return gaspedalPosition;
    }

    @Override
    public void setGaspedalPosition(int gaspedalPosition) {
        this.gaspedalPosition = gaspedalPosition;
    }

    @Override
    public int getGearState() {
        return gearState;
    }

    @Override
    public void setGearState(int gearState) {
        this.gearState = gearState;
    }

    @Override
    public int getSteeringWheelPosition() {
        return steeringWheelPosition;
    }

    @Override
    public void setSteeringWheelPosition(int steeringWheelPosition) {
        this.steeringWheelPosition = steeringWheelPosition;
    }

    @Override
    public int getBreakpedalPosition() {
        return breakpedalPosition;
    }

    @Override
    public void setBreakpedalPosition(int breakpedalPosition) {
        this.breakpedalPosition = breakpedalPosition;
    }

    // TODO implement all of the HMI signals
}
