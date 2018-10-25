package hu.oe.nik.szfmv.automatedcar.bus.packets.steering;

public class SteeringPacket implements ReadOnlySteeringPacket {

    int turningAngle;

    public SteeringPacket() {
        this.turningAngle = 0;
    }

    @Override
    public int getTurningAngle() {
        return this.turningAngle;
    }

    @Override
    public void setTurningAngle(int turning) {
        this.turningAngle = turning;
    }
}
