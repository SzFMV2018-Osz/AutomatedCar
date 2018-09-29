package hu.oe.nik.szfmv.visualization;

import hu.oe.nik.szfmv.environment.World;
import hu.oe.nik.szfmv.environment.WorldObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * CourseDisplay is for providing a viewport to the virtual world where the simulation happens.
 */
public class CourseDisplay extends JPanel {
    private final int width = 770;
    private final int height = 700;
    private final int backgroundColor = 0xEEEEEE;

    /**
     * Initialize the course display
     */
    public CourseDisplay() {
        // Not using any layout manager, but fixed coordinates
        setDoubleBuffered(true);
        setLayout(null);
        setBounds(0, 0, width, height);
        setBackground(new Color(backgroundColor));
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
    }

    /**
     * Rendering method to avoid flickering
     * 
     * @param world {@link World} object that describes the virtual world
     * @return the ready to render doubleBufferedScreen
     */
    protected BufferedImage renderDoubleBufferedScreen(World world) {
        BufferedImage doubleBufferedScreen = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Rectangle backGroundScreen = new Rectangle(0, 0, width, height);

        doubleBufferedScreen.createGraphics().fill(backGroundScreen);
        doubleBufferedScreen.createGraphics().setBackground(new Color(backgroundColor));

        for (WorldObject object : world.getWorldObjects()) {
            object.RotateImage();

            doubleBufferedScreen.createGraphics().drawImage(object.getImage(), object.getTransformation(), this);
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
}
