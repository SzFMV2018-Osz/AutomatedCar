package hu.oe.nik.szfmv.model.Classes;


import hu.oe.nik.szfmv.model.Interfaces.ICollidable;

public abstract class Car extends Dynamic implements ICollidable {

    /**
     * Creates an object of the virtual world on the given coordinates with the given image.
     *
     * @param x             the initial x coordinate of the object
     * @param y             the initial y coordinate of the object
     * @param imageFileName the filename of the image representing the object in the virtual world
     */
    public Car(int x, int y, String imageFileName) {
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
    public Car(int x, int y, String imageFileName, double m11, double m12, double m21, double m22) {
        super(x, y, imageFileName, m11, m12, m21, m22);

    }

}

