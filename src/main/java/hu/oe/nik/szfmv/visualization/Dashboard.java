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
	Measurer tachometer;
	Gui parent;
	public int power;
    Pedal gasPedal;

	/**
	 * Initialize the dashboard
	 */
	public Dashboard(Gui pt) {
		// Not using any layout manager, but fixed coordinates
		setLayout(null);
		setBackground(new Color(backgroundColor));
		setBounds(770, 0, width, height);
		tachometer = new Measurer(this);
		CreateTachometer();
		parent = pt;
		add(tachometer);
		power = 0;
		Timer.start();
        gasPedal=new Pedal();

	}

	private void CreateTachometer() {
		tachometer.setDiameter(125);
		tachometer.setMaxValue(10001);
		tachometer.setViewValue(2000);
		tachometer.setPosition(new Point(-30, -30));
		tachometer.setSize(new Point(200, 200));
	}

	Thread Timer = new Thread() {
		int newPower;

		public void run() {
			while (true) {
				newPower = parent.newValue - 69;
				power = newPower;
				if (parent.newValue > 0) {
					parent.newValue--;

				}

                if (gasPedal.level>0)
                    gasPedal.Decrease();

                try {
					Thread.sleep(100);
				} catch (InterruptedException ex) {
				}

				tachometer.repaint();
			}
		}
	};

}
