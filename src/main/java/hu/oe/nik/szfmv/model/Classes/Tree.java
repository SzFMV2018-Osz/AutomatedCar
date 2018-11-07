package hu.oe.nik.szfmv.model.Classes;

import hu.oe.nik.szfmv.model.Interfaces.ICollidable;

public class Tree extends RoadObsticle implements ICollidable {

    /**
     * @param x coordinate
     * @param y coordinate
     * @param imageFileName the name of the imagefile used for this type
     * @param m11 transformation
     * @param m12 transformation
     * @param m21 transformation
     * @param m22 transformation
     */

    //a fa törzsének mérete 25 pixelben lett megállapítva

    int actualHeight = 25;
    int startingDamage = 0;
    int damageLimit = 100;
    int weightTree = 99999;
    boolean notDead = false;
    boolean notFatal = false;
    private PhysicsModel physicsModel;

    /**
     * @param x             coordinate
     * @param y             coordinate
     * @param imageFileName the name of the imagefile used for this type
     */
    public Tree(int x, int y, String imageFileName) {
        super(x, y, imageFileName);
    }

    public Tree(int x, int y, String imageFileName, double m11, double m12, double m21, double m22) {
        super(x, y, imageFileName, m11, m12, m21, m22);

        physicsModel = new PhysicsModel();
        physicsModel.setDamage(startingDamage);
        physicsModel.setDamageLimit(damageLimit);
        physicsModel.setIsDead(notDead);
        physicsModel.setFatal(notFatal);
        physicsModel.setWeight(weightTree);
        physicsModel.setWidth(actualHeight);
        physicsModel.setHeight(actualHeight);
    }

    public PhysicsModel getPhysicsModel() {
        return physicsModel;
    }


}
