package hu.oe.nik.szfmv.environment;

import hu.oe.nik.szfmv.Main;
import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.model.Classes.Person;
import hu.oe.nik.szfmv.model.Interfaces.ICollidable;

import java.awt.*;


public class Physics {


    public void update(World world) {
        AutomatedCar car = (AutomatedCar) world.getWorldObjects().stream().filter(x -> x instanceof AutomatedCar).findAny().orElse(null);
        boolean carcollide = false;
        for (WorldObject object : world.getWorldObjects()) {
            if (object instanceof ICollidable) {
                boolean collide = getCollide(car, object) && !car.equals(object);


                if (!car.equals(object)) {
                    object.setCollide(collide);
                    if (object instanceof Person && collide) {
                        Main.Gameloop = false;
                    }
                }
                if (collide) {
                    carcollide = true;
                }


            }
        }
        car.setCollide(carcollide);
    }

    boolean getCollide(WorldObject a, WorldObject b) {

        Rectangle aRectangle = new Rectangle(0, 0, a.getWidth(), a.getHeight());
        Shape sa = a.getTransformation().createTransformedShape(aRectangle);
        Rectangle bRectangle = new Rectangle(0, 0, b.getWidth(), b.getHeight());
        Shape sb = b.getTransformation().createTransformedShape(bRectangle);
        return sa.intersects(sb.getBounds());
    }


}
