package hu.oe.nik.szfmv.common;

import java.awt.*;

public class DynamicMoving {
    private int acceleration;
    private int speed;
    private Point vector;
    private Car car;
    private int turningCircle;
    protected static final int SECUNDS_IN_HOUR = 3600;
    protected static final int METERS_IN_KILOMETER = 1000;

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

    public void calculateNewVector(int acceleration) {
        speed += acceleration;
        double road = ((double) speed / SECUNDS_IN_HOUR) * METERS_IN_KILOMETER;
        int newy = (int) (Math.sin((double) turningCircle) * road);
        int newx = (int) (Math.cos((double) turningCircle) * road);
        vector = new Point(newx, newy);
    }
}
