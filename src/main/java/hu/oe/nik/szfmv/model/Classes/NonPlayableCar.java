package hu.oe.nik.szfmv.model.Classes;

import hu.oe.nik.szfmv.model.Interfaces.ICollidable;

import java.math.*;


public class NonPlayableCar extends Car implements ICollidable {
    int actualpos = 0;
    int speed = 20;
    int rotatio = 0;
    double xact =0;
    double yact = 0;

    public int getActualpos() {
        return actualpos;
    }

    public void setActualpos(int actualpos) {
        this.actualpos = actualpos;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


    /**
     * Creates an object of the virtual world on the given coordinates with the given image.
     *
     * @param x             the initial x coordinate of the object
     * @param y             the initial y coordinate of the object
     * @param imageFileName the filename of the image representing the object in the virtual world
     */
    public NonPlayableCar(int x, int y, String imageFileName) {
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
    public NonPlayableCar(int x, int y, String imageFileName, double m11, double m12, double m21, double m22) {
        super(x, y, imageFileName, m11, m12, m21, m22);
    }

    public void movecar() {
        switch (actualpos){
            case 0:
                y-=speed;
                if (y <= 610)
                {
                    y = 610;
                    actualpos++;
                    xact = x;
                    yact = y;
                }

                break;
            case 1:

             //   double yspeed =(double) (250.0 / 90.0);
             //   double xspeed =(double) (400.0 / 90.0);
              double  xact = (double) (740.0 + 400.0 * Math.cos((180.0+rotatio) * Math.PI / 180.0));
              double  yact = (double) (610.0 + 250.0 * Math.sin((180.0+rotatio) * Math.PI / 180.0));
               // xact += xspeed*2;
              //  yact -= yspeed*2;
                y = (int)yact;
                x = (int)xact;
                rotatio = rotatio + (1) ;
                this.setRotation(rotatio);
                if (rotatio >= 90) {
                  rotatio = 90;
                    x = 740;
                    y = 360;
                  actualpos++;
                }
                break;
                case 2:
                    x+=speed;
                    if (x >= 3100)
                    {
                        x = 3100;
                        actualpos++;
                        xact = x;
                        yact = y;
                    }
                    break;

                default:

                    break;
        }
    }
}
