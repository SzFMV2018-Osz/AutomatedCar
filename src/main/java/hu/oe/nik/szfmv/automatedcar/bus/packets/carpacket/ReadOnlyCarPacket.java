package hu.oe.nik.szfmv.automatedcar.bus.packets.carpacket;

public interface ReadOnlyCarPacket {

    /**
     * @return the heigth of the car
     */
    int getCarHeigth();

    /**
     * @return the width of the car
     */
    int getCarWidth();
}
