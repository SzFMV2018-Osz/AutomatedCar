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
    //a különböző autók pixelpontos pontos mérete
    int actualheight_car_1 =238;
    int actualwidth_car_1 = 95;
    int weight_car_1=1400;
    
    int actualheight_car_2 =207;
    int actualwidth_car_2 = 89;
    int weight_car_2=1100;

    int actualheight_car_3 =287;
    int actualwidth_car_3 = 98;
    int weight_car_3=2500;

    //károsodás mértéke: alapból 0, első ütközésnél 50, másodiknál 100, ami a végét is jelenti a jármű működésének mert a limit is 100
    int startingdamage=0;
    int damagedstate=50;
    int damagelimit=100;
    int fulldamagedstate=100;
    
    //jármú állapota
    boolean notdead = false;
    boolean dead=true;

    //járókelő/biciklis ütközés
    boolean notfatal= false;
    boolean fatal=true;
    
    
    public Car(int x, int y, String imageFileName, double m11, double m12, double m21, double m22) {
        super(x, y, imageFileName, m11, m12, m21, m22);

        PhysicsModel physicsModel = new PhysicsModel();

        switch(imageFileName) {
            case "car_1_blue" :
                physicsModel.setDamage(startingdamage);
                physicsModel.setDamagelimit(damagelimit);
                physicsModel.setIsdead(notdead);
                physicsModel.setFatal(notfatal);
                physicsModel.setWeight(weight_car_1);
                physicsModel.setWidth(actualwidth_car_1);
                physicsModel.setHeight(actualheight_car_1);

                break; // optional
            case "car_1_red" :
                physicsModel.setDamage(startingdamage);
                physicsModel.setDamagelimit(damagelimit);
                physicsModel.setIsdead(notdead);
                physicsModel.setFatal(notfatal); 
                physicsModel.setWeight(weight_car_1);
                physicsModel.setWidth(actualwidth_car_1);
                physicsModel.setHeight(actualheight_car_1);
                break; // optional
            case "car_1_white" :
                physicsModel.setDamage(startingdamage);
                physicsModel.setDamagelimit(damagelimit);
                physicsModel.setIsdead(notdead);
                physicsModel.setFatal(notfatal); 
                physicsModel.setWeight(weight_car_1);
                physicsModel.setWidth(actualwidth_car_1);
                physicsModel.setHeight(actualheight_car_1);
                break; // optional
            case "car_2_blue" :
                physicsModel.setDamage(startingdamage);
                physicsModel.setDamagelimit(damagelimit);
                physicsModel.setIsdead(notdead);
                physicsModel.setFatal(notfatal); 
                physicsModel.setWeight(weight_car_2);
                physicsModel.setWidth(actualwidth_car_2);
                physicsModel.setHeight(actualheight_car_2);
                break; // optional
            case "car_2_red" :
                physicsModel.setDamage(startingdamage);
                physicsModel.setDamagelimit(damagelimit);
                physicsModel.setIsdead(notdead);
                physicsModel.setFatal(notfatal); 
                physicsModel.setWeight(weight_car_2);
                physicsModel.setWidth(actualwidth_car_2);
                physicsModel.setHeight(actualheight_car_2);
                break; // optional
            case "car_2_white" :
                physicsModel.setDamage(startingdamage);
                physicsModel.setDamagelimit(damagelimit);
                physicsModel.setIsdead(notdead);
                physicsModel.setFatal(notfatal); 
                physicsModel.setWeight(weight_car_2);
                physicsModel.setWidth(actualwidth_car_2);
                physicsModel.setHeight(actualheight_car_2);
                break; // optional
            case "car_3_black" :
                physicsModel.setDamage(startingdamage);
                physicsModel.setDamagelimit(damagelimit);
                physicsModel.setIsdead(notdead);
                physicsModel.setWeight(weight_car_3);
                physicsModel.setWidth(actualwidth_car_3);
                physicsModel.setHeight(actualheight_car_3);
                break; // optional
            case "car_1_blue_damaged" :
                physicsModel.setDamage(damagedstate);
                physicsModel.setDamagelimit(damagelimit);
                physicsModel.setIsdead(notdead);
                physicsModel.setFatal(notfatal); 
                physicsModel.setWeight(weight_car_1);
                physicsModel.setWidth(actualwidth_car_1);
                physicsModel.setHeight(actualheight_car_1);
                break; // optional
            case "car_1_red_damaged" :
                physicsModel.setDamage(damagedstate);
                physicsModel.setDamagelimit(damagelimit);
                physicsModel.setIsdead(notdead);
                physicsModel.setFatal(notfatal); 
                physicsModel. setWeight(weight_car_1);
                physicsModel.setWidth(actualwidth_car_1);
                physicsModel.setHeight(actualheight_car_1);
                break; // optional
            case "car_1_white_damaged" :
                physicsModel.setDamage(damagedstate);
                physicsModel.setDamagelimit(damagelimit);
                physicsModel.setIsdead(notdead);
                physicsModel.setFatal(notfatal); 
                physicsModel.setWeight(weight_car_1);
                physicsModel.setWidth(actualwidth_car_1);
                physicsModel.setHeight(actualheight_car_1);
                break; // optional
            case "car_2_blue_damaged" :
                physicsModel.setDamage(damagedstate);
                physicsModel.setDamagelimit(damagelimit);
                physicsModel.setIsdead(notdead);
                physicsModel.setFatal(notfatal); 
                physicsModel.setWeight(weight_car_2);
                physicsModel.setWidth(actualwidth_car_2);
                physicsModel.setHeight(actualheight_car_2);
                break; // optional
            case "car_2_red_damaged" :
                physicsModel.setDamage(damagedstate);
                physicsModel.setDamagelimit(damagelimit);
                physicsModel.setIsdead(notdead);
                physicsModel.setFatal(notfatal); 
                physicsModel.setWeight(weight_car_2);
                physicsModel.setWidth(actualwidth_car_2);
                physicsModel.setHeight(actualheight_car_2);
                break; // optional
            case "car_2_white_damaged" :
                physicsModel.setDamage(damagedstate);
                physicsModel.setDamagelimit(damagelimit);
                physicsModel.setIsdead(notdead);
                physicsModel.setFatal(notfatal); 
                physicsModel.setWeight(weight_car_2);
                physicsModel.setWidth(actualwidth_car_2);
                physicsModel.setHeight(actualheight_car_2);
                break; // optional
            case "car_3_black_damaged" :
                physicsModel.setDamage(damagedstate);
                physicsModel.setDamagelimit(damagelimit);
                physicsModel.setIsdead(notdead);
                physicsModel.setWeight(weight_car_3);
                physicsModel.setWidth(actualwidth_car_3);
                physicsModel.setHeight(actualheight_car_3);
                break; // optional
            case "car_1_blue_damaged_full" :
                physicsModel.setDamage(fulldamagedstate);
                physicsModel.setDamagelimit(damagelimit);
                physicsModel.setIsdead(dead);
                physicsModel.setFatal(notfatal); 
                physicsModel.setWeight(weight_car_1);
                physicsModel.setWidth(actualwidth_car_1);
                physicsModel.setHeight(actualheight_car_1);
                break; // optional
            case "car_1_red_damaged_full" :
                physicsModel.setDamage(fulldamagedstate);
                physicsModel.setDamagelimit(damagelimit);
                physicsModel.setIsdead(dead);
                physicsModel.setFatal(notfatal); 
                physicsModel.setWeight(weight_car_1);
                physicsModel.setWidth(actualwidth_car_1);
                physicsModel.setHeight(actualheight_car_1);
                break; // optional
            case "car_1_white_damaged_full" :
                physicsModel.setDamage(fulldamagedstate);
                physicsModel.setDamagelimit(damagelimit);
                physicsModel.setIsdead(dead);
                physicsModel.setFatal(notfatal); 
                physicsModel.setWeight(weight_car_1);
                physicsModel.setWidth(actualwidth_car_1);
                physicsModel.setHeight(actualheight_car_1);
                break; // optional
            case "car_2_blue_damaged_full" :
                physicsModel.setDamage(fulldamagedstate);
                physicsModel.setDamagelimit(damagelimit);
                physicsModel.setIsdead(dead);
                physicsModel.setFatal(notfatal); 
                physicsModel.setWeight(weight_car_2);
                physicsModel.setWidth(actualwidth_car_2);
                physicsModel.setHeight(actualheight_car_2);
                break; // optional
            case "car_2_red_damaged_full" :
                physicsModel.setDamage(fulldamagedstate);
                physicsModel.setDamagelimit(damagelimit);
                physicsModel.setIsdead(dead);
                physicsModel.setFatal(notfatal); 
                physicsModel.setWeight(weight_car_2);
                physicsModel.setWidth(actualwidth_car_2);
                physicsModel.setHeight(actualheight_car_2);
                break; // optional
            case "car_2_white_damaged_full" :
                physicsModel.setDamage(fulldamagedstate);
                physicsModel.setDamagelimit(damagelimit);
                physicsModel.setIsdead(dead);
                physicsModel.setFatal(notfatal); 
                physicsModel.setWeight(weight_car_2);
                physicsModel.setWidth(actualwidth_car_2);
                physicsModel.setHeight(actualheight_car_2);
                break; // optional
            case "car_3_black_damaged_full" :
                physicsModel.setDamage(fulldamagedstate);
                physicsModel.setDamagelimit(damagelimit);
                physicsModel.setIsdead(dead);
                physicsModel.weight=2500;
                physicsModel.setWidth(actualwidth_car_3);
                physicsModel.setHeight(actualheight_car_3);
                break; // optional
            case "car_2_white_blooded" :
                physicsModel.setDamage(startingdamage);
                physicsModel.setDamagelimit(damagelimit);
                physicsModel.setIsdead(dead);
                physicsModel.setFatal(fatal);
                physicsModel.setWeight(weight_car_2);
                physicsModel.setWidth(actualwidth_car_2);
                physicsModel.setHeight(actualheight_car_2);
                break; // optional
            case "car_2_white_damaged_blooded" :
                physicsModel.setDamage(damagedstate);
                physicsModel.setDamagelimit(damagelimit);
                physicsModel.setIsdead(dead);
                physicsModel.setFatal(fatal);
                physicsModel.setWeight(weight_car_2);
                physicsModel.setWidth(actualwidth_car_2);
                physicsModel.setHeight(actualheight_car_2);
                break; // optional
            // You can have any number of case statements.
            default : // Optional
                // Statements
        }

    }

}

