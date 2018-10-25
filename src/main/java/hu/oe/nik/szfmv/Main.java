package hu.oe.nik.szfmv;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.common.ConfigProvider;
import hu.oe.nik.szfmv.environment.World;
import hu.oe.nik.szfmv.model.Classes.Person;
import hu.oe.nik.szfmv.model.Classes.Tree;
import hu.oe.nik.szfmv.model.XML_read_in.XMLReader;
import hu.oe.nik.szfmv.visualization.Camera;
import hu.oe.nik.szfmv.visualization.CourseDisplay;
import hu.oe.nik.szfmv.visualization.Gui;
import hu.oe.nik.szfmv.visualization.Timer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int[] yS = {875, 875, 875, 0, 525, 525, 371, 371, 367, 367, 104, 104};
    private static int[] xS = {0, 0, 0, 874, 175, 349, 51, 351, 17, 350, 51, 51};

    /**
     * Main entrypoint of the software
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        // log the current debug mode in config
        LOGGER.info(ConfigProvider.provide().getBoolean("general.debug"));

        // set timer for fps rate
        Timer t = new Timer();
        t.setTargetFps(24);

        // create the world
        World w = XMLReader.worldMaker();

        // create an automated car
        AutomatedCar car = new AutomatedCar(100, 100, "car_2_white.png");
        Person person = new Person(150,150,"man.png");


        // add car to the world
        w.addObjectToWorld(car);
        w.addObjectToWorld(person);

        person.setRoute(50,50,4,false);
        // create gui
        Gui gui = new Gui();
        gui.setVirtualFunctionBus(car.getVirtualFunctionBus());

        // create camera
        CourseDisplay display = gui.getCourseDisplay();
        display.camera = new Camera(display.getWidth(), display.getHeight(), w, car);
        // gui.addKeyListener(new Keychecker(display.camera));
        // draw world to course display
        gui.getCourseDisplay().drawWorld(w);
        t.initialize();
        while (true) {
            try {
                car.drive();
                person.move();
                gui.getCourseDisplay().drawWorld(w);
                t.updateFPS();
                Thread.sleep(t.getCyclePeriod());
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
        }

    }

}

/**
 * A key checker for camera debug porpuse
 */
class Keychecker extends KeyAdapter {
    private final int movespeed = 10;
    private Camera camera;

    /**
     * Init the key checker
     *
     * @param c the camera object to move the camera
     */
    public Keychecker(Camera c) {
        this.camera = c;


    }

    /**
     * Moving the camera
     *
     * @param event the key event tringered by pushing a button
     */
    @Override
    public void keyPressed(KeyEvent event) {

        if (event.getKeyChar() == 'a') {
            camera.moveCamera(movespeed, 0);
        }
        if (event.getKeyChar() == 'd') {
            camera.moveCamera(-movespeed, 0);
        }
        if (event.getKeyChar() == 'w') {
            camera.moveCamera(0, movespeed);
        }
        if (event.getKeyChar() == 's') {
            camera.moveCamera(0, -movespeed);
        }

    }
}
