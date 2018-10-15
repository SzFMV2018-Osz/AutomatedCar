package hu.oe.nik.szfmv.model.Classes;

public class Bush extends RoadObsticle {
    public Bush(int x, int y, String imageFileName) {
        super(x, y, imageFileName);
    }

    public Bush(int x, int y, String imageFileName, double m11, double m12, double m21, double m22) {
        super(x, y, imageFileName, m11, m12, m21, m22);
    }
}
