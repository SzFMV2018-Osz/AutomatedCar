package hu.oe.nik.szfmv.model.Classes;

import hu.oe.nik.szfmv.model.Interfaces.ICollidable;

public class NonPlayableCar extends Car implements ICollidable {
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

    public NonPlayableCar(int x, int y, String imageFileName , double m11 , double m12 , double m21 , double m22) {
        super(x, y, imageFileName , m11 ,m12 ,m21 ,m22);
    }
}
