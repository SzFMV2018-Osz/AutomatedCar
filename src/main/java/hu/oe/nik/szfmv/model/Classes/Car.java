package hu.oe.nik.szfmv.model.Classes;


import hu.oe.nik.szfmv.Main;
import hu.oe.nik.szfmv.model.Interfaces.ICollidable;

public class Car extends Dynamic implements ICollidable {

    /**
     * @param x             coordinate
     * @param y             coordinate
     * @param imageFileName the name of the imagefile used for this type
     * @param m11           transformation
     * @param m12           transformation
     * @param m21           transformation
     * @param m22           transformation
     */

    //a különböző autók pixelpontos pontos mérete
    int actualHeightCar1 = 238;
    int actualWidthCar1 = 95;
    int weightCar1 = 1400;
    int actualHeightCar2 = 207;
    int actualWidthCar2 = 89;
    int weightCar2 = 1100;
    int actualHeightCar3 = 287;
    int actualWidthCar3 = 98;
    int weightCar3 = 2500;
    //károsodás mértéke: alapból 0, első ütközésnél 50, másodiknál 100, ami
    // a végét is jelenti a jármű működésének mert a limit is 100
    int startingDamage = 0;
    int damagedState = 50;
    int damageLimit = 100;
    int fullDamagedState = 100;
    //jármú állapota
    boolean notDead = false;
    boolean dead = true;
    //járókelő/biciklis ütközés
    boolean notFatal = false;
    boolean fatal = true;


    /**
     * Creates an object of the virtual world on the given coordinates with the given image.
     *
     * @param x             the initial x coordinate of the object
     * @param y             the initial y coordinate of the object
     * @param imageFileName the filename of the image representing the object in the virtual world
     */
    public Car(int x, int y, String imageFileName) {
        super(x, y, imageFileName);
        initPhysicModel(imageFileName);
    }

    public Car(int x, int y, String imageFileName, double m11, double m12, double m21, double m22) {
        super(x, y, imageFileName, m11, m12, m21, m22);
        initPhysicModel(imageFileName);

    }

    private void initPhysicModel(String imageFileName) {

        physicsModel = new PhysicsModel();


        switch (imageFileName) {
            case "car_1_blue.png":
                physicsModel.setDamage(startingDamage);
                physicsModel.setDamageLimit(damageLimit);
                physicsModel.setIsDead(notDead);
                physicsModel.setFatal(notFatal);
                physicsModel.setWeight(weightCar1);
                physicsModel.setWidth(actualWidthCar1);
                physicsModel.setHeight(actualHeightCar1);

                break;
            case "car_1_red.png":
                physicsModel.setDamage(startingDamage);
                physicsModel.setDamageLimit(damageLimit);
                physicsModel.setIsDead(notDead);
                physicsModel.setFatal(notFatal);
                physicsModel.setWeight(weightCar1);
                physicsModel.setWidth(actualWidthCar1);
                physicsModel.setHeight(actualHeightCar1);
                break;
            case "car_1_white.png":
                physicsModel.setDamage(startingDamage);
                physicsModel.setDamageLimit(damageLimit);
                physicsModel.setIsDead(notDead);
                physicsModel.setFatal(notFatal);
                physicsModel.setWeight(weightCar1);
                physicsModel.setWidth(actualWidthCar1);
                physicsModel.setHeight(actualHeightCar1);
                break;
            case "car_2_blue.png":
                physicsModel.setDamage(startingDamage);
                physicsModel.setDamageLimit(damageLimit);
                physicsModel.setIsDead(notDead);
                physicsModel.setFatal(notFatal);
                physicsModel.setWeight(weightCar2);
                physicsModel.setWidth(actualWidthCar2);
                physicsModel.setHeight(actualHeightCar2);
                break;
            case "car_2_red.png":
                physicsModel.setDamage(startingDamage);
                physicsModel.setDamageLimit(damageLimit);
                physicsModel.setIsDead(notDead);
                physicsModel.setFatal(notFatal);
                physicsModel.setWeight(weightCar2);
                physicsModel.setWidth(actualWidthCar2);
                physicsModel.setHeight(actualHeightCar2);
                break;
            case "car_2_white.png":
                physicsModel.setDamage(startingDamage);
                physicsModel.setDamageLimit(damageLimit);
                physicsModel.setIsDead(notDead);
                physicsModel.setFatal(notFatal);
                physicsModel.setWeight(weightCar2);
                physicsModel.setWidth(actualWidthCar2);
                physicsModel.setHeight(actualHeightCar2);
                break;
            case "car_3_black.png":
                physicsModel.setDamage(startingDamage);
                physicsModel.setDamageLimit(damageLimit);
                physicsModel.setIsDead(notDead);
                physicsModel.setWeight(weightCar3);
                physicsModel.setWidth(actualWidthCar3);
                physicsModel.setHeight(actualHeightCar3);
                break;
            case "car_1_blue_damaged.png":
                physicsModel.setDamage(damagedState);
                physicsModel.setDamageLimit(damageLimit);
                physicsModel.setIsDead(notDead);
                physicsModel.setFatal(notFatal);
                physicsModel.setWeight(weightCar1);
                physicsModel.setWidth(actualWidthCar1);
                physicsModel.setHeight(actualHeightCar1);
                break;
            case "car_1_red_damaged.png":
                physicsModel.setDamage(damagedState);
                physicsModel.setDamageLimit(damageLimit);
                physicsModel.setIsDead(notDead);
                physicsModel.setFatal(notFatal);
                physicsModel.setWeight(weightCar1);
                physicsModel.setWidth(actualWidthCar1);
                physicsModel.setHeight(actualHeightCar1);
                break;
            case "car_1_white_damaged.png":
                physicsModel.setDamage(damagedState);
                physicsModel.setDamageLimit(damageLimit);
                physicsModel.setIsDead(notDead);
                physicsModel.setFatal(notFatal);
                physicsModel.setWeight(weightCar1);
                physicsModel.setWidth(actualWidthCar1);
                physicsModel.setHeight(actualHeightCar1);
                break;
            case "car_2_blue_damaged.png":
                physicsModel.setDamage(damagedState);
                physicsModel.setDamageLimit(damageLimit);
                physicsModel.setIsDead(notDead);
                physicsModel.setFatal(notFatal);
                physicsModel.setWeight(weightCar2);
                physicsModel.setWidth(actualWidthCar2);
                physicsModel.setHeight(actualHeightCar2);
                break;
            case "car_2_red_damaged.png":
                physicsModel.setDamage(damagedState);
                physicsModel.setDamageLimit(damageLimit);
                physicsModel.setIsDead(notDead);
                physicsModel.setFatal(notFatal);
                physicsModel.setWeight(weightCar2);
                physicsModel.setWidth(actualWidthCar2);
                physicsModel.setHeight(actualHeightCar2);
                break;
            case "car_2_white_damaged.png":
                physicsModel.setDamage(damagedState);
                physicsModel.setDamageLimit(damageLimit);
                physicsModel.setIsDead(notDead);
                physicsModel.setFatal(notFatal);
                physicsModel.setWeight(weightCar2);
                physicsModel.setWidth(actualWidthCar2);
                physicsModel.setHeight(actualHeightCar2);
                break;
            case "car_3_black_damaged.png":
                physicsModel.setDamage(damagedState);
                physicsModel.setDamageLimit(damageLimit);
                physicsModel.setIsDead(notDead);
                physicsModel.setWeight(weightCar3);
                physicsModel.setWidth(actualWidthCar3);
                physicsModel.setHeight(actualHeightCar3);
                break;
            case "car_1_blue_damaged_full.png":
                physicsModel.setDamage(fullDamagedState);
                physicsModel.setDamageLimit(damageLimit);
                physicsModel.setIsDead(dead);
                physicsModel.setFatal(notFatal);
                physicsModel.setWeight(weightCar1);
                physicsModel.setWidth(actualWidthCar1);
                physicsModel.setHeight(actualHeightCar1);
                break;
            case "car_1_red_damaged_full.png":
                physicsModel.setDamage(fullDamagedState);
                physicsModel.setDamageLimit(damageLimit);
                physicsModel.setIsDead(dead);
                physicsModel.setFatal(notFatal);
                physicsModel.setWeight(weightCar1);
                physicsModel.setWidth(actualWidthCar1);
                physicsModel.setHeight(actualHeightCar1);
                break;
            case "car_1_white_damaged_full.png":
                physicsModel.setDamage(fullDamagedState);
                physicsModel.setDamageLimit(damageLimit);
                physicsModel.setIsDead(dead);
                physicsModel.setFatal(notFatal);
                physicsModel.setWeight(weightCar1);
                physicsModel.setWidth(actualWidthCar1);
                physicsModel.setHeight(actualHeightCar1);
                break;
            case "car_2_blue_damaged_full.png":
                physicsModel.setDamage(fullDamagedState);
                physicsModel.setDamageLimit(damageLimit);
                physicsModel.setIsDead(dead);
                physicsModel.setFatal(notFatal);
                physicsModel.setWeight(weightCar2);
                physicsModel.setWidth(actualWidthCar2);
                physicsModel.setHeight(actualHeightCar2);
                break;
            case "car_2_red_damaged_full.png":
                physicsModel.setDamage(fullDamagedState);
                physicsModel.setDamageLimit(damageLimit);
                physicsModel.setIsDead(dead);
                physicsModel.setFatal(notFatal);
                physicsModel.setWeight(weightCar2);
                physicsModel.setWidth(actualWidthCar2);
                physicsModel.setHeight(actualHeightCar2);
                break;
            case "car_2_white_damaged_full.png":
                physicsModel.setDamage(fullDamagedState);
                physicsModel.setDamageLimit(damageLimit);
                physicsModel.setIsDead(dead);
                physicsModel.setFatal(notFatal);
                physicsModel.setWeight(weightCar2);
                physicsModel.setWidth(actualWidthCar2);
                physicsModel.setHeight(actualHeightCar2);
                break;
            case "car_3_black_damaged_full.png":
                physicsModel.setDamage(fullDamagedState);
                physicsModel.setDamageLimit(damageLimit);
                physicsModel.setIsDead(dead);
                physicsModel.setWeight(weightCar3);
                physicsModel.setWidth(actualWidthCar3);
                physicsModel.setHeight(actualHeightCar3);
                break;
            case "car_2_white_blooded.png":
                physicsModel.setDamage(startingDamage);
                physicsModel.setDamageLimit(damageLimit);
                physicsModel.setIsDead(dead);
                physicsModel.setFatal(fatal);
                physicsModel.setWeight(weightCar2);
                physicsModel.setWidth(actualWidthCar2);
                physicsModel.setHeight(actualHeightCar2);
                break;
            case "car_2_white_damaged_blooded.png":
                physicsModel.setDamage(damagedState);
                physicsModel.setDamageLimit(damageLimit);
                physicsModel.setIsDead(dead);
                physicsModel.setFatal(fatal);
                physicsModel.setWeight(weightCar2);
                physicsModel.setWidth(actualWidthCar2);
                physicsModel.setHeight(actualHeightCar2);
                break;

            default:

        }
    }

    public void setNewImage() {
        if (physicsModel.getDamage() >= this.damagedState) {
            if (!imageFileName.contains("_damaged")) {
                this.imageFileName = imageFileName.replace(".png", "_damaged.png");
            }
        }
        if (physicsModel.getDamage() >= this.damageLimit) {
            if (!imageFileName.contains("_full"))
                this.imageFileName = imageFileName.replace(".png", "_full.png");
            Main.Gameloop = false;
        }


        if (physicsModel.isFatal()) {
            if (!imageFileName.contains("_blooded.png") && !imageFileName.contains("_full.png"))
                this.imageFileName = imageFileName.replace(".png", "_blooded.png");

        }
        this.initImage();


    }
}
