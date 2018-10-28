package hu.oe.nik.szfmv.model.Classes;

import hu.oe.nik.szfmv.environment.WorldObject;

import java.awt.*;

public abstract class Dynamic extends WorldObject {
    /**
     * Creates an object of the virtual world on the given coordinates with the given image.
     *
     * @param x             the initial x coordinate of the object
     * @param y             the initial y coordinate of the object
     * @param imageFileName the filename of the image representing the object in the virtual world
     */

    int oldal = 1;
    Rectangle rectangle;
    int speed;
    boolean clockwise;
    public Dynamic(int x, int y, String imageFileName) {
        super(x, y, imageFileName);
    }

    /**
     *
     * @param x coordinate
     * @param y coordinate
     * @param imageFileName the name of the imagefile used for this type
     * @param m11 transformation
     * @param m12 transformation
     * @param m21 transformation
     * @param m22 transformation
     */
    public Dynamic(int x, int y, String imageFileName, double m11, double m12, double m21, double m22) {
        super(x, y, imageFileName, m11, m12, m21, m22);
    }

    public void setRoute(int width, int height, int speed, boolean clockwise)
    {
        this.rectangle = new Rectangle(this.x,this.y, width, height);
        this.speed = speed;
        this.clockwise = clockwise;
    }

    public void move() {
        if (clockwise) {
            if (oldal == 1) {
                if ((rectangle.y  - rectangle.height) <= y) {
                    y -= speed;
                    this.setRotation(0);
                } else oldal = 2;
            }
            if (oldal == 2) {
                if ((rectangle.x + rectangle.width) >= x) {
                    x += speed;
                    this.setRotation(270);
                } else oldal = 3;
            }
            if (oldal == 3) {
                if (rectangle.y >= y) {
                    y += speed;
                    this.setRotation(180);
                } else oldal = 4;
            }
            if (oldal == 4) {
                if (rectangle.x <= x) {
                    x -= speed;
                    this.setRotation(90);
                } else oldal = 1;
            }
        } else {
            if (oldal == 1) {
                if ((rectangle.x + rectangle.width) >= x) {
                    x += speed;
                    this.setRotation(90);  // 90
                } else oldal = 2;
            }
            if (oldal == 2) {
                if ((rectangle.y  - rectangle.height) <= y) {
                    y -= speed;
                    this.setRotation(0);  // 0
                } else oldal = 3;
            }
            if (oldal == 3) {
                if (rectangle.x <= x) {
                    x -= speed;
                    this.setRotation(270);
                } else oldal = 4;
            }
            if (oldal == 4) {
                if (rectangle.y >= y) {
                    y += speed;
                    this.setRotation(180);
                } else oldal = 1;
            }
        }
    }
}