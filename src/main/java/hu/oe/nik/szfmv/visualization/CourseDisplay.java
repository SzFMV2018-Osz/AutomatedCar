package hu.oe.nik.szfmv.visualization;

import hu.oe.nik.szfmv.automatedcar.sensors.ISensor;
import hu.oe.nik.szfmv.environment.World;
import hu.oe.nik.szfmv.environment.WorldObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import hu.oe.nik.szfmv.automatedcar.sensors.UltrasonicSensor;

/**
 * CourseDisplay is for providing a viewport to the virtual world where the simulation happens.
 */
public class CourseDisplay extends JPanel {
    private final int width = 770;
    private final int height = 700;
    private final int backgroundColor = 0xEEEEEE;
    public Camera camera;
    Gui parent;
    public Boolean drawTriangles;

    /**
     * Initialize the course display
     */
    public CourseDisplay(Gui pt) {
        // Not using any layout manager, but fixed coordinates
        setDoubleBuffered(true);
        setLayout(null);
        setBounds(0, 0, width, height);
        parent = pt;
        drawTriangles = false;
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
    protected void paintComponent(Graphics g, World world) {


        g.drawImage(renderDoubleBufferedScreen(world), 0, 0, this);
        if (drawTriangles) {
            drawSensor(g, parent.getVirtualFunctionBus().ultrasonic);
        }
    }

    /**
     * Rendering method to avoid flickering
     *
     * @param world {@link World} object that describes the virtual world
     * @return the ready to render doubleBufferedScreen
     */
    protected BufferedImage renderDoubleBufferedScreen(World world) {
        BufferedImage doubleBufferedScreen = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) doubleBufferedScreen.getGraphics();
        Rectangle r = new Rectangle(0, 0, width, height);
        g2d.setPaint(new Color(backgroundColor));
        g2d.fill(r);

        camera.update();

        for (WorldObject object : world.getWorldObjects()) {
            object.RotateImage(camera.getX(), camera.getY());

            g2d.drawImage(object.getImage(), object.getTransformation(), this);
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

    public void drawSensor(Graphics g, ISensor sensor) {
        g.setColor(Color.GREEN);


        float sensorRotation = 180 - (parent.getVirtualFunctionBus().carPacket.getCarRotation() % 360);
        int carWidth = parent.getVirtualFunctionBus().carPacket.getCarWidth();

        int sersorCenterX = (int) (parent.getVirtualFunctionBus().carPacket.getxPosition() - Math.cos(Math.toRadians(sensorRotation)) * (carWidth / 2) + Math.sin(Math.toRadians(sensorRotation)) * 60);
        int sersorCenterY = (int) (parent.getVirtualFunctionBus().carPacket.getyPosition() - Math.sin(Math.toRadians(sensorRotation)) * 60 - Math.cos(Math.toRadians(sensorRotation)) * (carWidth / 2));

        g.drawPolygon(sensor.locateSensorTriangle(new Point(sersorCenterX, sersorCenterY), 120, 60, sensorRotation + 20));
        g.drawPolygon(sensor.locateSensorTriangle(new Point(sersorCenterX, sersorCenterY), 400, 25, sensorRotation));

    }
}
