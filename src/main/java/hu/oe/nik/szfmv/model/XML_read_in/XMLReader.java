package hu.oe.nik.szfmv.model.XML_read_in;


import hu.oe.nik.szfmv.common.ConfigProvider;
import hu.oe.nik.szfmv.common.Utils;
import hu.oe.nik.szfmv.environment.World;
import hu.oe.nik.szfmv.environment.WorldObject;
import hu.oe.nik.szfmv.model.Classes.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XMLReader {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int[] yS = {875, 875, 875, 0, 525, 525, 371, 371, 367, 367, 104, 104};
    private static int[] xS = {0, 0, 0, 874, 175, 349, 51, 351, 17, 350, 51, 51};

    private static int width = 800;
    private static int height = 600;

    /**
     *
     * @Creates World object of the elements of rescource xml
     * @return World obejct
     */
    public static World worldMaker() {
        LOGGER.info(ConfigProvider.provide().getBoolean("general.debug"));
        World w = new World(width, height);

        try {
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
            String[] names = {
                "road_2lane_rotary.png", "2_crossroad_1.png", "2_crossroad_2.png",
                "road_2lane_tjunctionleft.png", "road_2lane_90left.png", "road_2lane_90right.png",
                "road_2lane_45left.png", "road_2lane_45right.png", "road_2lane_6left.png",
                "road_2lane_6right.png", "car_2_white.png", "car_2_red.png",
                "road_2lane_straight", "road_2lane_tjunctionright", "parking_space_parallel",
                "crosswalk", "tree", "roadsign_speed_40", "roadsign_speed_50",
                "roadsign_speed_60", "roadsign_priority_stop", "roadsign_parking_right"};

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
                WorldObject obj;
                switch (filename) {
                    case "road_2lane_straight.png":
                        obj = new Road(x, y, filename);
                        break;
                    case "road_2lane_tjunctionright.png":
                        obj = new Road(x, y, filename);
                        break;
                    case "parking_space_parallel.png":
                        obj = new Road(x, y, filename);
                        break;
                    case "crosswalk.png":
                        obj = new Road(x, y, filename);
                        break;
                    case "roadsign_parking_right.png":
                        obj = new RoadSign(x, y, filename);
                        break;
                    case "roadsign_priority_stop.png":
                        obj = new RoadSign(x, y, filename);
                        break;
                    case "roadsign_speed_40.png":
                        obj = new RoadSign(x, y, filename);
                        break;
                    case "roadsign_speed_50.png":
                        obj = new RoadSign(x, y, filename);
                        break;
                    case "roadsign_speed_60.png":
                        obj = new RoadSign(x, y, filename);
                        break;
                    case "tree.png":
                        obj = new Tree(x, y, filename);
                        break;
                    case "road_2lane_rotary.png":
                        obj = new Road(x, y, filename);
                        break;
                    case "2_crossroad_1.png":
                        obj = new Road(x, y, filename);
                        break;
                    case "2_crossroad_2.png":
                        obj = new Road(x, y, filename);
                        break;
                    case "road_2lane_tjunctionleft.png":
                        obj = new Road(x, y, filename);
                        break;
                    case "road_2lane_90left.png":
                        obj = new Road(x, y, filename);
                        break;
                    case "road_2lane_90right.png":
                        obj = new Road(x, y, filename);
                        break;
                    case "road_2lane_45left.png":
                        obj = new Road(x, y, filename);
                        break;
                    case "road_2lane_45right.png":
                        obj = new Road(x, y, filename);
                        break;
                    case "road_2lane_6left.png":
                        obj = new Road(x, y, filename);
                        break;
                    case "road_2lane_6right.png":
                        obj = new Road(x, y, filename);
                        break;
                    case "car_2_white.png":
                        obj = new NonPlayableCar(x, y, filename);
                        break;
                    case "car_2_red.png":
                        obj = new NonPlayableCar(x, y, filename);
                        break;
                    default:
                        obj = new WorldObject(x, y, filename);
                        break;

                }

                obj.setRotation(-routate);
                for (int l = 0; l < names.length; l++) {
                    if (names[l].equals(filename)) {
                        obj.setRotationPointX(xS[l]);
                        obj.setRotationPointY(yS[l]);
                    }
                }
                w.addObjectToWorld(obj);

            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        return w;
    }

    private static List<Dynamic> getDynamicObject(World w) {
        List<Dynamic> dynamicObjects = new ArrayList<>();
        for (WorldObject object : w.getWorldObjects()) {
            if (object instanceof Dynamic) {
                dynamicObjects.add((Dynamic)object);
            }
        }
        return dynamicObjects;
    }

    private static List<Static> getStaticObject(World w) {
        List<Static> staticObjects = new ArrayList<>();
        for (WorldObject object : w.getWorldObjects()) {
            if (object instanceof Static) {
                staticObjects.add((Static) object);
            }
        }
        return staticObjects;
    }
}
