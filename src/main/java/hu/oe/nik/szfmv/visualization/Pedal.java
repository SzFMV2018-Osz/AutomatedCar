package hu.oe.nik.szfmv.visualization;

public class Pedal {

    int level;

    public Pedal() {
        level = 0;
    }

    public void Pressed() {

        if (level >= 95) {
            level = 100;
        } else if (level < 95) {
            level += 10;
        }
    }

    public void Decrease() {
        level -= 9;
    }
}
