package hu.oe.nik.szfmv.visualization;

import javax.swing.*;
import java.awt.*;

public class TurnSignal extends JPanel {
    Point position;
    public Color color;
    boolean isRightArrow;

    public void setPosition(Point position) {
        this.position = position;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setOrientation(boolean isRightArrow) {
        this.isRightArrow = isRightArrow;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        if (isRightArrow) {
            int[] x = new int[] { 0, 10, 10, 20, 10,
                    10, 0 };
            int[] y = new int[] { 10, 10, 0, 14, 28,
                    18, 18 };
            g.fillPolygon(x, y, 7);
        } else {
            int[] x = new int[] { 0, 10, 10, 20, 20,
                    10, 10 };
            int[] y = new int[] { 14, 0, 10, 10, 18,
                    18, 28 };
            g.fillPolygon(x, y, 7);
        }
    }

    public Dimension getPreferedSize() {
        return new Dimension(30, 30);
    }
}
