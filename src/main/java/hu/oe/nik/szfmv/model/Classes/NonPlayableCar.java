package hu.oe.nik.szfmv.model.Classes;

import hu.oe.nik.szfmv.model.Interfaces.ICollidable;

//import java.io.Console;
//import java.math.*;


public class NonPlayableCar extends Car implements ICollidable {
    int actualpos = 0;
    double speed = 20;
    int rotatio = 0;
    double xact = 0;
    double yact = 0;



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


    public int getActualpos() {
        return actualpos;
    }

    public void setActualpos(int actualpos) {
        this.actualpos = actualpos;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }


    /**
     * Moves the car object automatically based on it's temporary values
     */
    public void movecar1() {
        switch (actualpos) {
            case 0:
                y -= speed ;
                if (y <= 669 - 120) {
                    y = 669 - 120;
                    actualpos++;
                }

                break;
            case 1:
                // 474,669
                xact = (double) ((474.0 + 175.0 + 120.0) + ((474.0 + 175.0 + 120.0) - (474.0 - 175 + 54))
                        * Math.cos((180.0 + rotatio) * Math.PI / 180.0));
                yact = (double) ((669.0 - 120.0) + ((669.0 - 120.0) - (669.0 - 175.0 - 175.0 + 54.0))
                        * Math.sin((180.0 + rotatio) * Math.PI / 180.0));
                y = (int)yact;
                x = (int)xact;
                rotatio = rotatio + (3) ;
                this.setRotation(rotatio);
                if (rotatio >= 90) {
                    rotatio = 90;
                    //  x = 769;
                    //   y = 363;
                    actualpos++;
                }
                break;
            case 2:
                x += speed;
                if (x >= 3220) {
                    x = 3220;
                    actualpos++;
                }
                break;
            case 3:
                // 3100,494
                xact = (double) ((3100.0 + 120.0) + (176.0) * Math.cos((180.0 + rotatio) * Math.PI / 180.0));
                yact = (double) ((494.0 + 295.0) + (416.0) * Math.sin((180.0 + rotatio) * Math.PI / 180.0)); //363+426
                y = (int)yact;
                x = (int)xact;
                rotatio = rotatio + (3) ;
                this.setRotation(rotatio);
                if (rotatio >= 135) {
                    rotatio = 135;
                    //  x = 3344;
                    //   y = 472;
                    actualpos++;
                }

                break;
            case 4:
                yact = Math.sqrt((speed * speed) / 2);
                xact = Math.sqrt((speed * speed) / 2);
                y += (int)yact;
                x += (int)xact;
                if (y >= 720) {
                    actualpos++;
                }
                break;
            case 5:
                // 3471,793
                xact = (double) ((3471.0) + (186.0) * Math.cos((180.0 + rotatio) * Math.PI / 180.0));
                yact = (double) ((793 + 244.0) + (426.0) * Math.sin((180.0 + rotatio) * Math.PI / 180.0));
                y = (int)yact;
                x = (int)xact;
                rotatio = rotatio + (3) ;
                this.setRotation(rotatio);
                if (rotatio >= 180) {
                    rotatio = 180;
                    actualpos++;
                }
                break;
            case 6:
                y += speed;
                if (y >= 2316 + 120) {
                    y = 2316 + 120;
                    actualpos++;
                }
                break;
            case 7:
                // 3522,2316
                xact = (double) ((3522.0 - 175.0 - 120.0) + (416.0) * Math.cos((180.0 + rotatio) * Math.PI / 180.0));
                yact = (double) ((2316.0 + 120.0) + (176.0) * Math.sin((180.0 + rotatio) * Math.PI / 180.0));
                y = (int)yact;
                x = (int)xact;
                rotatio = rotatio + (3) ;
                this.setRotation(rotatio);
                if (rotatio >= 270) {
                    rotatio = 270;
                    actualpos++;
                }
                break;
            case 8:
                x -= speed;
                if (x <= 897.0 - 120.0) {
                    x = 897 - 120;
                    actualpos++;
                }
                break;
            case 9:
                // 897,2491
                xact = (double) ((897.0 - 120.0) + (176.0) * Math.cos((180.0 + rotatio) * Math.PI / 180.0));
                yact = (double) ((2491.0 - 295.0) + (416.0) * Math.sin((180.0 + rotatio) * Math.PI / 180.0)); //363+426
                y = (int)yact;
                x = (int)xact;
                rotatio = rotatio + (3) ;
                this.setRotation(rotatio);
                if (rotatio >= 315) {
                    rotatio = 315;
                    actualpos++;
                }

                break;
            case 10:
                yact = Math.sqrt((speed * speed) / 2);
                xact = Math.sqrt((speed * speed) / 2);
                y -= (int)yact;
                x -= (int)xact;
                if (y <= 2303) {
                    actualpos++;
                }
                break;
            case 11:
                // 526,2193
                xact = (double) ((526.0) + (186.0) * Math.cos((180.0 + rotatio ) * Math.PI / 180.0));
                yact = (double) ((2193 - 244.0) + (426.0) * Math.sin((180.0 + rotatio) * Math.PI / 180.0));
                y = (int)yact;
                x = (int)xact;
                rotatio = rotatio + (3) ;
                this.setRotation(rotatio);
                if (rotatio >= 360) {
                    rotatio = 0;
                    actualpos = 0;
                }
                break;

                default:
                x = 343;
                y = 1500;
                actualpos = 0;
                break;
        }
    }

    /**
     * Moves the car object automatically based on it's temporary values
     */
    public void movecar2() {
        switch (actualpos) {
            case 0:
                y -= speed ;
                if (y <= 669 - 120) {
                    y = 669 - 120;
                    actualpos++;
                }

                break;
            case 1:
                // 474,669
                double  xact = (double) ((474.0 + 175.0 + 120.0) + ((474.0 + 175.0 + 120.0) - (474.0 - 175 + 54)) * Math.cos((180.0 + rotatio) * Math.PI / 180.0));
                double  yact = (double) ((669.0 - 120.0) + ((669.0 - 120.0) - (669.0 - 175.0 - 175.0 + 54.0)) * Math.sin((180.0 + rotatio) * Math.PI / 180.0));
                y = (int)yact - 175;
                x = (int)xact - 175;
                rotatio = rotatio + (3) ;
                this.setRotation(rotatio);
                if (rotatio >= 90) {
                    rotatio = 90;
                    //  x = 769;
                    //   y = 363;
                    actualpos++;
                }
                break;
            case 2:
                x += speed;
                if (x >= 3220) {
                    x = 3220;
                    actualpos++;
                }
                break;
            case 3:
                // 3100,494
                xact = (double) ((3100.0 + 120.0) + (176.0) * Math.cos((180.0 + rotatio) * Math.PI / 180.0));
                yact = (double) ((494.0 + 295.0) + (416.0) * Math.sin((180.0 + rotatio) * Math.PI / 180.0)); //363+426
                y = (int)yact;
                x = (int)xact;
                rotatio = rotatio + (3) ;
                this.setRotation(rotatio);
                if (rotatio >= 135) {
                    rotatio = 135;
                    //  x = 3344;
                    //   y = 472;
                    actualpos++;
                }

                break;
            case 4:
                y += speed;
                x += speed;
                if (y >= 720) {
                    actualpos++;
                }
                break;
            case 5:
                // 3471,793
                xact = (double) ((3471.0) + (186.0) * Math.cos((180.0 + rotatio) * Math.PI / 180.0));
                yact = (double) ((793 + 244.0) + (426.0) * Math.sin((180.0 + rotatio) * Math.PI / 180.0));
                y = (int)yact;
                x = (int)xact;
                rotatio = rotatio + (3) ;
                this.setRotation(rotatio);
                if (rotatio >= 180) {
                    rotatio = 180;
                    actualpos++;
                }
                break;
            case 6:
                y += speed;
                if (y >= 2316 + 120) {
                    y = 2316 + 120;
                    actualpos++;
                }
                break;
            case 7:
                // 3522,2316
                xact = (double) ((3522.0 - 175.0 - 120.0) + (416.0)
                        * Math.cos((180.0 + rotatio) * Math.PI / 180.0));
                yact = (double) ((2316.0 + 120.0) + (176.0)
                        * Math.sin((180.0 + rotatio) * Math.PI / 180.0));
                y = (int)yact;
                x = (int)xact;
                rotatio = rotatio + (3) ;
                this.setRotation(rotatio);
                if (rotatio >= 270) {
                    rotatio = 270;
                    actualpos++;
                }
                break;
            case 8:
                x -= speed;
                if (x <= 897.0 - 120.0) {
                    x = 897 - 120;
                    actualpos++;
                }
                break;
            case 9:
                // 897,2491
                xact = (double) ((897.0 - 120.0) + (176.0)
                        * Math.cos((180.0 + rotatio) * Math.PI / 180.0));
                yact = (double) ((2491.0 - 295.0) + (416.0)
                        * Math.sin((180.0 + rotatio) * Math.PI / 180.0)); //363+426
                y = (int)yact;
                x = (int)xact;
                rotatio = rotatio + (3) ;
                this.setRotation(rotatio);
                if (rotatio >= 315) {
                    rotatio = 315;
                    actualpos++;
                }

                break;
            case 10:
                y -= speed;
                x -= speed;
                if (y <= 2303) {
                    actualpos++;
                }
                break;
            case 11:
                // 526,2193
                xact = (double) ((526.0) + (186.0)
                        * Math.cos((180.0 + rotatio) * Math.PI / 180.0));
                yact = (double) ((2193 - 244.0) + (426.0)
                        * Math.sin((180.0 + rotatio) * Math.PI / 180.0));
                y = (int)yact;
                x = (int)xact;
                rotatio = rotatio + (3) ;
                this.setRotation(rotatio);
                if (rotatio >= 360) {
                    rotatio = 0;
                    actualpos = 0;
                }
                break;

            default:
                x = 343;
                y = 1500;
                actualpos = 0;
                break;
        }
    }
}
