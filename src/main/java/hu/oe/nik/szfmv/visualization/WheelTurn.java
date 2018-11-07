package hu.oe.nik.szfmv.visualization;

public class WheelTurn {

    int level;
    boolean isTurning;

    public WheelTurn() {
        level = 0;
        isTurning = false;
    }

    public void TurnRight() {
        isTurning = true;
        if (level >= 95) {
            level = 100;
        } else if (level < 95) {
            level += 8;
        }
    }

    public void TurnLeft() {
        isTurning = true;
        if (level <= -95) {
            level = -100;
        } else if (level > -95) {
            level -= 8;
        }
    }

    public void BackPosition() {
        if (isTurning) return;

        if (level < 0) {
            level += 20;
        } else if (level > 0) {
            level -= 20;
        }
        if (level < 3 && level > -3) {
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
