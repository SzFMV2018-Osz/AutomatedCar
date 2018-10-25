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

    protected Rectangle getCollision(Rectangle rect1, Rectangle rect2) {
        Area a1 = new Area(rect1);
        Area a2 = new Area(rect2);
        a1.intersect(a2);
        return a1.getBounds();
    }

   /* protected boolean collision(WorldObject a, WorldObject b, int x, int y) {
        boolean collision = false;

        int spiderPixel = spider.getRGB(x - spiderBounds.x, y - spiderBounds.y);
        int flyPixel = fly.getRGB(x - flyBounds.x, y - flyBounds.y);
        // 255 is completely transparent, you might consider using something
        // a little less absolute, like 225, to give you a sligtly
        // higher hit right, for example...
        if (((spiderPixel >> 24) & 0xFF) < 255 && ((flyPixel >> 24) & 0xFF) < 255) {
            collision = true;
        }
        return collision;
    }*/
}
