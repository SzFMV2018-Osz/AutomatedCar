package hu.oe.nik.szfmv.common;

import java.awt.*;

public final class Resistences {

    private static double cDrag = 0.4257;
    private static double cRolling = 12.8;

    public static Point calculateAirResistance(Point velocityVector) {
        double speed = Math.sqrt(Math.pow(velocityVector.x, 2) + Math.pow(velocityVector.y, 2));
        Point fDrag = new Point();
        fDrag.x = (int) (-cDrag * velocityVector.x * speed);
        fDrag.y = (int) (-cDrag * velocityVector.y * speed);
        return fDrag;

    }

    public static Point calulateRollingResistance(Point velocityVector) {
        Point fRolling = new Point();
        fRolling.x = (int) (-cRolling * velocityVector.x);
        fRolling.y = (int) (-cRolling * velocityVector.y);
        return fRolling;
    }
}