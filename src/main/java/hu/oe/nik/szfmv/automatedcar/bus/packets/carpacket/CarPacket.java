package hu.oe.nik.szfmv.automatedcar.bus.packets.carpacket;

public class CarPacket implements ReadOnlyCarPacket {

    private int carHeigth;
    private int carWidth;
    private int xPosition;
    private int yPosition;

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

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