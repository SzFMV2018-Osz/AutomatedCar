package hu.oe.nik.szfmv.common;

import java.awt.*;

public final class Resistences {

    private static double cDrag = 0.4257;
    private static double cRolling = 12.8;

    /**
     * Calculating the air resistance
     * @param velocityVector vector
     * @return the vector of the air resistance
     */
    public static Point calculateAirResistance(Point velocityVector) {
        double speed = Math.sqrt(Math.pow(velocityVector.x, 2) + Math.pow(velocityVector.y, 2));
        Point fDrag = new Point();
        fDrag.x = (int) (-cDrag * velocityVector.x * speed);
        fDrag.y = (int) (-cDrag * velocityVector.y * speed);
        return fDrag;

    }

    /**
     * Calculating the rolling resistance
     * @param velocityVector vector
     * @return the vector of the rolling resistance
     */
    public static Point calulateRollingResistance(Point velocityVector) {
        Point fRolling = new Point();
        fRolling.x = (int) (-cRolling * velocityVector.x);
        fRolling.y = (int) (-cRolling * velocityVector.y);
        return fRolling;
    }
}