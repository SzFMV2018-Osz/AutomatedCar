package hu.oe.nik.szfmv.common;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.carpacket.CarPacket;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SteeringSystem;
import hu.oe.nik.szfmv.model.Classes.Car;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.*;

public class SteeringSystemTest {
    SteeringSystem steeringSystem;

    @Test
    public void testSteeringSystemNotNull() {
        steeringSystem = new SteeringSystem(new VirtualFunctionBus());

        assertNotNull(steeringSystem);
        assertNotEquals(steeringSystem.getTurningCircle(), 0);
    }

    @Test
    public void testCarPropertiesNotNull() {
        Car car = new Car(50, 50, "car_1_red.png");

        assertNotEquals(car.getHeight(), 0);
        assertNotEquals(car.getWidth(), 0);
    }

    @Test
    public void testAutoPropertiesEqualsIconSize() {
        ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource("car_1_red.png"));
        Car car = new Car(50, 50, "car_1_red.png");

        assertEquals(car.getWidth(), icon.getIconWidth());
        assertEquals(car.getHeight(), icon.getIconHeight());
    }

    @Test
    public void testCalculateTurningCircle() {
        VirtualFunctionBus virtualFunctionBus = new VirtualFunctionBus();
        virtualFunctionBus.carPacket = new CarPacket();
        ((CarPacket) virtualFunctionBus.carPacket).setCarHeigth(190);
        ((CarPacket) virtualFunctionBus.carPacket).setCarWidth(90);

        steeringSystem = new SteeringSystem(virtualFunctionBus);
        int oldTurningCircle = steeringSystem.getTurningCircle();
        steeringSystem.calculateTurningCircle(30);
        int newTurningCircle = steeringSystem.getTurningCircle();

        assertNotEquals(oldTurningCircle, newTurningCircle);
    }
}