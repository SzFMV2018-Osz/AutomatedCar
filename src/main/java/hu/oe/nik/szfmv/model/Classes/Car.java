package hu.oe.nik.szfmv.model.Classes;


import hu.oe.nik.szfmv.model.Interfaces.ICollidable;

public abstract class Car extends Dynamic implements ICollidable {

    /**
     * Creates an object of the virtual world on the given coordinates with the given image.
     *
     * @param x             the initial x coordinate of the object
     * @param y             the initial y coordinate of the object
     * @param imageFileName the filename of the image representing the object in the virtual world
     */
    public Car(int x, int y, String imageFileName) {
        super(x, y, imageFileName);

    }

    /**
     * @param x             coordinate
     * @param y             coordinate
     * @param imageFileName the name of the imagefile used for this type
     * @param m11           transformation
     * @param m12           transformation
     * @param m21           transformation
     * @param m22           transformation
     */
    public Car(int x, int y, String imageFileName, double m11, double m12, double m21, double m22) {
        super(x, y, imageFileName, m11, m12, m21, m22);

        PhysicsModel physicsModel = new PhysicsModel();

        switch(imageFileName) {
            case "car_1_blue" :
                physicsModel.setDamage(0);
                physicsModel.setDamagelimit(100);
                physicsModel.setIsdead(false);
                physicsModel.setFatal(false);
                physicsModel.setWeight(1400);
                physicsModel.setWidth(95);
                physicsModel.setWeight(238);

                break; // optional
            case "car_1_red" :
                physicsModel.setDamage(0);
                physicsModel.setDamagelimit(100);
                physicsModel.setIsdead(false);
                physicsModel.setFatal(false); 
                physicsModel. setWeight(1400);
                physicsModel.setWidth(95);
                physicsModel.setWeight(238);
                break; // optional
            case "car_1_white" :
                physicsModel.setDamage(0);
                physicsModel.setDamagelimit(100);
                physicsModel.setIsdead(false);
                physicsModel.setFatal(false); 
                physicsModel. setWeight(1400);
                physicsModel.setWidth(95);
                physicsModel.setWeight(238);
                break; // optional
            case "car_2_blue" :
                physicsModel.setDamage(0);
                physicsModel.setDamagelimit(100);
                physicsModel.setIsdead(false);
                physicsModel.setFatal(false); 
                physicsModel.setWeight(1100);
                physicsModel.setWidth(89);
                physicsModel.setWeight(207);
                break; // optional
            case "car_2_red" :
                physicsModel.setDamage(0);
                physicsModel.setDamagelimit(100);
                physicsModel.setIsdead(false);
                physicsModel.setFatal(false); 
                physicsModel.setWeight(1100);
                physicsModel.setWidth(89);
                physicsModel.setWeight(207);
                break; // optional
            case "car_2_white" :
                physicsModel.setDamage(0);
                physicsModel.setDamagelimit(100);
                physicsModel.setIsdead(false);
                physicsModel.setFatal(false); 
                physicsModel.setWeight(1100);
                physicsModel.setWidth(89);
                physicsModel.setWeight(207);
                break; // optional
            case "car_3_black" :
                physicsModel.setDamage(0);
                physicsModel.setDamagelimit(100);
                physicsModel.setIsdead(false);
                physicsModel.weight=2500;
                physicsModel.setWidth(98);
                physicsModel.setWeight(287);
                break; // optional
            case "car_1_blue_damaged" :
                physicsModel.setDamage(50);
                physicsModel.setDamagelimit(100);
                physicsModel.setIsdead(false);
                physicsModel.setFatal(false); 
                physicsModel.setWeight(1400);
                physicsModel.setWidth(95);
                physicsModel.setWeight(238);
                break; // optional
            case "car_1_red_damaged" :
                physicsModel.setDamage(50);
                physicsModel.setDamagelimit(100);
                physicsModel.setIsdead(false);
                physicsModel.setFatal(false); 
                physicsModel. setWeight(1400);
                physicsModel.setWidth(95);
                physicsModel.setWeight(238);
                break; // optional
            case "car_1_white_damaged" :
                physicsModel.setDamage(50);
                physicsModel.setDamagelimit(100);
                physicsModel.setIsdead(false);
                physicsModel.setFatal(false); 
                physicsModel.setWeight(1400);
                physicsModel.setWidth(95);
                physicsModel.setWeight(238);
                break; // optional
            case "car_2_blue_damaged" :
                physicsModel.setDamage(50);
                physicsModel.setDamagelimit(100);
                physicsModel.setIsdead(false);
                physicsModel.setFatal(false); 
                physicsModel.setWeight(1100);
                physicsModel.setWidth(89);
                physicsModel.setWeight(207);
                break; // optional
            case "car_2_red_damaged" :
                physicsModel.setDamage(50);
                physicsModel.setDamagelimit(100);
                physicsModel.setIsdead(false);
                physicsModel.setFatal(false); 
                physicsModel.setWeight(1100);
                physicsModel.setWidth(89);
                physicsModel.setWeight(207);
                break; // optional
            case "car_2_white_damaged" :
                physicsModel.setDamage(50);
                physicsModel.setDamagelimit(100);
                physicsModel.setIsdead(false);
                physicsModel.setFatal(false); 
                physicsModel.setWeight(1100);
                physicsModel.setWidth(89);
                physicsModel.setWeight(207);
                break; // optional
            case "car_3_black_damaged" :
                physicsModel.setDamage(50);
                physicsModel.setDamagelimit(100);
                physicsModel.setIsdead(false);
                physicsModel.weight=2500;
                physicsModel.setWidth(98);
                physicsModel.setWeight(287);
                break; // optional
            case "car_1_blue_damaged_full" :
                physicsModel.setDamage(100);
                physicsModel.setDamagelimit(100);
                physicsModel.setIsdead(true);
                physicsModel.setFatal(false); 
                physicsModel.setWeight(1400);
                physicsModel.setWidth(95);
                physicsModel.setWeight(238);
                break; // optional
            case "car_1_red_damaged_full" :
                physicsModel.setDamage(100);
                physicsModel.setDamagelimit(100);
                physicsModel.setIsdead(true);
                physicsModel.setFatal(false); 
                physicsModel.setWeight(1400);
                physicsModel.setWidth(95);
                physicsModel.setWeight(238);
                break; // optional
            case "car_1_white_damaged_full" :
                physicsModel.setDamage(100);
                physicsModel.setDamagelimit(100);
                physicsModel.setIsdead(true);
                physicsModel.setFatal(false); 
                physicsModel.setWeight(1400);
                physicsModel.setWidth(95);
                physicsModel.setWeight(238);
                break; // optional
            case "car_2_blue_damaged_full" :
                physicsModel.setDamage(100);
                physicsModel.setDamagelimit(100);
                physicsModel.setIsdead(true);
                physicsModel.setFatal(false); 
                physicsModel.setWeight(1100);
                physicsModel.setWidth(89);
                physicsModel.setWeight(207);
                break; // optional
            case "car_2_red_damaged_full" :
                physicsModel.setDamage(100);
                physicsModel.setDamagelimit(100);
                physicsModel.setIsdead(true);
                physicsModel.setFatal(false); 
                physicsModel.setWeight(1100);
                physicsModel.setWidth(89);
                physicsModel.setWeight(207);
                break; // optional
            case "car_2_white_damaged_full" :
                physicsModel.setDamage(100);
                physicsModel.setDamagelimit(100);
                physicsModel.setIsdead(true);
                physicsModel.setFatal(false); 
                physicsModel.setWeight(1100);
                physicsModel.setWidth(89);
                physicsModel.setWeight(207);
                break; // optional
            case "car_3_black_damaged_full" :
                physicsModel.setDamage(100);
                physicsModel.setDamagelimit(100);
                physicsModel.setIsdead(true);
                physicsModel.weight=2500;
                physicsModel.setWidth(98);
                physicsModel.setWeight(287);
                break; // optional
            case "car_2_white_blooded" :
                physicsModel.setDamage(0);
                physicsModel.setDamagelimit(100);
                physicsModel.setIsdead(true);
                physicsModel.setFatal(true);
                physicsModel.setWeight(1100);
                physicsModel.setWidth(89);
                physicsModel.setWeight(207);
                break; // optional
            case "car_2_white_damaged_blooded" :
                physicsModel.setDamage(50);
                physicsModel.setDamagelimit(100);
                physicsModel.setIsdead(true);
                physicsModel.setFatal(true);
                physicsModel.setWeight(1100);
                physicsModel.setWidth(89);
                physicsModel.setWeight(207);
                break; // optional
            // You can have any number of case statements.
            default : // Optional
                // Statements
        }

    }

}

