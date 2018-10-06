package hu.oe.nik.szfmv.visualization;

import hu.oe.nik.szfmv.environment.World;
import hu.oe.nik.szfmv.environment.WorldObject;

public class Camera {
    private int x;
    private int y;
    private int objectLastX;
    private int objectLastY;
    private WorldObject followableObject;
    private int displayWidth;
    private int displayHeight;
    private World world;


    /**
     * initializing the camera
     *
     * @param x             the x cord of the camera
     * @param y             the y cord of the camera
     * @param displayWidth  the width of the display
     * @param displayHeight the height of the display
     * @param world         the world object that contains the game world
     */
    public Camera(int x, int y, int displayWidth, int displayHeight, World world) {
        this.x = x;
        this.y = y;
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;
        this.world = world;
    }

    /**
     * Initializing the camera
     *
     * @param displayWidth  the width of the display
     * @param displayHeight the height of the display
     * @param world         the world object that contains the game world
     * @param followable    the object followed by the camera
     */
    public Camera(int displayWidth, int displayHeight, World world, WorldObject followable) {
        this.world = world;
        this.displayHeight = displayHeight;
        this.displayWidth = displayWidth;
        this.x = 0;
        this.y = 0;
        this.followableObject = followable;
        this.objectLastX = followableObject.getX();
        this.objectLastY = followableObject.getY();
    }

    /**
     * Updating the position of the camera when the camera have a followable object
     */
    public void Update() {
        if ((followableObject.getX() > (displayWidth / 2 - followableObject.getWidth() / 2) &&
                followableObject.getX() < world.getWidth() - (displayWidth / 2 - followableObject.getWidth() / 2))) {
          //  this.x = displayWidth / 2 - followableObject.getX() - followableObject.getWidth() / 2;
        }
        if ((followableObject.getY() > (displayHeight / 2 - followableObject.getHeight() / 2) &&
                followableObject.getY() < world.getHeight() - (displayHeight / 2 - followableObject.getHeight() / 2))) {
         //   this.y = displayHeight / 2 - followableObject.getY() - followableObject.getHeight() / 2;
        }
        System.out.println("x,y: " + x + "," + y);
        this.objectLastX = followableObject.getX();
        this.objectLastY = followableObject.getY();
    }

    /**
     * Moving the camera inside the game field
     *
     * @param deltaX the movement value of the x cord
     * @param deltaY the movement value of the y cord
     */
    public void MoveCamera(int deltaX, int deltaY) {
        if (this.x + deltaX <= 0 &&
                Math.abs(this.x + deltaX) <= world.getWidth() - (displayWidth / 2)) {
            x += deltaX;
        }

        if (this.y + deltaY <= 0 &&
                Math.abs(this.y + deltaY) <= world.getHeight() - (displayHeight / 2)) {
            y += deltaY;
        }
    }

    /**
     * @return the x cord of the camera
     */
    public int getX() {
        return x;
    }

    /**
     * @return the x cord of the camera
     */
    public int getY() {
        return y;
    }
}