package hu.oe.nik.szfmv.model.Classes;

import hu.oe.nik.szfmv.environment.WorldObject;

public class Statikus extends WorldObject {

    boolean neki_lehet_menni;

    public boolean isNeki_lehet_menni() {
        return neki_lehet_menni;
    }

    public void setNeki_lehet_menni(boolean neki_lehet_menni) {
        this.neki_lehet_menni = neki_lehet_menni;
    }



    /**
     * Creates an object of the virtual world on the given coordinates with the given image.
     *
     * @param x             the initial x coordinate of the object
     * @param y             the initial y coordinate of the object
     * @param imageFileName the filename of the image representing the object in the virtual world
     */
    public Statikus(int x, int y, String imageFileName, String m11s, String m12s, String m21s, String m22s)
    {
        super(x, y, imageFileName);
        super.Tranz_Matrix_Letrehoz(m11s,m12s,m21s,m22s);
    }




}
