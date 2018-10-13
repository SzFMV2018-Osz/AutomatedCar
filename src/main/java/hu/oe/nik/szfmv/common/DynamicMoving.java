package hu.oe.nik.szfmv.common;

import java.awt.*;

public class DynamicMoving {
    private static final int SECONDS_IN_HOUR = 3600;
    private static final int METERS_IN_KILOMETER = 1000;

    private int acceleration;
    private int angle;
    private int speed;
    private Point vector;

    public DynamicMoving() {
        acceleration = 0;
        angle = 0;
        speed = 0;
        vector = new Point(0, 0);
    }

    public int getAcceleration() {
        return acceleration;
    }

    public int getAngle() {
        return angle;
    }

    public int getSpeed() {
        return speed;
    }

    public Point getVector() {
        return vector;
    }

    public void CalculateNewVector(int acceleration) {
        speed += acceleration;
        double road = ((double) speed / SECONDS_IN_HOUR) * METERS_IN_KILOMETER;
        int newy = (int) (Math.sin((double) angle) * road);
        int newx = (int) (Math.cos((double) angle) * road);
        vector = new Point(newx, newy);
    }
}
