package hu.oe.nik.szfmv.automatedcar.bus.packets.powertrain;

import hu.oe.nik.szfmv.common.Vector;

public interface ReadOnlyPowertrainPacket {

    /**
     * Gets the engine revolution (revolution/minute)
     *
     * @return int (rpm)
     */
    int getRpm();

    /**
     * Sets the value of the rpm of the engine
     *
     * @param rpm of the engine
     */
    void setRpm(int rpm);

    /**
     * Gets the car actual speed
     * If it is a positive number, the car moves forward
     * If it is a negative number, the car moves backward
     *
     * @return dobule
     */
    double getSpeed();

    /**
     * Sets the value of the speed of the car
     *
     * @param speed the speed of the car
     */
    void setSpeed(double speed);
    /**
     * Gets the value of the turning.
     *
     * @return int (rpm)
     */
    Vector getVector();
    /**
     * Sets the value of the turningAngle
     *
     * @param vector of the engine
     */
    void setVector(Vector vector);
}
