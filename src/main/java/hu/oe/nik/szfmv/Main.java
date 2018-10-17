package hu.oe.nik.szfmv;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.common.ConfigProvider;
import hu.oe.nik.szfmv.environment.World;
import hu.oe.nik.szfmv.model.XML_read_in.XMLReader;
import hu.oe.nik.szfmv.visualization.Camera;
import hu.oe.nik.szfmv.visualization.CourseDisplay;
import hu.oe.nik.szfmv.visualization.Gui;
import hu.oe.nik.szfmv.visualization.Timer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int[] ys = {875, 875, 875, 0, 525, 525, 371, 371, 367, 367, 104, 104};
    private static int[] xs = {0, 0, 0, 874, 175, 349, 51, 351, 17, 350, 51, 51};

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
      //  World w = new World(800, 600);
        World w = XMLReader.WorldMaker();

       /* try {
            File fXmlFile = new File(ClassLoader.getSystemResource("test_world.xml").getFile());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            System.out.println(doc.getDocumentElement().getFirstChild().getNodeName());
            w.setWidth(Integer.parseInt(doc.getDocumentElement().getAttribute("width")));
            w.setHeight(Integer.parseInt(doc.getDocumentElement().getAttribute("height")));

            NodeList objects = doc.getDocumentElement().getElementsByTagName("Object");
            int ocount = objects.getLength();
            NodeList poss = doc.getDocumentElement().getElementsByTagName("Position");
            int pcount = poss.getLength();
            NodeList trans = doc.getDocumentElement().getElementsByTagName("Transform");
            int tcount = trans.getLength();
            String[] names = {"road_2lane_rotary.png", "2_crossroad_1.png",
                    "2_crossroad_2.png", "road_2lane_tjunctionleft.png",
                    "road_2lane_90left.png", "road_2lane_90right.png", "road_2lane_45left.png",
                    "road_2lane_45right.png", "road_2lane_6left.png",
                    "road_2lane_6right.png", "car_2_white.png", "car_2_red.png"};

            for (int i = 0; i < objects.getLength(); i++) {
                String filename = objects.item(i).getAttributes().getNamedItem("type").getNodeValue() + ".png";
                int x = Integer.parseInt(poss.item(i).getAttributes().getNamedItem("x").getNodeValue());
                int y = Integer.parseInt(poss.item(i).getAttributes().getNamedItem("y").getNodeValue());
                double m11 = Double.parseDouble(trans.item(i).getAttributes().getNamedItem("m11").getNodeValue());
                double m12 = Double.parseDouble(trans.item(i).getAttributes().getNamedItem("m12").getNodeValue());
                double m21 = Double.parseDouble(trans.item(i).getAttributes().getNamedItem("m21").getNodeValue());
                double m22 = Double.parseDouble(trans.item(i).getAttributes().getNamedItem("m22").getNodeValue());
                double matrix = Utils.convertMatrixToRadians(m11, m12, m21, m22);
                float routate = (float) Utils.radianToDegree(matrix);
                WorldObject obj = new WorldObject(x, y, filename);
                switch (filename)
                {
                    case "road_2lane_rotary.png":
                        obj = new Road(x,y,filename);
                        break;
                    case "2_crossroad_1.png":
                        obj = new Road(x,y,filename);
                        break;
                    case "2_crossroad_2.png":
                        obj = new Road(x,y,filename);
                        break;
                    case "road_2lane_tjunctionleft.png":
                        obj = new Road(x,y,filename);
                        break;
                    case "road_2lane_90left.png":
                        obj = new Road(x,y,filename);
                        break;
                    case "road_2lane_90right.png":
                        obj = new Road(x,y,filename);
                        break;
                    case "road_2lane_45left.png":
                        obj = new Road(x,y,filename);
                        break;
                    case "road_2lane_45right.png":
                        obj = new Road(x,y,filename);
                        break;
                    case "road_2lane_6left.png":
                        obj = new Road(x,y,filename);
                        break;
                    case "road_2lane_6right.png":
                        obj = new Road(x,y,filename);
                        break;
                    case "car_2_white.png":
                        obj = new NonPlayableCar(x,y,filename);
                        break;
                    case "car_2_red.png":
                        obj = new NonPlayableCar(x,y,filename);
                        break;
                    default:
                             obj = new WorldObject(x, y, filename);
                             break;

                }

                obj.setRotation(routate);
                for (int l = 0; l < names.length; l++) {
                    if (names[l].equals(filename)) {
                        obj.setRotationPointX(xs[l]);
                        obj.setRotationPointY(ys[l]);
                    }
                }
                w.addObjectToWorld(obj);

            }


        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }*/


        // create an automated car
        AutomatedCar car = new AutomatedCar(20, 20, "car_2_white.png");

        // add car to the world
        w.addObjectToWorld(car);
        // create gui
        Gui gui = new Gui();

        // create camera
        CourseDisplay display = gui.getCourseDisplay();
        display.camera = new Camera(display.getWidth(), display.getHeight(), w, car);
        gui.addKeyListener(new Keychecker(display.camera));
        // draw world to course display
        gui.getCourseDisplay().drawWorld(w);
        t.initialize();
        while (true) {
            try {
                car.drive();
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
            camera.MoveCamera(movespeed, 0);
        }
        if (event.getKeyChar() == 'd') {
            camera.MoveCamera(-movespeed, 0);
        }
        if (event.getKeyChar() == 'w') {
            camera.MoveCamera(0, movespeed);
        }
        if (event.getKeyChar() == 's') {
            camera.MoveCamera(0, -movespeed);
        }

    }
}
