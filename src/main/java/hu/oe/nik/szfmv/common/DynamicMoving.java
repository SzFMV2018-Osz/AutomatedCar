package hu.oe.nik.szfmv.common;

import java.awt.*;

public class DynamicMoving {
    private int acceleration;
    private int speed;
    private Point vector;
    private Car car;
    private int turningCircle;
    protected static final int SECONDS_IN_HOUR = 3600;
    protected static final int METERS_IN_KILOMETER = 1000;

    /**
     * Constructor of the DynamicMoving class
     */
    public DynamicMoving() {
        acceleration = 0;
        speed = 0;
        vector = new Point(0, 0);
        car = new Car("car_1_red.png");
        turningCircle = 0;
    }

    public int getAcceleration() {
        return acceleration;
    }

    public int getSpeed() {
        return speed;
    }

    public Point getVector() {
        return vector;
    }

    public Car getCar() {
        return car;
    }

    public int getTurningCircle() {
        return turningCircle;
    }

    public void calculateTurningCircle(int turningDegree) {
        turningCircle = (int) (car.getWheelbase() / Math.tan(turningDegree) + car.getWidth());
    }

    /**
     * Calculate the new velocity vector
     * @param speedDelta the difference between the old and the new speed
     */
    public void calculateNewVector(double speedDelta) {
        speed += speedDelta;
        double road = (speed / SECONDS_IN_HOUR) * METERS_IN_KILOMETER;
        int newY = (int) (Math.sin((double) turningCircle) * road);
        int newX = (int) (Math.cos((double) turningCircle) * road);
        vector = new Point(newX, newY);
    }
}
