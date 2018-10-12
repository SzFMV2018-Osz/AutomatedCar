package hu.oe.nik.szfmv.model.Classes;

import hu.oe.nik.szfmv.model.Interfaces.Collidable;

public class Road_Obsticle extends  Static implements Collidable {
    public Road_Obsticle(int x, int y, String imageFileName) {
        super(x, y, imageFileName);
    }

    public Road_Obsticle(int x, int y, String imageFileName, double m11, double m12, double m21, double m22) {
        super(x, y, imageFileName, m11, m12, m21, m22);
    }
}
