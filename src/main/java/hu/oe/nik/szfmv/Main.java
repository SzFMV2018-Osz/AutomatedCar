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
        AutomatedCar car = new AutomatedCar(480, 840, "car_2_white.png");
        Car c = new Car(1500, 1500, "car_2_red.png");
        Person person = new Person(1500, 500, "man.png");
        //NonPlayableCar car1 = new NonPlayableCar(340,1500,"car_2_red.png") ; // 1800
        NonPlayableCar car1 = new NonPlayableCar(343, 1500, "car_2_red.png");
        // NonPlayableCar car2 = new NonPlayableCar(343-175,1800,"car_1_blue.png") ;
        // car2.setSpeed(10);

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
        while (isClosing == false) {
            try {


                if (gameLoop) {
                    //gui.handleKeysPressed(); //is it still necessary
                    gui.inputUpdate();
                    car.drive();
                    person.moveperson();
                    if (car1.getPhysicsModel().getDamage() == 0)
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
