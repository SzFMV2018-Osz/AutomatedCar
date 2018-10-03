package hu.oe.nik.szfmv.common;

import java.awt.*;

public final class Resistences {

    private static double cDrag = 0.5;
    private static double cRolling = 1.5;

    public static Point calculateAirResistance(Point v) {
        double speed = Math.sqrt(Math.pow(v.x, 2) + Math.pow(v.y, 2));
        Point fDrag = new Point();
        fDrag.x = (int) (-cDrag * v.x * speed);
        fDrag.y = (int) (-cDrag * v.y * speed);
        return fDrag;

    }

    public static Point calulateRollingResistance(Point v) {
        Point fRolling = new Point();
        fRolling.x = (int) (-cRolling * v.x);
        fRolling.y = (int) (-cRolling * v.y);
        return fRolling;
    }
}