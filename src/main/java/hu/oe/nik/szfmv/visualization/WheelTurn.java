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
            level += 5;
        }
    }

    public void TurnLeft() {
        if (level <= -95) {
            level = -100;
        } else if (level > -95) {
            level -= 5;
        }
    }

}
