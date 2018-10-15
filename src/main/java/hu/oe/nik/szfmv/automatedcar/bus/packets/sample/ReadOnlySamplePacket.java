package hu.oe.nik.szfmv.automatedcar.bus.packets.sample;

public interface ReadOnlySamplePacket {
    int getGaspedalPosition();

    int getBreakpedalPosition();

    int getWheelPosition();

    String getGear();
}
