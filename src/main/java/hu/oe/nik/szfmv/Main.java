package hu.oe.nik.szfmv;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.ParkingPilot;
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
import java.awt.event.WindowEvent;
public class Main {
    private static final Logger LOGGER = LogManager.getLogger();
    public static boolean gameLoop = true;
    private static boolean isClosing = false;

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
        AutomatedCar car = new AutomatedCar(480, 840, "car_2_white.png", w.getWorldObjects());
        //car.getVirtualFunctionBus().worldObjects = w.getWorldObjects();
        Person person = new Person(1500,500,"man.png");
    //    NonPlayableCar car1 = new NonPlayableCar(340,1500,"car_2_red.png") ; // 1800
        NonPlayableCar car1 = new NonPlayableCar(343,1500,"car_2_red.png") ;
        NonPlayableCar parking1 = new NonPlayableCar(475,1750,"car_2_red.png") ;
        NonPlayableCar parking2 = new NonPlayableCar(475,1150,"car_2_blue.png") ;
       // NonPlayableCar car2 = new NonPlayableCar(343-175,1800,"car_1_blue.png") ;
       // car2.setSpeed(10);

        // add car to the world


        w.addObjectToWorld(person);
        w.addObjectToWorld(car1);
        w.addObjectToWorld(parking1);
        w.addObjectToWorld(parking2);

        car.getVirtualFunctionBus().worldObjects = w.getWorldObjects();
      //  w.addObjectToWorld(car2);

        w.addObjectToWorld(car);

        //  w.addObjectToWorld(car2);

        // person.setRoute(100,750,8,false);

        // create gui
        Gui gui = new Gui();

        //Give automated car the dashboard as paramter to use it's data
        car.setOwnDashBoardData(gui.getDashboard());

        gui.setVirtualFunctionBus(car.getVirtualFunctionBus());
        car.setGui(gui);

        // create camera
        CourseDisplay display = gui.getCourseDisplay();
        display.camera = new Camera(display.getWidth(), display.getHeight(), w, car);
        for (WorldObject object : w.getWorldObjects()) {
            object.rotateImage(display.camera);
        }

        gui.addKeyListener(new Keychecker(display.camera));
        // draw world to course display
        gui.getCourseDisplay().drawWorld(w);
        gui.addWindowListener(new java.awt.event.WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent e) {
                isClosing = true;
                super.windowClosing(e);

            }
        });
        t.initialize();
        while (!isClosing) {
            try {

               
              
              
              

                if (gameLoop) {
  //gui.handleKeysPressed(); //is it still necessary
                    gui.inputUpdate();
                    car.drive();
                    if(person.getPhysicsModel().getDamage() == 0) {
                        person.moveperson();
                    }
                    if (car1.getPhysicsModel().getDamage() == 0) {
                        car1.movecar1();
                    }
                    physics.update(w, display.camera);

                    //PARKOLÁSVIZSGÁLAT

                    car.parkingSpotSeeking(w.getWorldObjects());

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