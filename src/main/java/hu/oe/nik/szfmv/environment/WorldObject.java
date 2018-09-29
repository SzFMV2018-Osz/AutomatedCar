package hu.oe.nik.szfmv.environment;


import hu.oe.nik.szfmv.visualization.IRender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WorldObject implements IRender {
    private static final Logger LOGGER = LogManager.getLogger();
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected float rotation = 0f;
    protected String imageFileName;

    protected int rotationPointX; // néhány world elemet előre definiált pont körül kell forgatni.
    protected int rotationPointY;
    protected BufferedImage image;
    protected AffineTransform transformTheImageToCorrectPos;

    /**
     * Creates an object of the virtual world on the given coordinates with the given image.
     *
     * @param x             the initial x coordinate of the object
     * @param y             the initial y coordinate of the object
     * @param imageFileName the filename of the image representing the object in the virtual world
     */
    public WorldObject(int x, int y, String imageFileName) {
        this.x = x;
        this.y = y;
        this.rotationPointX = x;
        this.rotationPointY = y;
        this.imageFileName = imageFileName;
        InitImage();

    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public float getRotation() {
        return this.rotation;
    }

    public String getImageFileName() {
        return this.imageFileName;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public AffineTransform getTransformation() {
        return transformTheImageToCorrectPos;
    }

    @Override
    public void InitImage() {
        try {
            image = ImageIO.read(new File(ClassLoader.getSystemResource(imageFileName).getFile()));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void RotateImage() {
        transformTheImageToCorrectPos = new AffineTransform();
        transformTheImageToCorrectPos.rotate(Math.toRadians(rotation), rotationPointX, rotationPointY);
        transformTheImageToCorrectPos.translate(x, y);
    }
}
