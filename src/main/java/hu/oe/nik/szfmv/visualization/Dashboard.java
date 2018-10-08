package hu.oe.nik.szfmv.visualization;

import javax.swing.*;
import java.awt.*;

/**
 * Dashboard shows the state of the ego car, thus helps in debugging.
 */
public class Dashboard extends JPanel {

    public static final int MIN_BREAK_VALUE = 0;
    public static final int MAX_BREAK_VALUE = 100;
    private final int width = 250;
    private final int height = 700;
    private final int backgroundColor = 0x888888;
    Measurer tachometer;
    Gui parent;
    public int power;
    int newValue;
    Pedal gasPedal;
    Pedal breakPedal;
    private JLabel gearLabel;

    private JProgressBar breakProgressBar;

    /**
     * Initialize the dashboard
     */
    public Dashboard(Gui pt) {
        // Not using any layout manager, but fixed coordinates
        setLayout(null);
        setBackground(new Color(backgroundColor));
        setBounds(770, 0, width, height);

        breakProgressBar = addProgressBar(10, 500, "Break pedal");

        tachometer = new Measurer(this);
        CreateTachometer();

        parent = pt;
        add(tachometer);
        power = 0;
        Timer.start();
        newValue = 0;
        gasPedal = new Pedal();
        breakPedal = new Pedal();

        gearLabel = addLabel((width / 2) - 20, 200, "Gear: N");

    }

    public void setBreakProgress(int value) {
        if (value >= MIN_BREAK_VALUE && value <= MAX_BREAK_VALUE) {
            breakProgressBar.setValue(value);
        }
    }

    public void setGear(String gear) {
        String gearLabelValue = "Gear: " + gear;
        gearLabel.setText(gearLabelValue);
    }

    private void CreateTachometer() {
        tachometer.setDiameter(125);
        tachometer.setMaxValue(10001);
        tachometer.setViewValue(2000);
        tachometer.setPosition(new Point(-30, -30));
        tachometer.setSize(new Point(200, 200));
    }

    private JProgressBar addProgressBar(int offsetX, int offsetY, String label) {
        JLabel breakLabel = new JLabel(label);
        Insets insets = getInsets();

        Dimension labelSize = breakLabel.getPreferredSize();
        breakLabel.setBounds(insets.left + offsetX, insets.top + offsetY, labelSize.width, labelSize.height);

        add(breakLabel);

        JProgressBar progressBar = new JProgressBar(MIN_BREAK_VALUE, MAX_BREAK_VALUE);

        Dimension size = progressBar.getPreferredSize();
        progressBar.setBounds(insets.left + offsetX, insets.top + offsetY + labelSize.height, size.width, size.height);

        progressBar.setStringPainted(true);
        progressBar.setVisible(true);
        progressBar.setValue(0);

        add(progressBar);

        return progressBar;
    }

    private JLabel addLabel(int offsetX, int offsetY, String defaultText) {
        JLabel label = new JLabel(defaultText);
        Insets insets = getInsets();

        Dimension labelSize = label.getPreferredSize();
        label.setBounds(insets.left + offsetX, insets.top + offsetY, labelSize.width, labelSize.height);

        add(label);

        return label;
    }

    Thread Timer = new Thread() {
        int difference;

        public void run() {
            while (true) {
                difference = gasPedal.level / 10 - breakPedal.level / 10;
                if (newValue + difference < 100 && newValue + difference > 0)
                    newValue += difference;

                power = newValue - 69;

                if (newValue > 0)
                    newValue -= 4;

                if (gasPedal.level > 0)
                    gasPedal.Decrease();
                if (breakPedal.level > 0)
                    breakPedal.Decrease();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }

                tachometer.repaint();
            }
        }
    };

}
