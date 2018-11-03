package hu.oe.nik.szfmv.environment;


import hu.oe.nik.szfmv.model.Classes.Dynamic;
import hu.oe.nik.szfmv.model.Classes.Static;


//import hu.oe.nik.szfmv.model.XMLRead;


import java.util.ArrayList;
import java.util.List;

public class World {
    private int width = 0;
    private int height = 0;
    private List<WorldObject> worldObjects = new ArrayList<>();


    /**
     * Creates the virtual world with the given dimension.
     *
     * @param width  the width of the virtual world
     * @param height the height of the virtual world
     */
    public World(int width, int height) {
        this.width = width;
        this.height = height;

      //  complexWorldObjects= new Xml().Obj_List();
=======
      //  worldObjects = XMLRead.Read();

    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<WorldObject> getWorldObjects() {
        return worldObjects;
    }


    /**
     * Add an object to the virtual world.
     *
     * @param o {@link WorldObject} to be added to the virtual world
     */
    public void addObjectToWorld(WorldObject o) {
        worldObjects.add(o);
    }




}
