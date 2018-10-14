package hu.oe.nik.szfmv.common;

import javax.swing.*;

public class Car {
    private int width;
    private int wheelbase;

    public Car(String image) {
        ImageIcon iid = new ImageIcon(this.getClass().getClassLoader().getResource(image));
        width = iid.getIconWidth();
        wheelbase = iid.getIconHeight();
    }

    public int getWidth() {
        return width;
    }

    public int getWheelbase() {
        return wheelbase;
    }
}
