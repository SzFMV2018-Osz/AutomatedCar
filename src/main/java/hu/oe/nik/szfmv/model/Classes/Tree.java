package hu.oe.nik.szfmv.model.Classes;

import hu.oe.nik.szfmv.model.Interfaces.ICollidable;

public class Tree extends RoadObsticle implements ICollidable {
    public Tree(int x, int y, String imageFileName) {
        super(x, y, imageFileName);
    }

    public Tree(int x, int y, String imageFileName, double m11, double m12, double m21, double m22) {
        super(x, y, imageFileName, m11, m12, m21, m22);
    }
}
