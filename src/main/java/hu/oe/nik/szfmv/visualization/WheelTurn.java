package hu.oe.nik.szfmv.visualization;

public class WheelTurn {
    public static final int TURNING_STEPS = 6;
    public static final int MIN = -100;
    public static final int MIN_THRESHOLD = -94; // Should be MIN + TURNING_STEPS.
    public static final int MAX = 100;
    public static final int MAX_THRESHOLD = 94; // Should be MAX - TURNING_STEPS.
    public static final int BACK_POSITION_STEPS = 25;
    public static final int BACK_POSITION_MIN_THRESHOLD = -25; // Should be 0 - TURNING_STEPS.
    public static final int BACK_POSITION_MAX_THRESHOLD = 25; // Should be 0 + TURNING_STEPS.

    int level;
    boolean isTurning;

    public WheelTurn() {
        level = 0;
        isTurning = false;
    }

    public void TurnRight() {
        isTurning = true;
        if (level >= MAX_THRESHOLD) {
            level = MAX;
        } else if (level < MAX_THRESHOLD) {
            level += TURNING_STEPS;
        }
    }

    public void TurnLeft() {
        isTurning = true;
        if (level <= MIN_THRESHOLD) {
            level = MIN;
        } else if (level > MIN_THRESHOLD) {
            level -= TURNING_STEPS;
        }
    }

    public void BackPosition() {
        if (isTurning) return;

        if (level < -20) {
            level += BACK_POSITION_STEPS;
        } else if (level > 20) {
            level -= BACK_POSITION_STEPS;
        }else  if(level >= -20 && level <= 0){
            level = 0;
        }
        if (level < BACK_POSITION_MAX_THRESHOLD && level > BACK_POSITION_MIN_THRESHOLD) {
            level = 0;
        }
    }

    public void setIsTurning(boolean value) {
        this.isTurning = value;
    }

    public boolean getIsTurning() {
        return this.isTurning;
    }
}
