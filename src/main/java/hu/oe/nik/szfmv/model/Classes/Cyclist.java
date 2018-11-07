package hu.oe.nik.szfmv.model.Classes;

import hu.oe.nik.szfmv.model.Interfaces.ICollidable;

public class Cyclist extends Dynamic implements ICollidable {

    /**
     * @param x coordinate
     * @param y coordinate
     * @param imageFileName the name of the imagefile used for this type
     * @param m11 transformation
     * @param m12 transformation
     * @param m21 transformation
     * @param m22 transformation
     */

    /* a biciklis magassága/szélessége pixel szerint*/
    int actualheight = 126;
    int actualwidth = 51;
    int weight_cyclist = 100;
    int startingdamage = 0;
    int damagelimit = 100;
    boolean notdead = false;
    boolean notfatal = false;
    /**
     * @param x             coordinate
     * @param y             coordinate
     * @param imageFileName the name of the imagefile used for this type
     */
    public Cyclist(int x, int y, String imageFileName) {
        super(x, y, imageFileName);
    }

    public Cyclist(int x, int y, String imageFileName, double m11, double m12, double m21, double m22) {
        super(x, y, imageFileName, m11, m12, m21, m22);

        PhysicsModel physicsModel = new PhysicsModel();
        physicsModel.setDamage(startingdamage);
        physicsModel.setDamagelimit(damagelimit);
        physicsModel.setIsdead(notdead);
        physicsModel.setFatal(notfatal);
        physicsModel.setWeight(weight_cyclist);
        physicsModel.setWidth(actualwidth);
        physicsModel.setHeight(actualheight);
    }
}
