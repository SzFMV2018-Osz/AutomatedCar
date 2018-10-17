package hu.oe.nik.szfmv.automatedcar.bus.packets.carpacket;

public class CarPacket implements ReadOnlyCarPacket {

    private int carHeigth;
    private int carWidth;

    @Override
    public int getCarHeigth() {
        return carHeigth;
    }

    public void setCarHeigth(int carHeigth) {
        this.carHeigth = carHeigth;
    }

    @Override
    public int getCarWidth() {
        return carWidth;
    }

    public void setCarWidth(int carWidth) {
        this.carWidth = carWidth;
    }
}
