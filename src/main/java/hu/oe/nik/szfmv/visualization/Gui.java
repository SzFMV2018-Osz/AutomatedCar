package hu.oe.nik.szfmv.visualization;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Gui extends JFrame {

    private final int windowWidth = 1020;
    private final int windowHeight = 700;
    private ArrayList<Integer> keysPressed;
    private CourseDisplay courseDisplay;
    private Dashboard dashboard;
    private VirtualFunctionBus virtualFunctionBus;


    /**
     * Initialize the GUI class
     */
    public Gui() {
        setTitle("AutomatedCar");
        setLocation(0, 0); // default is 0,0 (top left corner)
        addWindowListener(new GuiAdapter());
        setPreferredSize(new Dimension(windowWidth, windowHeight)); // inner size
        setResizable(false);
        pack();

        // Icon downloaded from:
        // http://www.iconarchive.com/show/toolbar-2-icons-by-shlyapnikova/car-icon.html
        // and available under the licence of:
        // https://creativecommons.org/licenses/by/4.0/
        ImageIcon carIcon = new ImageIcon(ClassLoader.getSystemResource("car-icon.png"));
        setIconImage(carIcon.getImage());

        // Not using any layout manager, but fixed coordinates
        setLayout(null);

        courseDisplay = new CourseDisplay(this);
        add(courseDisplay);

        dashboard = new Dashboard(this);
        add(dashboard);

        setVisible(true);

        keysPressed = new ArrayList<>();

        KeyListener listen = new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {


            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();

                // Release turning and pedal pressing so the back positioning can run.
                if (keyCode == KeyEvent.VK_RIGHT) {
                    dashboard.wheelTurning.setIsTurning(false);
                }
                if (keyCode == KeyEvent.VK_LEFT) {
                    dashboard.wheelTurning.setIsTurning(false);
                }
                if (keyCode == KeyEvent.VK_UP) {
                    dashboard.gasPedal.setIsPressed(false);
                }
                if (keyCode == KeyEvent.VK_DOWN) {
                    dashboard.breakPedal.setIsPressed(false);
                }

                if (keysPressed.contains(keyCode)) {
                    keysPressed.remove(keysPressed.indexOf(keyCode));
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

                if (!keysPressed.contains(e.getKeyCode())) {
                    keysPressed.add(e.getKeyCode());
                }

            }
        };

        this.addKeyListener(listen);

    }

    /**
     * Update input
     */
    public void inputUpdate() {
        moveCar();

        if (keysPressed.contains(KeyEvent.VK_Q)) {
            dashboard.index.TurnLeft();
        } else if (keysPressed.contains(KeyEvent.VK_E)) {
            dashboard.index.TurnRight();
        } else if (keysPressed.contains(KeyEvent.VK_W)) {
            dashboard.index.Warning();
        } else if (keysPressed.contains(KeyEvent.VK_S)) {
            dashboard.index.SwitchBack();
        }

        oncePressedKeys();

    }

    public VirtualFunctionBus getVirtualFunctionBus() {
        return virtualFunctionBus;
    }

    public void setVirtualFunctionBus(VirtualFunctionBus virtualFunctionBus) {
        this.virtualFunctionBus = virtualFunctionBus;
    }

    public CourseDisplay getCourseDisplay() {
        return courseDisplay;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    private void oncePressedKeys() {
        if (keysPressed.contains(KeyEvent.VK_PAGE_UP)) {
            dashboard.autoTr.ShiftUp();
            keysPressed.remove(keysPressed.indexOf(KeyEvent.VK_PAGE_UP));
        } else if (keysPressed.contains(KeyEvent.VK_PAGE_DOWN)) {
            dashboard.autoTr.ShiftDown();
            keysPressed.remove(keysPressed.indexOf(KeyEvent.VK_PAGE_DOWN));
        }

        if (keysPressed.contains(KeyEvent.VK_R)) {
            courseDisplay.setShowRadarSensor(!courseDisplay.getShowRadarSensor());
            keysPressed.remove(keysPressed.indexOf(KeyEvent.VK_R));
        }

        if (keysPressed.contains(KeyEvent.VK_C)) {
            courseDisplay.setshowCameraSensor(!courseDisplay.getShowCameraSensor());
            keysPressed.remove(keysPressed.indexOf(KeyEvent.VK_C));
        }

        if (keysPressed.contains(KeyEvent.VK_T)) {
            getVirtualFunctionBus().powertrainPacket.setSpeedLimit(getVirtualFunctionBus().powertrainPacket.getSpeed());
            keysPressed.remove(keysPressed.indexOf(KeyEvent.VK_T));
        }
    }

    private void moveCar() {
        if (keysPressed.contains(KeyEvent.VK_UP)) {
            dashboard.gasPedal.Pressed();
        }
        if (keysPressed.contains(KeyEvent.VK_DOWN)) {
            dashboard.breakPedal.Pressed();
            getVirtualFunctionBus().powertrainPacket.unlockSpeedLimit();
        }
        if (keysPressed.contains(KeyEvent.VK_RIGHT)) {
            dashboard.wheelTurning.TurnRight();
        } else if (keysPressed.contains(KeyEvent.VK_LEFT)) {
            dashboard.wheelTurning.TurnLeft();
        }
    }
}
