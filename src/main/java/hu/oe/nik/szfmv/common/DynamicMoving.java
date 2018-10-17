package hu.oe.nik.szfmv.common;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.SteeringSystem;

import java.awt.*;

public class DynamicMoving {
    private static final int SECONDS_IN_HOUR = 3600;
    private static final int METERS_IN_KILOMETER = 1000;
    private int acceleration;
    private int speed;
    private Point vector;
    private SteeringSystem steeringSystem;

    /**
     * @param steeringSystem Constructor of the DynamicMoving class
     */
    public DynamicMoving(SteeringSystem steeringSystem) {
        speed = 0;
        vector = new Point(0, 0);
        this.steeringSystem = steeringSystem;
    }

    public int getSpeed() {
        return speed;
    }

    public Point getVector() {
        return vector;
    }

    public SteeringSystem getSteeringSystem() {
        return steeringSystem;
    }

    /**
     * Calculate the new velocity vector
     *
     * @param speedDelta the difference between the old and the new speed
     */
    public void calculateNewVector(double speedDelta) {
        speed += speedDelta;
        double road = ((double) speed / SECONDS_IN_HOUR) * METERS_IN_KILOMETER;
        int newY = (int) (Math.sin((double) steeringSystem.getTurningCircle()) * road);
        int newX = (int) (Math.cos((double) steeringSystem.getTurningCircle()) * road);
        vector = new Point(newX, newY);
    }
}
