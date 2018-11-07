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
    int actualHeight = 126;
    int actualWidth = 51;
    int weightCyclist = 100;
    int startingDamage = 0;
    int damageLimit = 100;
    boolean notDead = false;
    boolean notFatal = false;

    /**
     * @param x             coordinate
     * @param y             coordinate
     * @param imageFileName the name of the imagefile used for this type
     */
    public Cyclist(int x, int y, String imageFileName) {
        super(x, y, imageFileName);
        initPhysicModel();
    }

    public Cyclist(int x, int y, String imageFileName, double m11, double m12, double m21, double m22) {
        super(x, y, imageFileName, m11, m12, m21, m22);
        initPhysicModel();


    }

    private void initPhysicModel() {
        physicsModel = new PhysicsModel();
        physicsModel.setDamage(startingDamage);
        physicsModel.setDamageLimit(damageLimit);
        physicsModel.setIsDead(notDead);
        physicsModel.setFatal(notFatal);
        physicsModel.setWeight(weightCyclist);
        physicsModel.setWidth(actualWidth);
        physicsModel.setHeight(actualHeight);
    }
}
