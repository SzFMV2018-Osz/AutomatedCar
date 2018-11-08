package hu.oe.nik.szfmv.visualization;

public class Pedal {

    int level;
    boolean isPressed;

    public Pedal() {
        isPressed = false;
        level = 0;
    }

    public void Pressed() {
        isPressed = true;

        if (level >= 94) {
            level = 100;
        } else if (level < 94) {
            level += 4;
        }
    }

    public void Decrease() {
        if (isPressed) return;

        level -= 20;
        if (level < 3) {
            level = 0;
        }
    }

    public void setIsPressed(boolean value) {
        this.isPressed = value;
    }

    public boolean getIsPressed() {
        return this.isPressed;
    }
}
