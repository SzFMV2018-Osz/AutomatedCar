package hu.oe.nik.szfmv.visualization;

import hu.oe.nik.szfmv.Main;
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
    private final int width = 770;
    private final int height = 700;
    private final int backgroundColor = 0xEEEEEE;
    public Camera camera;
    AffineTransform t = new AffineTransform();
    private BufferedImage image;

    /**
     * Initialize the course display
     */
    public CourseDisplay() {
        // Not using any layout manager, but fixed coordinates
        setDoubleBuffered(true);
        setLayout(null);
        setBounds(0, 0, width, height);
        try {
            image = ImageIO.read(new File(ClassLoader.getSystemResource("gameover.png").getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        t.scale(0.75, 0.75);
        t.translate(width / 2 - image.getWidth() / 2 * 0.75, height / 2 - image.getHeight() / 2 * 0.75 - 50);

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
        Graphics2D g2d = (Graphics2D) doubleBufferedScreen.getGraphics();
        Rectangle r = new Rectangle(0, 0, width, height);
        g2d.setPaint(new Color(backgroundColor));
        g2d.fill(r);

        camera.update();

        for (WorldObject object : world.getWorldObjects()) {

            g2d.drawImage(object.getImage(), object.getTransformation(), this);
            if (object instanceof ICollidable && false) {

                Rectangle collision = new Rectangle(object.getWidth() / 2 - object.getPhysicsModel().getWidth() / 2, object.getHeight() / 2 - object.getPhysicsModel().getHeight() / 2, object.getPhysicsModel().getWidth(), object.getPhysicsModel().getHeight());
                Shape s = object.getTransformation().createTransformedShape(collision);
                g2d.setPaint(new Color(255, 0, 0, 128));
                g2d.fill(s);
            }

        }
        if (Main.Gameloop == false) {
            g2d.drawImage(image, t, this);
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
