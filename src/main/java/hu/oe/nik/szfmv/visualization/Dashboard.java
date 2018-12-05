package hu.oe.nik.szfmv.visualization;

import hu.oe.nik.szfmv.automatedcar.bus.packets.sample.SamplePacket;
import hu.oe.nik.szfmv.environment.WorldObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

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

    Gui parent;
    SamplePacket sp;
    Measurer tachometer;
    Measurer speedometer;
    Pedal gasPedal;
    Pedal breakPedal;
    Index index;
    AutoTransmission autoTr;
    WheelTurn wheelTurning;

    private JLabel steeringWheel;
    private JLabel debugLabel;
    private JLabel gearLabel;
    private JLabel carPositionLabel;

    private JProgressBar breakProgressBar;
    private JProgressBar gasProgressBar;

    private TurnSignal leftTurnSignal;
    private TurnSignal rightTurnSignal;
    
    private WorldObject lastSawRoadSign;
    
    private JLabel roadSignImage;
    
    private Thread Timer = new Thread() {

        public void run() {
            while (true) {

                setBreakProgress(breakPedal.level);
                sp.setBreakpedalPosition(breakPedal.level);
                setGasProgress(gasPedal.level);
                sp.setGaspedalPosition(gasPedal.level);
                setWheel(wheelTurning.level);
                sp.setWheelPosition(wheelTurning.level);

                if (gasPedal.level > 0) {
                    gasPedal.Decrease();
                }

                if (breakPedal.level > 0) {
                    breakPedal.Decrease();
                }

                if (wheelTurning.level != 0) {
                    wheelTurning.BackPosition();
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }

                setSpeed();
                setPower();

                setIndex(index.actIndex);
                setCarPosition(parent.getVirtualFunctionBus().carPacket.getxPosition(),
                        parent.getVirtualFunctionBus().carPacket.getyPosition());
                setGear(autoTr.actGear.toString());
                sp.setGear(autoTr.actGear.toString());
                parent.getVirtualFunctionBus().samplePacket = sp;

                try {
                    lastSawRoadSign = parent.getVirtualFunctionBus().sensorPacket.getDetectedRoadSign();
                    BufferedImage image=lastSawRoadSign.getImage();
                    roadSignImage.setIcon(new ImageIcon(image));
                    roadSignImage.setBounds(10,300,image.getHeight(),image.getWidth());


                } catch (NullPointerException e) {
                    // TODO Auto-generated catch block
                }

            }
        }
    };

    /**
     * Initialize the dashboard
     * @throws IOException 
     */
    public Dashboard(Gui pt) {
        // Not using any layout manager, but fixed coordinates
        setLayout(null);
        setBackground(new Color(backgroundColor));
        setBounds(770, 0, width, height);

        parent = pt;
        sp = new SamplePacket();

        gasPedal = new Pedal();
        breakPedal = new Pedal();
        wheelTurning = new WheelTurn();

        breakProgressBar = addProgressBar(10, 400, "Break pedal", MIN_BREAK_VALUE, MAX_BREAK_VALUE);
        gasProgressBar = addProgressBar(10, 430, "Gas pedal", MIN_GAS_VALUE, MAX_GAS_VALUE);

        leftTurnSignal = addTurnSignal(new Point(10, 150), false);
        rightTurnSignal = addTurnSignal(new Point(200, 150), true);

        tachometer = CreateTachometer();
        speedometer = CreateSpeedometer();

        autoTr = new AutoTransmission();
        index = new Index();

        gearLabel = addLabel((width / 2) - 30, 155, "Gear: N", 0);
        debugLabel = addLabel(5, 480, "debug:", 0);
        steeringWheel = addLabel(5, 500, "steering wheel: " + 0, 20);
        carPositionLabel = addLabel(10, 520, "X: 0, Y: 0", 200);

        roadSignImage=CreateRoadSignImage();



        Timer.start();
    }

    private JLabel CreateRoadSignImage() {

        JLabel picLabel = new JLabel();
        add(picLabel);

        return picLabel;
    }

    private Measurer CreateTachometer() {
        Measurer panel = new Measurer();
        panel.setDiameter(125);
        panel.setMaxValue(10001);
        panel.setViewValue(2000);
        panel.setPosition(new Point(-30, -30));
        panel.setSize(new Point(200, 200));
        panel.setPower(0);

        panel.setBounds(2, 0, 130, 130);
        panel.setVisible(true);

        add(panel);

        return panel;
    }

    private Measurer CreateSpeedometer() {
        Measurer panel = new Measurer();
        panel.setDiameter(110);
        panel.setMaxValue(91);
        panel.setViewValue(10);
        panel.setPosition(new Point(-30, -30));
        panel.setSize(new Point(200, 200));
        panel.setPower(0);

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
        Dimension arrow = turnSignal.getPreferedSize();
        turnSignal.setBounds(position.x, position.y, arrow.width, arrow.height);
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

        if (d == Index.Direction.left) {
            setTurnSignal(true, false);
        } else if (d == Index.Direction.right) {
            setTurnSignal(false, true);
        } else if (d == Index.Direction.none) {
            setTurnSignal(false, false);
        } else if (d == Index.Direction.warningsign) {
            setTurnSignal(true, true);
        }

    }

    private void setTurnSignal(boolean left, boolean right) {
        if (left && leftTurnSignal.color != Color.GREEN) {
            leftTurnSignal.setColor(Color.GREEN);
        } else {
            leftTurnSignal.setColor(Color.BLACK);
        }

        if (right && rightTurnSignal.color != Color.GREEN) {
            rightTurnSignal.setColor(Color.GREEN);
        } else {
            rightTurnSignal.setColor(Color.black);
        }
        leftTurnSignal.repaint();
        rightTurnSignal.repaint();
    }

    public void setCarPosition(int x, int y) {
        String position = "X: " + x + ", Y: " + y;
        carPositionLabel.setText(position);
    }

    private void setSpeed() {
        speedometer.setPower((int) parent.getVirtualFunctionBus().powertrainPacket.getSpeed());
        speedometer.repaint();
    }

    private void setPower() {
        tachometer.setPower((int) parent.getVirtualFunctionBus().powertrainPacket.getRpm() / 1000 * 11);
        tachometer.repaint();
    }

    private void setWheel(int value) {
        steeringWheel.setText("steering wheel: " + value);
    }

}
