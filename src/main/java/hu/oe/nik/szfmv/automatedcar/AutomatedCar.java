package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.carpacket.CarPacket;
import hu.oe.nik.szfmv.automatedcar.sensors.UltrasonicSensor;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.PowertrainSystem;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SteeringSystem;
import hu.oe.nik.szfmv.common.DynamicMoving;
import hu.oe.nik.szfmv.model.Classes.Car;

import java.awt.*;
import java.util.ArrayList;

public class AutomatedCar extends Car {
    private final VirtualFunctionBus virtualFunctionBus = new VirtualFunctionBus();
    private PowertrainSystem powertrainSystem;
    private SteeringSystem steeringSystem;
    private DynamicMoving dynamicMoving;
    private ArrayList<UltrasonicSensor> ultrasonicSensors = new ArrayList<UltrasonicSensor>();

    /**
     * Creates an object of the virtual world on the given coordinates with the given image.
     *
     * @param x             the initial x coordinate of the object
     * @param y             the initial y coordinate of the object
     * @param imageFileName the filename of the image representing the object in the virtual world
     */
    public AutomatedCar(int x, int y, String imageFileName) {
        super(x, y, imageFileName);

        steeringSystem = new SteeringSystem(virtualFunctionBus);
        dynamicMoving = new DynamicMoving(steeringSystem);
        powertrainSystem = new PowertrainSystem(virtualFunctionBus, dynamicMoving);
        setCarPacket();
        AddUltrasonicSensors();
        virtualFunctionBus.ultrasonicSensors = ultrasonicSensors;


        new Driver(virtualFunctionBus);


    }

    public VirtualFunctionBus getVirtualFunctionBus() {
        return virtualFunctionBus;
    }

    private void setCarPacket() {
        CarPacket carPacket = new CarPacket();
        carPacket.setCarWidth(width);
        carPacket.setCarHeigth(height);
        virtualFunctionBus.carPacket = carPacket;
    }

    /**
     * Driving the Car
     */
    public void drive() {

        calculatePositionAndOrientation();
        virtualFunctionBus.loop();


    }

    private void calculatePositionAndOrientation() {
        Point movingVector = powertrainSystem.getDynamicMoving().getVector();
        double angularSpeed = steeringSystem.getTurningCircle();

        x -= movingVector.getY();
        y -= movingVector.getX();

        virtualFunctionBus.carPacket.setxPosition(x);
        virtualFunctionBus.carPacket.setyPosition(y);

        rotation += angularSpeed;
        virtualFunctionBus.carPacket.setCarRotation(rotation);

    }

    private void AddUltrasonicSensors() {
        int carWidth = virtualFunctionBus.carPacket.getCarWidth();
        int carHeight = virtualFunctionBus.carPacket.getCarHeigth();
        //front sensors
        ultrasonicSensors.add(new UltrasonicSensor(virtualFunctionBus, 10, carWidth / 2 + 30, 0));
        ultrasonicSensors.add(new UltrasonicSensor(virtualFunctionBus, 10, carWidth / 2 - 30, 0));

        //back sensors
        ultrasonicSensors.add(new UltrasonicSensor(virtualFunctionBus, carHeight - 10, carWidth / 2 + 30, 180));
        ultrasonicSensors.add(new UltrasonicSensor(virtualFunctionBus, carHeight - 10, carWidth / 2 - 30, 180));

        //right sensors
        ultrasonicSensors.add(new UltrasonicSensor(virtualFunctionBus, 30, carWidth - 5, 90));
        ultrasonicSensors.add(new UltrasonicSensor(virtualFunctionBus, carHeight - 30, carWidth - 5, 90));

        //left sensors
        ultrasonicSensors.add(new UltrasonicSensor(virtualFunctionBus, 30, 5, -90));
        ultrasonicSensors.add(new UltrasonicSensor(virtualFunctionBus, carHeight - 30, 5, -90));

    }

}