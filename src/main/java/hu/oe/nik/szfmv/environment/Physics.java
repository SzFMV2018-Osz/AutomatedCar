package hu.oe.nik.szfmv.environment;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.model.Classes.Person;
import hu.oe.nik.szfmv.model.Classes.Static;
import hu.oe.nik.szfmv.model.Interfaces.ICollidable;
import hu.oe.nik.szfmv.visualization.Camera;

import java.awt.*;


public class Physics {




    public void update(World world, Camera camera) {
        AutomatedCar car = (AutomatedCar) world.getWorldObjects().stream().filter(x -> x instanceof AutomatedCar).findAny().orElse(null);
        boolean carcollide = false;
        for (WorldObject object : world.getWorldObjects()) {
            object.rotateImage(camera);
        }

        for (WorldObject object : world.getWorldObjects()) {
            if (object instanceof ICollidable) {
                boolean collide = getCollide(car, object) && !car.equals(object);


                if (!car.equals(object)) {
                    object.setCollide(collide);
                    if(collide && object instanceof Static){
                        staticColide(car,camera);
                    }
                    if (object instanceof Person && collide) {
                        //Main.Gameloop = false;
                        car.imageFileName = "car_2_white_blooded.png";
                        car.initImage();
                    }

                }
                if (collide) {
                    carcollide = true;
                }

                    updateLastPosition(object);



            }
        }

        car.setCollide(carcollide);

    }

    private void updateLastPosition(WorldObject object) {
      object.setLastX(object.getX());
      object.setLastY(object.getY());
    }

    boolean getCollide(WorldObject a, WorldObject b) {

        Rectangle aRectangle = new Rectangle(a.getWidth()/2 -a.getPhysicsModel().getWidth()/2, a.getHeight()/2 - a.getPhysicsModel().getHeight()/ 2, a.getPhysicsModel().getWidth(), a.getPhysicsModel().getHeight());
        Shape sa = a.getTransformation().createTransformedShape(aRectangle);
        Rectangle bRectangle = new Rectangle(b.getWidth()/2-b.getPhysicsModel().getWidth()/2, b.getHeight()/2 - b.getPhysicsModel().getHeight()/ 2, b.getPhysicsModel().getWidth(), b.getPhysicsModel().getHeight());
        Shape sb = b.getTransformation().createTransformedShape(bRectangle);
        return sa.intersects(sb.getBounds());
    }

    private void staticColide(WorldObject a, Camera camera){
        if(!a.isCollide())
        ((AutomatedCar)a).stopImmediately();
        a.setX(a.getLastX());
        a.setY(a.getLastY());
        a.rotateImage(camera);
        a.physicsModel.setDamage(50);
        a.setImageFileName("car_2_white_damaged.png");
        a.initImage();


    }

    private void dynamiccColide(WorldObject a, WorldObject b){

    }


}
