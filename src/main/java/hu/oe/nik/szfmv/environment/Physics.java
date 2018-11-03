package hu.oe.nik.szfmv.environment;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.model.Interfaces.ICollidable;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;


public class Physics
{



   public void update(World world){
        AutomatedCar car    = (AutomatedCar) world.getWorldObjects().stream().filter(x -> x instanceof AutomatedCar).findAny().orElse(null);
       for (WorldObject object : world.getWorldObjects()) {
          if(object instanceof ICollidable){
              boolean collide =   getCollide(car,object) && !car.equals(object);


                  if(!car.equals(object)) {
                      car.setCollide(collide);

                      object.setCollide(collide);
                  }


          }
       }
   }

   boolean getCollide(WorldObject a, WorldObject b){
       Rectangle aRectangle = new Rectangle(a.getX(),a.getY(),a.getWidth(),a.getHeight());
       Rectangle bRectangle = new Rectangle(b.getX(),b.getY(),b.getWidth(),b.getHeight());
       return aRectangle.intersects(bRectangle);
   }


}
