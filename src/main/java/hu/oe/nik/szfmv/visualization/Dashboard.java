package hu.oe.nik.szfmv.visualization;

import javax.swing.*;
import java.awt.*;

/**
 * Dashboard shows the state of the ego car, thus helps in debugging.
 */
public class Dashboard extends JPanel {

    public static final int MIN_BREAK_VALUE = 0;
    public static final int MAX_BREAK_VALUE = 100;
    public static final int MIN_GAS_VALUE = 0;
    public static final int MAX_GAS_VALUE = 100;
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

    Pedal gasPedal;
    Pedal breakPedal;
    Index index;
    AutoTransmission autoTr;

    private JLabel steeringWheel;
    private JLabel debugLabel;
    private JLabel gearLabel;
    private JLabel indexLabel;
    private JLabel carPositionLabel;

    private JProgressBar breakProgressBar;
    private JProgressBar gasProgressBar;

    WheelTurn wheelTurning;
    private TurnSignal leftTurnSignal;
    private TurnSignal rightTurnSignal;

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

        breakProgressBar = addProgressBar(10, 400, "Break pedal", MIN_BREAK_VALUE, MAX_BREAK_VALUE);
        gasProgressBar = addProgressBar(10, 430, "Gas pedal", MIN_GAS_VALUE, MAX_GAS_VALUE);

        leftTurnSignal = addTurnSignal(new Point(10, 200), false);
        rightTurnSignal = addTurnSignal(new Point(200, 200), true);

        tachometer = CreateTachometer();
        speedometer = CreateSpeedometer();

        autoTr = new AutoTransmission();
        index = new Index();

        gearLabel = addLabel((width / 2) - 20, 200, "Gear: N", 0);
        debugLabel = addLabel(5, 480, "debug:", 0);
        steeringWheel = addLabel(5, 500, "streering wheel: " + steeringWheelValue, 20);
        carPositionLabel = addLabel(10, 520, "X: 0, Y: 0", 0);
        indexLabel = addLabel((width / 2) - 20, 220, "O", 0);

        Timer.start();
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

    private JProgressBar addProgressBar(int offsetX, int offsetY, String label, int minValue, int maxValue) {
        JLabel breakLabel = new JLabel(label);
        Insets insets = getInsets();

        Dimension labelSize = breakLabel.getPreferredSize();
        breakLabel.setBounds(insets.left + offsetX, insets.top + offsetY, labelSize.width, labelSize.height);

        add(breakLabel);

        JProgressBar progressBar = new JProgressBar(minValue, maxValue);

        Dimension size = progressBar.getPreferredSize();
        progressBar.setBounds(insets.left + offsetX, insets.top + offsetY + labelSize.height, size.width, size.height);

        progressBar.setStringPainted(true);
        progressBar.setVisible(true);
        progressBar.setValue(0);

        add(progressBar);

        return progressBar;
    }

    private TurnSignal addTurnSignal(Point position, boolean isRightArrow) {
        TurnSignal turnSignal = new TurnSignal();
        turnSignal.setPosition(position);
        turnSignal.setColor(Color.BLACK);
        turnSignal.setOrientation(isRightArrow);

        // Dimension arrow = turnSignal.getPreferedSize();
        Insets insets = getInsets();
        // turnSignal.setBounds(insets.left, insets.top, 250, 200);

        add(turnSignal);

        return turnSignal;
    }

    private JLabel addLabel(int offsetX, int offsetY, String defaultText, int plusSize) {
        JLabel label = new JLabel(defaultText);
        Insets insets = getInsets();

        Dimension labelSize = label.getPreferredSize();
        label.setBounds(insets.left + offsetX, insets.top + offsetY, labelSize.width + plusSize, labelSize.height);

        add(label);

        return label;
    }

    private void setBreakProgress(int value) {
        if (value >= MIN_BREAK_VALUE && value <= MAX_BREAK_VALUE) {
            breakProgressBar.setValue(value);
        }
    }

    private void setGasProgress(int value) {
        if (value >= MIN_BREAK_VALUE && value <= MAX_BREAK_VALUE) {
            gasProgressBar.setValue(value);
        }
    }

    private void setGear(String gear) {
        String gearLabelValue = "Gear: " + gear;
        gearLabel.setText(gearLabelValue);
    }

    private void setIndex(Index.Direction d) {
        String indexLabelValue = "O";

        if (d == Index.Direction.left) {
            setTurnSignal(true, false);
        } else if (d == Index.Direction.right) {
            setTurnSignal(false, true);
        } else if (d == Index.Direction.none) {
            setTurnSignal(false, false);
        } else if (d == Index.Direction.warningsign) {
            setTurnSignal(true, true);
        }

        indexLabel.setText(indexLabelValue);
    }

    private void setTurnSignal(boolean left, boolean right) {
        if (left) {
            leftTurnSignal.setColor(Color.GREEN);
        } else {
            leftTurnSignal.setColor(Color.BLACK);
        }

        if (right) {
            rightTurnSignal.setColor(Color.GREEN);
        } else {
            rightTurnSignal.setColor(Color.BLACK);
        }
        leftTurnSignal.repaint();
        rightTurnSignal.repaint();
    }

    public void setCarPosition(int x, int y) {
        String position = "X: " + x + ", Y: " + y;
        carPositionLabel.setText(position);
    }

    private void setSpeed() {
        speedometer.repaint();
    }

    private void setPower() {
        tachometer.repaint();
    }

    private void setWheel(int value) {
        steeringWheel.setText("streering wheel: " + value);
    }

    Thread Timer = new Thread() {
        int difference;

        public void run() {
            while (true) {

                difference = gasPedal.level / 10 - breakPedal.level / 10;

                if (newValue + difference < 100 && newValue + difference > 0) {
                    newValue += difference;
                }

                setBreakProgress(breakPedal.level);
                setGasProgress(gasPedal.level);

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

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }

                setSpeed();
                setPower();

                setIndex(index.actIndex);
                setGear(autoTr.actGear.toString());
            }
        }
    };

}
