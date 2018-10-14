package hu.oe.nik.szfmv.common;

import java.awt.*;

public class DynamicMoving {
    private static final int SECONDS_IN_HOUR = 3600;
    private static final int METERS_IN_KILOMETER = 1000;

    private int acceleration;
    private int angle;
    private double speed;
    private Point vector;

    /**
     * Constructor of the DynamicMoving class
     */
    public DynamicMoving() {
        acceleration = 0;
        angle = 0;
        speed = 0d;
        vector = new Point(0, 0);
    }

    public int getAcceleration() {
        return acceleration;
    }

    public int getAngle() {
        return angle;
    }

    public double getSpeed() {
        return speed;
    }

    public Point getVector() {
        return vector;
    }

    /**
     * Calculate the new velocity vector
     * @param speedDelta the difference between the old and the new speed
     */
    public void calculateNewVector(double speedDelta) {
        speed += speedDelta;
        double road = (speed / SECONDS_IN_HOUR) * METERS_IN_KILOMETER;
        int newY = (int) (Math.sin((double) angle) * road);
        int newX = (int) (Math.cos((double) angle) * road);
        vector = new Point(newX, newY);
    }
}
