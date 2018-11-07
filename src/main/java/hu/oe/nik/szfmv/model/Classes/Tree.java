package hu.oe.nik.szfmv.model.Classes;

import hu.oe.nik.szfmv.model.Interfaces.ICollidable;

public class Tree extends RoadObsticle implements ICollidable {

    /**
     *
     * @param x coordinate
     * @param y coordinate
     * @param imageFileName the name of the imagefile used for this type
     */
    public Tree(int x, int y, String imageFileName) {
        super(x, y, imageFileName);
    }

    /**
     *
     * @param x coordinate
     * @param y coordinate
     * @param imageFileName the name of the imagefile used for this type
     * @param m11 transformation
     * @param m12 transformation
     * @param m21 transformation
     * @param m22 transformation
     */

    //a fa törzsének mérete 25 pixelben lett megállapítva

    int actualheight = 25;
    int startingdamage = 0;
    int damagelimit = 100;
    int weight_tree = 99999;
    boolean notdead = false;
    boolean notfatal = false;



    public Tree(int x, int y, String imageFileName, double m11, double m12, double m21, double m22) {
        super(x, y, imageFileName, m11, m12, m21, m22);

        PhysicsModel physicsModel = new PhysicsModel();
        physicsModel.setDamage(startingdamage);
        physicsModel.setDamagelimit(damagelimit);
        physicsModel.setIsdead(notdead);
        physicsModel.setFatal(notfatal);
        physicsModel.setWeight(weight_tree);
        physicsModel.setWidth(actualheight);
        physicsModel.setHeight(actualheight);
    }
}
