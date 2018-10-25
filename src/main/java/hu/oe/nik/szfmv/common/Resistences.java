package hu.oe.nik.szfmv.common;

public final class Resistences {

    private static final double C_DRAG = 0.4257;
    private static final double C_ROLLING = 12.8;

    /**
     * Calculating the air resistance
     * @param velocityVector vector
     * @return the vector of the air resistance
     */
    public static Vector calculateAirResistance(Vector velocityVector) {
        double speed = Math.sqrt(Math.pow(velocityVector.getX(), 2) + Math.pow(velocityVector.getY(), 2));
        Vector fDrag = new Vector();
        fDrag.setX(-C_DRAG * velocityVector.getX() * speed);
        fDrag.setY(-C_DRAG * velocityVector.getY() * speed);
        return fDrag;

    }

    /**
     * Calculating the rolling resistance
     * @param velocityVector vector
     * @return the vector of the rolling resistance
     */
    public static Vector calulateRollingResistance(Vector velocityVector) {
        Vector fRolling = new Vector();
        fRolling.setX(-C_ROLLING * velocityVector.getX());
        fRolling.setY(-C_ROLLING * velocityVector.getY());
        return fRolling;
    }
}