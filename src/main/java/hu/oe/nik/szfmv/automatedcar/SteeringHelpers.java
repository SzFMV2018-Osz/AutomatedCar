package hu.oe.nik.szfmv.automatedcar;

import java.awt.geom.Point2D;

// This class has been kindly borrowed from the 2017/2018/2 szfmv course.
// See: https://github.com/SzFMV2018-Tavasz/AutomatedCar/blob/master/src/main/java/hu/oe/nik/szfmv/automatedcar/SteeringMethods.java

public class SteeringHelpers {
    /**
     * @param xy             Car x and y position
     * @param carHeading     Car heading
     * @param speed          Car speed
     * @param angularSpeed   Car wheel position in radian
     * @param widthAndHeight Car image width and height
     * @return Return with car position and car heading
     */
    public static Object[] getCarPositionAndCarHead(Point2D xy, double carHeading, double speed, double angularSpeed,
                                                    int[] widthAndHeight) {
        xy = new Point2D.Double(xy.getX() + widthAndHeight[0] / 2,
                xy.getY() + widthAndHeight[1] / 2);
        Point2D[] frontAndBack = getFrontAndBackWheel(xy, carHeading, speed, angularSpeed, widthAndHeight);

        Object[] carPositionAndCarHead = new Object[2];

        carPositionAndCarHead[0] = getCarPosition(frontAndBack[0], frontAndBack[1]);
        carPositionAndCarHead[1] = getCarHeading(frontAndBack[0], frontAndBack[1]);

        return carPositionAndCarHead;
    }

    /**
     * @param xy             Car x and y position
     * @param carHeading     Car heading
     * @param speed          Car speed
     * @param angularSpeed   Car wheel position in radian
     * @param widthAndHeight Car image width and height
     * @return Return with car front and back wheel position
     */
    public static Point2D[] getFrontAndBackWheel(Point2D xy, double carHeading, double speed, double angularSpeed,
                                                 int[] widthAndHeight) {
        int halfWheelBase = widthAndHeight[1] / 2;
        int fps = 1;

        Point2D frontWheel = getFrontWheel(carHeading, halfWheelBase, xy);
        Point2D backWheel = getBackWheel(carHeading, halfWheelBase, xy);

        Point2D backWheelDisplacement = getBackWheelDisplacement(carHeading, speed, fps);
        Point2D frontWheelDisplacement =
                getFrontWheelDisplacement(carHeading, angularSpeed, speed, fps);

        frontWheel = getNewFrontWheelPosition(frontWheel, frontWheelDisplacement);
        backWheel = getNewBackWheelPosition(backWheel, backWheelDisplacement);

        Point2D[] frontAndBack = new Point2D[2];
        frontAndBack[0] = frontWheel;
        frontAndBack[1] = backWheel;

        return frontAndBack;
    }

    /**
     * Returns the position of the car based on its two wheels by calculating the middle point between two points
     *
     * @param frontWheel Position of the front wheel
     * @param backWheel  Position of the back wheel
     * @return Position of the car based on its wheels
     */
    public static double getCarHeading(Point2D frontWheel, Point2D backWheel) {
        return Math.atan2(frontWheel.getY() - backWheel.getY(), frontWheel.getX() - backWheel.getX());
    }

    /**
     * Get [-100,100] percent and it give back a value which between -60 and 60 degree.
     *
     * @param wheelPosition in percent form.
     * @return steeringAngle between -60 and 60 degree.
     * @throws Exception wrong parameter Exception
     */
    public static double getSteerAngle(double wheelPosition) {

        final double maxLeft = 100d;
        final double maxRight = -100d;

        if (wheelPosition > maxLeft) {
            wheelPosition = maxLeft;
        }
        if (wheelPosition < maxRight) {
            wheelPosition = maxRight;
        }

        // From -60 to 60 degree
        double steerAngle;
        final double MULTIPLIER = -0.6;

        steerAngle = wheelPosition * MULTIPLIER;
        steerAngle = Math.toRadians(steerAngle);
        return steerAngle;
    }

    /**
     * Get front wheel position before moving
     *
     * @param carHeading    Car rotation
     * @param halfWheelBase Distance between wheels
     * @param carPosition   Car position, x and y point
     * @return front wheel position
     **/
    public static Point2D getFrontWheel(double carHeading, double halfWheelBase, Point2D carPosition) {
        return new Point2D.Double((Math.cos(carHeading) * halfWheelBase) + carPosition.getX(),
                (Math.sin(carHeading) * halfWheelBase) + carPosition.getY());
    }

    /**
     * Get back wheel position before moving
     *
     * @param carHeading    Car rotation
     * @param halfWheelBase Distance between wheels
     * @param carPosition   Car position, x and y point
     * @return back wheel position
     **/
    public static Point2D getBackWheel(double carHeading, double halfWheelBase, Point2D carPosition) {
        return new Point2D.Double(carPosition.getX() - (Math.cos(carHeading) * halfWheelBase),
                carPosition.getY() - (Math.sin(carHeading) * halfWheelBase));
    }

    /**
     * Get back wheel displacement after moving
     *
     * @param carHeading Car rotation
     * @param speed      Car actual speed
     * @param fps        Display fps
     * @return Back wheel displacement after moving
     **/
    public static Point2D getBackWheelDisplacement(double carHeading, double speed, double fps) {
        return new Point2D.Double(Math.cos(carHeading) * speed * (1 / fps),
                (Math.sin(carHeading) * speed * (1 / fps)));
    }

    /**
     * Get front wheel displacement after moving
     *
     * @param carHeading   Car rotation
     * @param speed        Car actual speed
     * @param angularSpeed Car steering angle
     * @param fps          Display fps
     * @return Front wheel displacement after moving
     **/
    public static Point2D getFrontWheelDisplacement(double carHeading, double angularSpeed, double speed, double fps) {
        return new Point2D.Double(Math.cos(carHeading + angularSpeed) * speed * (1 / fps),
                (Math.sin(carHeading + angularSpeed) * speed * (1 / fps)));
    }

    /**
     * Get new front wheel position
     *
     * @param frontWheel             Old front wheel position
     * @param frontWheelDisplacement Front wheel displacement
     * @return New front position
     */
    public static Point2D getNewFrontWheelPosition(Point2D frontWheel, Point2D frontWheelDisplacement) {
        return new Point2D.Double(frontWheel.getX() + frontWheelDisplacement.getX(),
                frontWheel.getY() + frontWheelDisplacement.getY());
    }

    /**
     * Get new back wheel position
     *
     * @param backWheel             Old back wheel position
     * @param backWheelDisplacement Back wheel displacement
     * @return New back position
     */
    public static Point2D getNewBackWheelPosition(Point2D backWheel, Point2D backWheelDisplacement) {
        return new Point2D.Double(backWheel.getX() + backWheelDisplacement.getX(),
                backWheel.getY() + backWheelDisplacement.getY());
    }

    /**
     * Get back new car position
     *
     * @param frontWheel Front wheel position
     * @param backWheel  Back wheel position
     * @return Car position
     **/
    public static Point2D getCarPosition(Point2D frontWheel, Point2D backWheel) {
        return new Point2D.Double((frontWheel.getX() + backWheel.getX()) / 2,
                (frontWheel.getY() + backWheel.getY()) / 2);
    }
}
