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
    boolean orajaras;
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

    public void setRoute(int x, int y, int width, int height, int speed, boolean orajaras)
    {
        this.rectangle = new Rectangle(x,y, width, height);
        this.speed = speed;
        this.orajaras = orajaras;
    }

    public void move() {
        float f = 0;
        if (orajaras) {
            if (oldal == 1) {
                if (rectangle.getMaxY() >= y) {
                    y += speed;
                    this.setRotation(f);
                } else oldal = 2;
            }
            if (oldal == 2) {
                if (rectangle.getMinX() <= x) {
                    x -= speed;
                    this.setRotation(f-270);
                } else oldal = 3;
            }
            if (oldal == 3) {
                if (rectangle.getMinY() <= y) {
                    y -= speed;
                    this.setRotation(f+180);
                } else oldal = 4;
            }
            if (oldal == 4) {
                if (rectangle.getMaxX() >= x) {
                    x += speed;
                    this.setRotation(f-90);
                } else oldal = 1;
            }
        } else {
            if (oldal == 1) {
                if (rectangle.getMaxY() >= y) {
                    y += speed;
                } else oldal = 2;
            }
            if (oldal == 2) {
                if (rectangle.getMaxX() >= x) {
                    x += speed;
                } else oldal = 3;
            }
            if (oldal == 3) {
                if (rectangle.getMinY() <= y) {
                    y -= speed;
                } else oldal = 4;
            }
            if (oldal == 4) {
                if (rectangle.getMinX() <= x) {
                    x -= speed;
                } else oldal = 1;
            }
        }
    }
}
