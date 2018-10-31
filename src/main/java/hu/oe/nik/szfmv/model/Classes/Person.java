package hu.oe.nik.szfmv.model.Classes;

import hu.oe.nik.szfmv.model.Interfaces.ICollidable;

public class Person extends Dynamic implements ICollidable {

    int actualpos = 0;
    double speed = 4;
    int rotatio = 0;
    double xact = 0;
    double yact = 0;

    /**
     * @param x             coordinate
     * @param y             coordinate
     * @param imageFileName the name of the imagefile used for this type
     */
    public Person(int x, int y, String imageFileName) {
        super(x, y, imageFileName);
    }

    /**
     * @param x             coordinate
     * @param y             coordinate
     * @param imageFileName the name of the imagefile used for this type
     * @param m11           transformation
     * @param m12           transformation
     * @param m21           transformation
     * @param m22           transformation
     */
    public Person(int x, int y, String imageFileName, double m11, double m12, double m21, double m22) {
        super(x, y, imageFileName, m11, m12, m21, m22);
    }



    public void moveperson() {
        switch (actualpos) {
            case 0:
                rotatio = 0;
                this.setRotation(rotatio);
                y -= speed;
                if (y < 80) {

                    actualpos++;

                  //  x+=16;
                  //  y+=36+20;

                }
                break;
            case 1:
                rotatio = 90;
                this.setRotation(rotatio);
              x +=speed;
                if (x > 1650) {
                    actualpos++;
                }
                break;
            case 2:
                rotatio = 180;
                this.setRotation(rotatio);
                y += speed;
                if (y > 700) {
                    actualpos++;
                }
                break;
            case 3:
                rotatio = 270;
                this.setRotation(rotatio);
                x-=speed;
                if (x < 1500) {
                    actualpos=0;
                }
                break;
            default:
                break;
        }
    }
}

