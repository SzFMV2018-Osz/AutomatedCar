package hu.oe.nik.szfmv.common;

import java.awt.*;

public final class Resistences {

    private static final double C_DRAG = 0.4257;
    private static final double C_ROLLING = 12.8;

    /**
     * Calculating the air resistance
     * @param velocityVector vector
     * @return the vector of the air resistance
     */
    public static Point calculateAirResistance(Point velocityVector) {
        double speed = Math.sqrt(Math.pow(velocityVector.x, 2) + Math.pow(velocityVector.y, 2));
        Point fDrag = new Point();
        fDrag.x = (int) (-C_DRAG * velocityVector.x * speed);
        fDrag.y = (int) (-C_DRAG * velocityVector.y * speed);
        return fDrag;

    }

    /**
     * Calculating the rolling resistance
     * @param velocityVector vector
     * @return the vector of the rolling resistance
     */
    public static Point calulateRollingResistance(Point velocityVector) {
        Point fRolling = new Point();
        fRolling.x = (int) (-C_ROLLING * velocityVector.x);
        fRolling.y = (int) (-C_ROLLING * velocityVector.y);
        return fRolling;
    }
}