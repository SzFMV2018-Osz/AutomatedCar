package hu.oe.nik.szfmv.automatedcar.bus.packets.powertrain;

import hu.oe.nik.szfmv.common.Vector;

public class PowertrainPacket implements ReadOnlyPowertrainPacket {

    private int rmp;
    private double speed;
    Vector vector;

    /**
     * PowertrainPacket consturctor
     */
    public PowertrainPacket() {
        this.rmp = 0;
        this.speed = 0d;
        vector = new Vector(0,0);
    }

    @Override
    public int getRpm() {
        return this.rmp;
    }

    @Override
    public void setRpm(int rpm) {
        this.rmp = rpm;
    }

    @Override
    public double getSpeed() {
        return this.speed;
    }

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public Vector getVector() {
        return this.vector;
    }

    @Override
    public void setVector(Vector vector) {
        this.vector = vector;
    }
}
