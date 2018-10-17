package hu.oe.nik.szfmv.visualization;

public class Pedal {

    int level;

    public Pedal() {
        level = 0;
    }

    public void Pressed() {

        if (level >= 94) {
            level = 100;
        } else if (level < 94) {
            level += 6;
        }
    }

    public void Decrease() {
        level -= 3;
        if (level < 3) {
            level = 0;
        }
    }
}
