package hu.oe.nik.szfmv.visualization;

import hu.oe.nik.szfmv.environment.World;
import hu.oe.nik.szfmv.environment.WorldObject;
import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;


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
        g.drawImage(renderDoubleBufferedScreen(world),0,0,this);
    }


    protected BufferedImage renderDoubleBufferedScreen(World world){
        BufferedImage DoubleBufferedScreen = new BufferedImage(width, height,BufferedImage.TYPE_INT_ARGB);
        Rectangle BackGroundScreen = new Rectangle(0,0,width,height);

        DoubleBufferedScreen.createGraphics().fill(BackGroundScreen);
        DoubleBufferedScreen.createGraphics().setBackground(new Color(backgroundColor));


        // super.paintComponent(g);
        for (WorldObject object : world.getWorldObjects()) {
            object.RotateImage();

            DoubleBufferedScreen.createGraphics().drawImage(object.getImage(),object.getTransformation(),this); // see javadoc for more info on the parameters
        }
        return DoubleBufferedScreen;
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
