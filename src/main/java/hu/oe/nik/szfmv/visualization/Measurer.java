package hu.oe.nik.szfmv.visualization;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

public class Measurer extends JPanel {

    int power;
    int maxValue;
    int viewValue;
    int diameter;
    Point position;
    Point size;

    public void setPower(int power) {
        this.power = power - 69;
    }

    public void setSize(Point size) {
        this.size = size;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setViewValue(int viewValue) {
        this.viewValue = viewValue;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    @Override
    protected void paintComponent(Graphics g) {

        g.setColor(new Color(0x888888));
        g.fillOval(position.x, position.y, size.x, size.y);
        g.setColor(Color.BLACK);
        g.fillOval(3, 3, diameter, diameter);
        g.setColor(Color.WHITE);
        g.fillOval(6, 6, diameter - 6, diameter - 6);
        g.setColor(Color.RED);
        g.fillOval(diameter / 2 + 3, diameter / 2 + 3, 5, 5);
        g.setColor(Color.BLACK);
        g.setFont(g.getFont().deriveFont(Font.BOLD, 10));

        for (int i = 110; i <= 120 + maxValue; i++) {
            int x = 52 + (int) (43 * Math.sin(i * Math.PI / 90));
            int y = 68 - (int) (45 * Math.cos(i * Math.PI / 90));
            if ((i - 110) % viewValue == 0) {
                g.drawString(Integer.toString(i - 110), x, y);
            }
        }

        int tx, ty;
        tx = 58 + (int) (48 * Math.sin(power * Math.PI / 90));
        ty = 65 - (int) (48 * Math.cos(power * Math.PI / 90));
        g.setColor(Color.RED);
        g.drawLine(diameter / 2 + 5, diameter / 2 + 5, tx, ty);
    }
}
