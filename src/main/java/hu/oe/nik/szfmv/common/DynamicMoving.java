package hu.oe.nik.szfmv.common;

import java.awt.*;

public class DynamicMoving {
    private int acceleration;
    private int angle;
    private int speed;
    private Point vector;

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

    public DynamicMoving() {
        acceleration = 0;
        angle = 0;
        speed = 0;
        vector = new Point(0,0);
    }

    public void CalculateNewVector(int acceleration){
        speed += acceleration;
        double road = ((double)speed/3600)*1000;
        int newy = (int)(Math.sin((double)angle)*road);
        int newx = (int)(Math.cos((double)angle)*road);
        vector = new Point(newx,newy);
    }
}
