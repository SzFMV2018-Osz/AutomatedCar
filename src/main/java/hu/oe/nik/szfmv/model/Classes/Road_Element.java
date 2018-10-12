package hu.oe.nik.szfmv.model.Classes;

import hu.oe.nik.szfmv.model.Interfaces.Not_Collidable;

public class Road_Element extends  Static implements Not_Collidable {
    public Road_Element(int x, int y, String imageFileName) {
        super(x, y, imageFileName);
    }

    public Road_Element(int x, int y, String imageFileName, double m11, double m12, double m21, double m22) {
        super(x, y, imageFileName, m11, m12, m21, m22);
    }
}
