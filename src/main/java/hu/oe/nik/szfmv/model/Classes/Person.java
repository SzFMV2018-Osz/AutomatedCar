package hu.oe.nik.szfmv.model.Classes;

import hu.oe.nik.szfmv.model.Interfaces.ICollidable;

public class Person extends Dynamic implements ICollidable {

    /**
     *
     * @param x coordinate
     * @param y coordinate
     * @param imageFileName the name of the imagefile used for this type
     */
    public Person(int x, int y, String imageFileName) {
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

    int actualheight_man = 67;
    int actualwidth_man = 38;
    int weight_man=80;

    int actualheight_woman = 72;
    int actualwidth_woman = 38;
    int weight_woman=50;

    int startingdamage=0;
    int damagelimit=100;

    boolean notdead = false;
    boolean notfatal= false;

    public Person(int x, int y, String imageFileName, double m11, double m12, double m21, double m22) {
        super(x, y, imageFileName, m11, m12, m21, m22);

        PhysicsModel physicsModel = new PhysicsModel();


        switch(imageFileName) {
            case "man":
                physicsModel.setDamage(startingdamage);
                physicsModel.setDamagelimit(damagelimit);
                physicsModel.setIsdead(notdead);
                physicsModel.setFatal(notfatal);
                physicsModel.setWeight(weight_man);
                physicsModel.setWidth(actualwidth_man);
                physicsModel.setHeight(actualheight_man);
                break; // optional
            case "woman":
                physicsModel.setDamage(startingdamage);
                physicsModel.setDamagelimit(damagelimit);
                physicsModel.setIsdead(notdead);
                physicsModel.setFatal(notfatal);
                physicsModel.setWeight(weight_woman);
                physicsModel.setWidth(actualwidth_woman);
                physicsModel.setHeight(actualheight_woman);
                break; // optional
            default: // Optional
                // Statements
        }



    }
}
