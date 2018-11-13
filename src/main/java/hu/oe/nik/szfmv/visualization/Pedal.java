package hu.oe.nik.szfmv.visualization;

public class Pedal {
    public static final int STEPS = 4;
    public static final int MAX = 100;
    public static final int DECREASE_STEPS = 20;

    int level;
    boolean isPressed;

    public Pedal() {
        isPressed = false;
        level = 0;
    }

    public void Pressed() {
        isPressed = true;
        
        level += STEPS;
        if (level > MAX) {
            level = MAX;
        }
    }

    public void Decrease() {
        if (isPressed) return;

        level -= DECREASE_STEPS;
        if (level < 0) {
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
