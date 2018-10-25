package hu.oe.nik.szfmv.automatedcar.bus.packets.steering;

public interface ReadOnlySteeringPacket {

    /**
     * Gets the value of the turning.
     *
     * @return int (rpm)
     */
    int getTurningAngle();
    /**
     * Sets the value of the turningAngle
     *
     * @param turning of the engine
     */
    void setTurningAngle(int turning);
}
