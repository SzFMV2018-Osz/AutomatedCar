package hu.oe.nik.szfmv.visualization;

import javax.swing.*;
import java.awt.*;

public class TurnSignal extends JPanel {
    Point position;
    Color color;
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
        g.setColor(color);
        if (isRightArrow) {
            int[] x = new int[] { position.x, position.x + 10, position.x + 10, position.x + 20, position.x + 10,
                    position.x + 10, position.x };
            int[] y = new int[] { position.y, position.y, position.y - 10, position.y + 4, position.y + 18,
                    position.y + 8, position.y + 8 };
            g.fillPolygon(x, y, 7);
            setBounds(0, 200, 20, 20); // ez itt azért van hogy a panel csak az index méretében jöjjön létre, mert
                                       // amikor a panelt repaintelem akkor az egész képernyőt elfedte
        } else {
            int[] x = new int[] { position.x, position.x + 10, position.x + 10, position.x + 20, position.x + 20,
                    position.x + 10, position.x + 10 };
            int[] y = new int[] { position.y + 4, position.y - 10, position.y, position.y, position.y + 8,
                    position.y + 8, position.y + 18 };
            g.fillPolygon(x, y, 7);
            setBounds(220, 200, 20, 20);
        }

    }

    public Dimension getPreferedSize() {
        return new Dimension(500, 500);
    }
}
