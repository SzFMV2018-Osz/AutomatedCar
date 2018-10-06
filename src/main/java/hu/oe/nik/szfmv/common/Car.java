package hu.oe.nik.szfmv.common;

import javax.swing.*;

public class Car {
    private int width;
    private int wheelbase;

    public int getWidth() {
        return width;
    }

    public int getWheelbase() {
        return wheelbase;
    }

    public Car() {
        ImageIcon iid = new ImageIcon(this.getClass().getClassLoader().getResource("car_1_red.png"));
        width = iid.getIconWidth();
        wheelbase = iid.getIconHeight();
    }
}
