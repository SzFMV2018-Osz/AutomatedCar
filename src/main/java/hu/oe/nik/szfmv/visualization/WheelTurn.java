package hu.oe.nik.szfmv.visualization;

public class WheelTurn {

    int level;

    public WheelTurn() {
        level = 0;
    }

    public void TurnRight() {
        if (level >= 95) {
            level = 100;
        } else if (level < 95) {
            level += 10;
        }
    }

    public void TurnLeft() {
        if (level <= -95) {
            level = -100;
        } else if (level > -95) {
            level -= 10;
        }
    }

    public void BackPosition() {
        if (level < 0) {
            level += 2;
        } else if (level > 0) {
            level -= 2;
        }
        if (level < 3 && level > -3) {
            level = 0;
        }
    }

}
