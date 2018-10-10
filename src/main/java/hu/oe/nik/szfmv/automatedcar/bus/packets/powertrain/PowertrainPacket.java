package hu.oe.nik.szfmv.automatedcar.bus.packets.powertrain;

public class PowertrainPacket implements ReadOnlyPowertrainPacket {

    private int rmp;
    private double speed;

    /**
     * PowertrainPacket consturctor
     */
    public PowertrainPacket() {
        this.rmp = 0;
        this.speed = 0d;
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
}
