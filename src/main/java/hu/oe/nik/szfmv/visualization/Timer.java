package hu.oe.nik.szfmv.visualization;

public class Timer {
    private static long lastFrame;
    private double TARGET_FPS = 60.0;
    private double OFSET_FPS;
    private int fps;
    private long lastFPS;

    /**
     * return The system time in milisecunds
     *
     * @return The system time in milisecunds
     */
    private static long getTime() {
        return System.nanoTime() / 1000000;
    }

    /**
     * Delta is used in games for decoupling update from framerate
     *
     * @return The time a frame was renderd
     */
    public static long getDelta() {
        long time = getTime();
        long delta = (time - lastFrame);
        lastFrame = time;
        System.out.println("Delta: " + delta);
        return delta;

    }

    /**
     * Init the valuse for the Timer
     */
    public void initialize() {
        lastFPS = getTime();
        lastFrame = getTime();
    }

    /**
     * Calculate the FPS and changing the offset rate to stabilize around the Target FPS
     */
    public void updateFPS() {
        long time = getTime();
        if (time - lastFPS > 1000) {
            System.out.println("FPS: " + fps + ", " + OFSET_FPS + " offset fps, " + TARGET_FPS + " target fps");
            if (fps != TARGET_FPS && fps < TARGET_FPS) {
                OFSET_FPS += 0.5 * (TARGET_FPS - fps);
            } else if (fps != TARGET_FPS && fps > TARGET_FPS) {
                OFSET_FPS -= 0.5 * (fps - TARGET_FPS);
            }
            fps = 0;
            lastFPS += time - lastFPS;
        }
        fps++;
    }

    /**
     * @return a long with the cycle period of time
     */
    public long getCyclePeriod() {
        return (long) (1000 / OFSET_FPS);
    }

    /**
     * you can set the Target fps with this method
     *
     * @param targetFps {@link int} number of the target fps
     */
    public void setTargetFps(int targetFps) {
        this.TARGET_FPS = targetFps;
        this.OFSET_FPS = TARGET_FPS;
    }
}
