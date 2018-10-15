package hu.oe.nik.szfmv.model.Classes;

import hu.oe.nik.szfmv.environment.WorldObject;
import hu.oe.nik.szfmv.model.Interfaces.ICollidable;

public  abstract class Dinamic extends WorldObject implements ICollidable {
    /**
     * Creates an object of the virtual world on the given coordinates with the given image.
     * @param x             the initial x coordinate of the object
     * @param y             the initial y coordinate of the object
     * @param imageFileName the filename of the image representing the object in the virtual world
     */
    public Dinamic(int x, int y, String imageFileName) {
        super(x, y, imageFileName);
    }

    public Dinamic(int x, int y, String imageFileName , double m11  ,double m12 , double m21 , double m22) {
        super(x, y, imageFileName , m11 ,m12 ,m21 ,m22);
    }
}
