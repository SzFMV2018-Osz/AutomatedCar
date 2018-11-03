package hu.oe.nik.szfmv.model.Classes;

public class NPC extends Dinamikus {

    /**
     * Creates an object of the virtual world on the given coordinates with the given image.
     *
     * @param x             the initial x coordinate of the object
     * @param y             the initial y coordinate of the object
     * @param imageFileName the filename of the image representing the object in the virtual world
     */
    public NPC(int x, int y, String imageFileName, String m11s, String m12s, String m21s, String m22s)
    {
        super(x, y, imageFileName,m11s,m12s,m21s,m22s);
    }
}
