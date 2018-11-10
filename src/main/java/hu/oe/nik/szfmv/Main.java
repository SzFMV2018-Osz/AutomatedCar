package hu.oe.nik.szfmv;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.common.ConfigProvider;
import hu.oe.nik.szfmv.environment.Physics;
import hu.oe.nik.szfmv.environment.World;
import hu.oe.nik.szfmv.environment.WorldObject;
import hu.oe.nik.szfmv.model.Classes.Car;
import hu.oe.nik.szfmv.model.Classes.NonPlayableCar;
import hu.oe.nik.szfmv.model.Classes.Person;
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
    public static boolean Gameloop = true;
    private static int[] xS = {0, 0, 0, 874, 175, 349, 51, 351, 17, 350, 51, 51};

    /**
     * Main entrypoint of the software
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        // log the current debug mode in config
        LOGGER.info(ConfigProvider.provide().getBoolean("general.debug"));
        Physics physics = new Physics();
        // set timer for fps rate
        Timer t = new Timer();
        t.setTargetFps(24);

        // create the world
        World w = XMLReader.worldMaker();

        // create an automated car
        AutomatedCar car = new AutomatedCar(480, 840, "car_2_white.png");
        car.getVirtualFunctionBus().worldObjects = w.getWorldObjects();
        car.createSensors();
        Person person = new Person(1500,500,"man.png");
    //    NonPlayableCar car1 = new NonPlayableCar(340,1500,"car_2_red.png") ; // 1800
        NonPlayableCar car1 = new NonPlayableCar(343,1500,"car_2_red.png") ;
       // NonPlayableCar car2 = new NonPlayableCar(343-175,1800,"car_1_blue.png") ;
       // car2.setSpeed(10);
        Car c = new Car(1500, 1500, "car_2_red.png");
        // add car to the world


        w.addObjectToWorld(person);
        w.addObjectToWorld(car1);

        car.getVirtualFunctionBus().worldObjects = w.getWorldObjects();
      //  w.addObjectToWorld(car2);

        w.addObjectToWorld(c);
        w.addObjectToWorld(car);



        //  w.addObjectToWorld(car2);

        // person.setRoute(100,750,8,false);

        // create gui
        Gui gui = new Gui();
        gui.setVirtualFunctionBus(car.getVirtualFunctionBus());

        // create camera
        CourseDisplay display = gui.getCourseDisplay();
        display.camera = new Camera(display.getWidth(), display.getHeight(), w, car);
        for (WorldObject object : w.getWorldObjects()) {
            object.rotateImage(display.camera);
        }

        gui.addKeyListener(new Keychecker(display.camera));
        // draw world to course display
        gui.getCourseDisplay().drawWorld(w);
        t.initialize();
        while (true) {
            try {






                if (Gameloop) {
                    gui.handleKeysPressedFast(); //is it still necessary
                    car.drive();
                    person.moveperson();
                    car1.movecar1();
                    physics.update(w, display.camera);
                }


                // car2.movecar2();
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
        if (event.getKeyChar() == '+') {
            camera.setScale(camera.getScale() + 0.1);
        }
        if (event.getKeyChar() == '-') {
            camera.setScale(camera.getScale() - 0.1);
        }


    }
}
