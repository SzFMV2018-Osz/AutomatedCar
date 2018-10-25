package hu.oe.nik.szfmv.model.Classes;

import hu.oe.nik.szfmv.model.Interfaces.ICollidable;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.Random;
import java.util.Vector;

public class Tree extends RoadObsticle implements ICollidable {

    /**
     *
     * @param x coordinate
     * @param y coordinate
     * @param imageFileName the name of the imagefile used for this type
     */
    /*int oldal = 1;
    Rectangle rectangle;
    int speed;
    boolean orajaras;*/
    public Tree(int x, int y, String imageFileName) {
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
    public Tree(int x, int y, String imageFileName, double m11, double m12, double m21, double m22) {
        super(x, y, imageFileName, m11, m12, m21, m22);
    }
/*
    public void setRoute(int x, int y, int width, int height, int speed, boolean orajaras)
    {
        this.rectangle = new Rectangle(x,y, width, height);
        this.speed = speed;
        this.orajaras = orajaras;
    }

    public void move() {
        if (orajaras) {
            if (oldal == 1) {
                if (rectangle.getMaxY() >= y) {
                    y += speed;
                } else oldal = 2;
            }
            if (oldal == 2) {
                if (rectangle.getMinX() <= x) {
                    x -= speed;
                } else oldal = 3;
            }
            if (oldal == 3) {
                if (rectangle.getMinY() <= y) {
                    y -= speed;
                } else oldal = 4;
            }
            if (oldal == 4) {
                if (rectangle.getMaxX() >= x) {
                    x += speed;
                } else oldal = 1;
            }
        }
        else
        {
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

        /*if (rectangle.getMaxX() <= x) {
            x -= 1;
        }
        if (rectangle.getMinX() >= x) {
            x += 1;
        }
        if (rectangle.getMaxY() <= y) {
            y -= 1;
        }
        if (rectangle.getMinY() >= y) {
            y += 1;
        }
        Random random = new Random();
        Random sRandom = new Random();
        int speed = sRandom.nextInt(6);
        int num = random.nextInt(4);
        if (num == 0)
        {
            x += speed;
        }
        if (num == 1)
        {
            x -= speed;
        }
        if (num == 2)
        {
            y += speed;
        }
        if (num == 3)
        {
            y -= speed;
        }
    }*/

}
