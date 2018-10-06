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

    private JProgressBar breakProgressBar;

    /**
     * Initialize the dashboard
     */
    public Dashboard() {
        // Not using any layout manager, but fixed coordinates
        setLayout(null);
        setBackground(new Color(backgroundColor));
        setBounds(770, 0, width, height);

        breakProgressBar = addProgressBar(10, 10, "Break pedal");

    }

    /**
     * Sets the break progress bar value.
     *
     * @param value the value to set
     */
    public void setBreakProgress(int value) {
        if (value >= MIN_BREAK_VALUE && value <= MAX_BREAK_VALUE) {
            breakProgressBar.setValue(value);
        }
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

}
