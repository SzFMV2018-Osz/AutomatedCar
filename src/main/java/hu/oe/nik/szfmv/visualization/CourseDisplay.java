package hu.oe.nik.szfmv.visualization;


import hu.oe.nik.szfmv.Main;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.sensors.RadarSensor;
import hu.oe.nik.szfmv.automatedcar.sensors.UltrasonicSensor;
import hu.oe.nik.szfmv.environment.World;
import hu.oe.nik.szfmv.environment.WorldObject;
import hu.oe.nik.szfmv.model.Interfaces.ICollidable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * CourseDisplay is for providing a viewport to the virtual world where the simulation happens.
 */
public class CourseDisplay extends JPanel {

    private static final double QUARTERTO = 0.75;
    private static final int FIFTY = 50;
    private static final int STROKE_WIDTH = 5;
    private static final int RED = 255;
    private static final int ALPHA = 128;

    public Camera camera;
    public Boolean showUltrasonicSensor;
    private Boolean showRadarSensor;

    private AffineTransform t = new AffineTransform();
    private Gui parent;

    private final int width = 770;
    private final int height = 700;
    private final int backgroundColor = 0xEEEEEE;
    private BufferedImage image;


    /**
     * Initialize the course display
     *
     * @param pt parent Gui
     */
    CourseDisplay(Gui pt) {
        // Not using any layout manager, but fixed coordinates
        setDoubleBuffered(true);
        setLayout(null);
        setBounds(0, 0, width, height);
        try {
            image = ImageIO.read(new File(ClassLoader.getSystemResource("gameover.png").getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        t.scale(QUARTERTO, QUARTERTO);
        t.translate(width / 2.0 - image.getWidth() / 2.0 * QUARTERTO,
                height / 2.0 - image.getHeight() / 2.0 * QUARTERTO - FIFTY);


        parent = pt;
        showUltrasonicSensor = false;
        showRadarSensor = false;
    }

    Boolean getShowRadarSensor() {
        return showRadarSensor;
    }

    void setShowRadarSensor(Boolean showRadarSensor) {
        this.showRadarSensor = showRadarSensor;
    }

    /**
     * Draws the world to the course display
     *
     * @param world {@link World} object that describes the virtual world
     */
    public void drawWorld(World world) {
        paintComponent(getGraphics(), world);
    }

    /**
     * Inherited method that can paint on the JPanel.
     *
     * @param g     {@link Graphics} object that can draw to the canvas
     * @param world {@link World} object that describes the virtual world
     */
    private void paintComponent(Graphics g, World world) {

        g.drawImage(renderDoubleBufferedScreen(world), 0, 0, this);
    }

    /**
     * Rendering method to avoid flickering
     *
     * @param world {@link World} object that describes the virtual world
     * @return the ready to render doubleBufferedScreen
     */
    private BufferedImage renderDoubleBufferedScreen(World world) {
        BufferedImage doubleBufferedScreen = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) doubleBufferedScreen.getGraphics();
        Rectangle r = new Rectangle(0, 0, width, height);
        g2d.setPaint(new Color(backgroundColor));
        g2d.fill(r);

        camera.update();

        drawObjects(g2d, world);

        if (!Main.Gameloop) {
            g2d.drawImage(image, t, this);
        }

        if (showUltrasonicSensor) {
            drawSensor(g2d, world);
        }

        if (showRadarSensor) {
            drawRadarSensor(g2d, world);
        }

        return doubleBufferedScreen;
    }

    /**
     * Intended to use for refreshing the course display after redrawing the world
     */
    public void refreshFrame() {
        invalidate();
        validate();
        repaint();
    }

    private void drawObjects(Graphics2D g2d,  World world) {

        //Rectangle collision;
        //Shape s;
        for (WorldObject object : world.getWorldObjects()) {

            g2d.drawImage(object.getImage(), object.getTransformation(), this);

            // eredetileg ez volt az if ben object instanceof ICollidable && false ennek semmi értelme, ez akart lenni? !(object instanceof ICollidable)
            // ennek így semmi értelme mert semmit nem csinál

            /*
            if (object instanceof ICollidable && false) {
                collision = new Rectangle(
                        object.getWidth() / 2 - object.getPhysicsModel().getWidth() / 2,
                        object.getHeight() / 2 - object.getPhysicsModel().getHeight() / 2,
                        object.getPhysicsModel().getWidth(),
                        object.getPhysicsModel().getHeight());
                s = object.getTransformation().createTransformedShape(collision);

                //mire kellenek, ki akarta vki rajzolni? nem voltak használatban
                // g2d.draw(collision);
                // g2d.draw(s);

                g2d.setPaint(new Color(RED, 0, 0, ALPHA));
                g2d.fill(s);
            }
            */

        }

    }

    private void drawSensor(Graphics2D g, World world) {
        AffineTransform transformTheImageToCorrectPos;
        transformTheImageToCorrectPos = new AffineTransform();
        transformTheImageToCorrectPos.scale(camera.getScale(), camera.getScale());
        transformTheImageToCorrectPos.translate(camera.getX(), camera.getY());

        for (UltrasonicSensor sensor : parent.getVirtualFunctionBus().ultrasonicSensors
        ) {
            sensor.setClosest(sensor.closestObject(sensor.detectedObjects(world.getColladibleObjects())));
            WorldObject closest=sensor.getClosest();

            g.setColor(Color.GREEN);
            g.setTransform(transformTheImageToCorrectPos);
            g.drawPolygon(sensor.getPoly());
            g.setColor(Color.RED);
            if (closest != null) {
                closest.rotateImage(camera);
                g.setTransform(closest.getTransformation());
                g.drawRect(0,0, closest.getWidth(), closest.getHeight());
            }
        }
    }

    private void drawRadarSensor(Graphics2D g, World w) {
        AffineTransform transformTheImageToCorrectPos;
        transformTheImageToCorrectPos = new AffineTransform();
        transformTheImageToCorrectPos.scale(camera.getScale(), camera.getScale());
        transformTheImageToCorrectPos.translate(camera.getX(), camera.getY());

        RadarSensor radar = parent.getVirtualFunctionBus().radarSensor;

        g.setColor(Color.yellow);
        g.setStroke(new BasicStroke(STROKE_WIDTH));
        g.setTransform(transformTheImageToCorrectPos);
        g.drawPolygon(radar.getPolygon());

        g.setColor(Color.RED);
        radar.detectedObjects(w.getColladibleObjects())
                .stream()
                .filter(item -> !(item instanceof AutomatedCar))
                .forEach(item -> g.drawRect(item.getX(), item.getY(), item.getWidth(), item.getHeight()));

    }
}
