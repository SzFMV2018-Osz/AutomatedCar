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
    private int newValue;

    private int steeringWheelValue;

    public int getSteeringWheelValue() {
        return steeringWheelValue;
    }

    Gui parent;
    Measurer tachometer;
    Measurer speedometer;
    JLabel steeringWheel;
    JLabel debugLabel;

    Pedal gasPedal;
    Pedal breakPedal;

    WheelTurn wheelTurning;

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
        steeringWheelValue = 0;
        gasPedal = new Pedal();
        breakPedal = new Pedal();
        wheelTurning = new WheelTurn();

        tachometer = new Measurer(this);
        //speedometer = new Measurer(this);
        steeringWheel = new JLabel();
        debugLabel = new JLabel();

        tachometer.setBounds(2, 0, 130, 130);
        CreateTachometer();

        //speedometer.setBounds(130, 15, 115, 115);
        //CreateSpeedometer();

        steeringWheel = addLabel(5, 500, "streering wheel: " + steeringWheelValue, 20);
        // steeringWheel.setText("streering wheel: " + steeringWheelValue);
        // steeringWheel.setBounds(5, 500, 250, 20);

        debugLabel = addLabel(5, 480, "debug:", 0);

        add(tachometer);

        Timer.start();
    }

    private void CreateSpeedometer() {
        speedometer.setDiameter(110);
        speedometer.setMaxValue(91);
        speedometer.setViewValue(10);
    }

    private void CreateTachometer() {
        tachometer.setDiameter(125);
        tachometer.setMaxValue(10001);
        tachometer.setViewValue(2000);
        tachometer.setPosition(new Point(-30, -30));
        tachometer.setSize(new Point(200, 200));
    }

    private void setWheel(int value) {
        steeringWheel.setText("streering wheel: " + value);
    }

    private JLabel addLabel(int offsetX, int offsetY, String defaultText, int plusSize) {
        JLabel label = new JLabel(defaultText);
        Insets insets = getInsets();

        Dimension labelSize = label.getPreferredSize();
        label.setBounds(insets.left + offsetX, insets.top + offsetY, labelSize.width + plusSize, labelSize.height);

        add(label);

        return label;
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

                setWheel(wheelTurning.level);
                // steeringWheelValue = wheelTurning.level;
                // steeringWheel.setText("streering wheel: " + steeringWheelValue);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }
                // speedometer.repaint();
                tachometer.repaint();
            }
        }
    };

}
