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
    protected double[][] t_Matrix;


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
        this.rotationPointX = 0;
        this.rotationPointY = 0;
        this.imageFileName = imageFileName;
        InitImage();
        t_Matrix = new double[2][2];
    }

    public WorldObject(int x, int y, String imageFileName, double m11, double m12, double m21, double m22) {
        this.x = x;
        this.y = y;
        this.rotationPointX = 0;
        this.rotationPointY = 0;
        this.imageFileName = imageFileName;
        InitImage();
        t_Matrix[1][1] = m11;
        t_Matrix[1][2] = m12;
        t_Matrix[2][1] = m21;
        t_Matrix[2][2] = m22;
    }

    public double[][] getT_Matrix() {
        return t_Matrix;
    }

    public void setT_Matrix(double[][] t_Matrix) {
        this.t_Matrix = t_Matrix;
    }

    public void setRotationPointX(int rotationPointX) {
        this.rotationPointX = rotationPointX;
    }

    public void setRotationPointY(int rotationPointY) {
        this.rotationPointY = rotationPointY;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getRotation() {
        return this.rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public String getImageFileName() {
        return this.imageFileName;
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

    /**
     * Create the image file for render
     */
    @Override
    public void InitImage() {
        try {
            image = ImageIO.read(new File(ClassLoader.getSystemResource(imageFileName).getFile()));
            this.width = image.getWidth();
            this.height = image.getHeight();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * Rotate the image to the correct pos
     *
     * @param cameraX the camera x for offset
     * @param cameraY the camera y for offset
     */
    @Override
    public void RotateImage(int cameraX, int cameraY) {
        transformTheImageToCorrectPos = new AffineTransform();
        transformTheImageToCorrectPos.rotate(Math.toRadians(-rotation), cameraX + x, cameraY + y);
        transformTheImageToCorrectPos.translate(cameraX + x - rotationPointX, cameraY + y - rotationPointY);

    }
}
