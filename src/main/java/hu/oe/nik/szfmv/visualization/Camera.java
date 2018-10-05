package hu.oe.nik.szfmv.visualization;

import hu.oe.nik.szfmv.environment.World;
import hu.oe.nik.szfmv.environment.WorldObject;

public class Camera{
    private int x;
    private int y;
    private int objectLastX;
    private int objectLastY;
    private WorldObject followableObject;
    private int displayWidth;
    private int displayHeight;
    private World world;


    public Camera (int x, int y)
    {
        this.x=x;
        this.y=y;
    }


    public Camera (int displayWidth,int displayHeight , World world, WorldObject followable)
    {
        this.world=world;
        this.displayHeight=displayHeight;
        this.displayWidth=displayWidth;
        this.x = displayWidth / 2 - followable.getX() - followable.getWidth() / 2;
        this.y = displayHeight/2 - followable.getY() - followable.getHeight() / 2;
        this.followableObject=followable;
        this.objectLastX=followableObject.getX();
        this.objectLastY=followableObject.getY();
    }

    public void Update()
    {
        if(!(this.x<0 || this.x>world.getWidth()))
        {
            this.x += objectLastX-followableObject.getX();
        }
        if(!(this.y<0 || this.y>world.getHeight())) {
            this.y += objectLastY - followableObject.getY();
        }
        this.objectLastX=followableObject.getX();
        this.objectLastY=followableObject.getY();
    }

    public void MoveCamera(int deltaX, int deltaY)
    {
        x+=deltaX;
        y+=deltaY;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }
}