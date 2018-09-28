package hu.oe.nik.szfmv.visualization;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.environment.World;

public class CameraUpdate {

    private int dx;
    private int dy;
    private CourseDisplay courseDisplay;
    private boolean once=false;

    private void Init(AutomatedCar automatedCar)
    {
        if(once == false) {
            int left = courseDisplay.getWidth()/2 - 60; //automatedcar width kéne
            dx += left;
            int top = courseDisplay.getHeight()/2 - 125; // automatedcar height kéne
            dy += top;
            once=true;
        }
    }

    public CameraUpdate (AutomatedCar automatedCar, World world,CourseDisplay courseDisplay)
    {
        this.courseDisplay=courseDisplay;
        Init(automatedCar);
        dx =+ world.getWorldObjects().get(0).getX()-dx;
        dy =+ world.getWorldObjects().get(0).getY()-dy;
        //OutofBorder(dx,dy,world,courseDisplay);
    }


    public int GetDx(){
        return dx;
    }

    public int GetDy() {
        return dy;
    }

    public boolean OutofBorder (int dx, int dy, World world, CourseDisplay courseDisplay)
    {
        if(dx>0 && dy>0 && dx<courseDisplay.getWidth() && dy<courseDisplay.getHeight())
        {
            dx =+ world.getWorldObjects().get(0).getX()-dx;
            dy =+ world.getWorldObjects().get(0).getY()-dy;
            return  true;
        }
        else if (dx>0 && dy<0)
        {

        }
        else if (dx>courseDisplay.getWidth() && dy<courseDisplay.getHeight())
        {

        }
        return false;
    }
}
