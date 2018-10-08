package hu.oe.nik.szfmv.visualization;

import javax.swing.*;
import java.awt.*;

/**
 * Dashboard shows the state of the ego car, thus helps in debugging.
 */
public class Dashboard extends JPanel {

    private final int width = 250;
    private final int height = 700;
    private final int backgroundColor = 0x888888;

    public int power;
    int newValue;

    Gui parent;
    Measurer tachometer;
    Measurer speedometer;

    Pedal gasPedal;
    Pedal breakPedal;

    /**
     * Initialize the dashboard
     */
    public Dashboard(Gui pt) {
        // Not using any layout manager, but fixed coordinates
        setLayout(null);
        setBackground(new Color(backgroundColor));
        setBounds(770, 0, width, height);

        parent = pt;
        power = 0;
        newValue = 0;
        gasPedal = new Pedal();
        breakPedal = new Pedal();

        tachometer = CreateTachometer();
        speedometer = CreateSpeedometer();

        Timer.start();
    }

    private Measurer CreateSpeedometer() {
        Measurer panel = new Measurer(this);
        panel.setDiameter(110);
        panel.setMaxValue(91);
        panel.setViewValue(10);
        panel.setPosition(new Point(-30, -30));
        panel.setSize(new Point(200, 200));

        panel.setBounds(130, 15, 115, 115);
        panel.setVisible(true);

        add(panel);

        return panel;
    }

    private Measurer CreateTachometer() {
        Measurer panel = new Measurer(this);
        panel.setDiameter(125);
        panel.setMaxValue(10001);
        panel.setViewValue(2000);
        panel.setPosition(new Point(-30, -30));
        panel.setSize(new Point(200, 200));

        panel.setBounds(2, 0, 130, 130);
        panel.setVisible(true);

        add(panel);

        return panel;
    }

    private void setSpeed() {
        speedometer.repaint();
    }

    private void setPower() {
        tachometer.repaint();
    }

    Thread Timer = new Thread() {
        int difference;

        public void run() {
            while (true) {
                difference = gasPedal.level / 10 - breakPedal.level / 10;

                if (newValue + difference < 100 && newValue + difference > 0) {
                    newValue += difference;
                }

                power = newValue - 69;

                if (newValue > 0) {
                    newValue -= 4;
                }

                if (gasPedal.level > 0) {
                    gasPedal.Decrease();
                }

                if (breakPedal.level > 0) {
                    breakPedal.Decrease();
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }

                setSpeed();
                setPower();
            }
        }
    };

}
