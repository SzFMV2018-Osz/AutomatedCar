package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.sample.SamplePacket;
import hu.oe.nik.szfmv.environment.WorldObject;
import hu.oe.nik.szfmv.model.Interfaces.ICollidable;

import java.awt.*;
import java.awt.geom.AffineTransform;

import java.util.ArrayList;

import java.util.List;

public class AEB extends SystemComponent {
    private static final int WARNING = 1;
    private static final int ERROR = 2;
    private boolean breaking;
    public String msg;
    public int state;
    private int carX;
    private int carY;
    private int carLastX;
    private int carLastY;
    private int carNextX;
    private int carNextY;
    SamplePacket sp;


    public AEB(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
        setNull();
        carX = virtualFunctionBus.carPacket.getxPosition();
        carY = virtualFunctionBus.carPacket.getyPosition();
        carLastX = 0;
        carLastY = 0;
        breaking = false;
    }

    @Override
    public void loop() {
        carX = virtualFunctionBus.carPacket.getxPosition();
        carY = virtualFunctionBus.carPacket.getyPosition();
        setSp();
        callculateNextPosition();
        if (virtualFunctionBus.powertrainPacket.getSpeed() > 70) {
            setDangerState();
        } else {
            setNull();
        }
        if (canColide() && virtualFunctionBus.powertrainPacket.getSpeed() > 0 && virtualFunctionBus.powertrainPacket.getSpeed() < 70 && virtualFunctionBus.samplePacket.getGear() != "R") {
            setWarningState();
            if (sp.getBreakpedalPosition() > 0) {
                breaking = true;
            }
            if (distenceDanger() && virtualFunctionBus.powertrainPacket.getSpeed() > 10) {
                breaking = true;
            }

        }

        if (breaking)
            sp.setBreakpedalPosition(100);

        if (virtualFunctionBus.powertrainPacket.getSpeed() == 0)
            breaking = false;

        virtualFunctionBus.samplePacket = sp;
        carLastX = carX;
        carLastY = carY;
    }

    void setWarningState() {
        state = 1;
        msg = "Collison warning!";
    }

    void setDangerState() {
        state = 2;
        msg = "AEB can't handle the speed!";
    }

    void setNull() {
        state = 0;
        msg = "";
    }

    void callculateNextPosition() {
        carNextX = (carX - carLastX);
        carNextY = (carY - carLastY);
    }

    boolean canColide() {
        boolean col = false;
        AffineTransform carTransform;
        List<WorldObject> objects = new ArrayList<>();
        List<WorldObject> inRangeObjects = new ArrayList<>();
        AutomatedCar c = (AutomatedCar) virtualFunctionBus.worldObjects.stream().filter(x -> (x instanceof AutomatedCar)).toArray()[0];
        carTransform = c.getTransformation();
        Rectangle carbound = new Rectangle(0, -610, c.getWidth(), 600);
        Shape carShape = carTransform.createTransformedShape(carbound);
        virtualFunctionBus.worldObjects.stream().filter(x -> (x instanceof ICollidable)).forEach(x -> objects.add(x));
        inRangeObjects = virtualFunctionBus.radarSensor.detectedObjects(objects);

        for (WorldObject w : inRangeObjects) {
            Rectangle object = new Rectangle(0, 0, w.getWidth(), w.getHeight());
            if (carShape.intersects(w.getTransformation().createTransformedShape(object).getBounds()) && !c.equals(w))
                col = true;
        }

        return col;
    }

    boolean distenceDanger() {
        boolean col = false;
        AffineTransform carTransform;
        Rectangle carbound;
        List<WorldObject> objects = new ArrayList<>();
        List<WorldObject> inRangeObjects = new ArrayList<>();
        AutomatedCar c = (AutomatedCar) virtualFunctionBus.worldObjects.stream().filter(x -> (x instanceof AutomatedCar)).toArray()[0];
        carTransform = c.getTransformation();
        carbound = new Rectangle(0, (int) -(70 * (virtualFunctionBus.powertrainPacket.getSpeed() / 10) + 10), c.getWidth(), (int) (70 * (virtualFunctionBus.powertrainPacket.getSpeed() / 10)));
        Shape carShape = carTransform.createTransformedShape(carbound);
        virtualFunctionBus.worldObjects.stream().filter(x -> (x instanceof ICollidable)).forEach(x -> objects.add(x));
        inRangeObjects = virtualFunctionBus.radarSensor.detectedObjects(objects);

        for (WorldObject w : inRangeObjects) {
            Rectangle object = new Rectangle(0, 0, w.getWidth(), w.getHeight());
            if (carShape.intersects(w.getTransformation().createTransformedShape(object).getBounds()) && !c.equals(w))
                col = true;
        }

        return col;
    }

    void setSp() {
        sp = new SamplePacket();
        sp.setGaspedalPosition(virtualFunctionBus.samplePacket.getGaspedalPosition());
        sp.setBreakpedalPosition(virtualFunctionBus.samplePacket.getBreakpedalPosition());
        sp.setGear(virtualFunctionBus.samplePacket.getGear());
        sp.setWheelPosition(virtualFunctionBus.samplePacket.getWheelPosition());
    }
}
