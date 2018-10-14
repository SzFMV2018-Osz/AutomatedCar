package hu.oe.nik.szfmv.automatedcar.bus.packets.inputposition;

public interface ReadOnlyInputPositionPacket {


    /**
     * @return Gaspedal Position
     * Get the Gaspedal Position
     */
    int getGaspedalPosition();

    /**
     * @param gaspedalPosition Set the Gaspedal Position
     */
    void setGaspedalPosition(int gaspedalPosition);

    /**
     * @return Breakpedal Position
     * Get the Breakpedal Position
     */
    int getBreakpedalPosition();

    /**
     * @param breakpedalPosition Set the Breakpedal Position
     */
    void setBreakpedalPosition(int breakpedalPosition);

    /**
     * @return Steering Wheel Position
     * Get the Steering Wheel Position
     */
    int getSteeringWheelPosition();

    /**
     * @param steeringWheelPosition Set the Steering Wheel Position
     */
    void setSteeringWheelPosition(int steeringWheelPosition);

    /**
     * @return Gear State
     * Get the Gear State
     */
    int getGearState();

    /**
     * @param gearState Set the Gear State
     */
    void setGearState(int gearState);
}
