package hu.oe.nik.szfmv.model.Classes;

import hu.oe.nik.szfmv.model.Interfaces.ICollidable;

public class Person extends Dynamic implements ICollidable {

    /**
     * @param x coordinate
     * @param y coordinate
     * @param imageFileName the name of the imagefile used for this type
     * @param m11 transformation
     * @param m12 transformation
     * @param m21 transformation
     * @param m22 transformation
     */

    int actualHeightMan = 67;
    int actualWidthhMan = 38;
    int weightMan = 80;
    int actualHeightWoman = 72;
    int actualWidthhWoman = 38;
    int weightWoman = 50;
    int startingDamage = 0;
    int damageLimit = 100;
    boolean notDead = false;
    boolean notFatal = false;

    /**
     * @param x             coordinate
     * @param y             coordinate
     * @param imageFileName the name of the imagefile used for this type
     */
    public Person(int x, int y, String imageFileName) {
        super(x, y, imageFileName);
    }

    public Person(int x, int y, String imageFileName, double m11, double m12, double m21, double m22) {
        super(x, y, imageFileName, m11, m12, m21, m22);

        PhysicsModel physicsModel = new PhysicsModel();


        switch (imageFileName) {
            case "man":
                physicsModel.setDamage(startingDamage);
                physicsModel.setDamageLimit(damageLimit);
                physicsModel.setIsDead(notDead);
                physicsModel.setFatal(notFatal);
                physicsModel.setWeight(weightMan);
                physicsModel.setWidth(actualWidthhMan);
                physicsModel.setHeight(actualHeightMan);
                break; // optional
            case "woman":
                physicsModel.setDamage(startingDamage);
                physicsModel.setDamageLimit(damageLimit);
                physicsModel.setIsDead(notDead);
                physicsModel.setFatal(notFatal);
                physicsModel.setWeight(weightWoman);
                physicsModel.setWidth(actualWidthhWoman);
                physicsModel.setHeight(actualHeightWoman);
                break; // optional
            default: // Optional
                // Statements
        }


    }
}
