package hu.oe.nik.szfmv.visualization;

public class WheelTurn {
    public static final int TURNING_STEPS = 6;
    public static final int MIN = -100;
    public static final int MAX = 100;
    public static final int BACK_POSITION_STEPS = 25;

    int level;
    boolean isTurning;

    public WheelTurn() {
        level = 0;
        isTurning = false;
    }

    public void TurnRight() {
        isTurning = true;

        level += TURNING_STEPS;
        if (level > MAX) {
            level = MAX;
        }
    }

    public void TurnLeft() {
        isTurning = true;

        level -= TURNING_STEPS;
        if (level < MIN) {
            level = MIN;
        }
    }

    public void BackPosition() {
        if (isTurning) return;

        if (level < -BACK_POSITION_STEPS) {
            level += BACK_POSITION_STEPS;
        } else if (level > BACK_POSITION_STEPS) {
            level -= BACK_POSITION_STEPS;
        } else {
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
