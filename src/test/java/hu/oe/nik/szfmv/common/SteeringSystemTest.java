package hu.oe.nik.szfmv.common;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.carpacket.CarPacket;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SteeringSystem;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.*;

public class SteeringSystemTest {
    SteeringSystem steeringSystem;

    @Test
    public void testSteeringSystemNotNull() {
        steeringSystem = new SteeringSystem(new VirtualFunctionBus());

        assertNotNull(steeringSystem);
        assertEquals(steeringSystem.getTurningCircle(), 0);
    }

    @Test
    public void testCarPropertiesNotNull() {
        AutomatedCar car = new AutomatedCar(50, 50, "car_1_red.png");

        assertNotNull(car);
        assertNotEquals(car.getHeight(), 0);
        assertNotEquals(car.getWidth(), 0);
    }

    @Test
    public void testAutoPropertiesEqualsIconSize() {
        ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource("car_1_red.png"));
        AutomatedCar car = new AutomatedCar(50, 50, "car_1_red.png");

        assertEquals(car.getWidth(), icon.getIconWidth());
        assertEquals(car.getHeight(), icon.getIconHeight());
    }

    @Test
    public void testCalculateTurningCircleIfDegreeIsNull(){
        steeringSystem = new SteeringSystem(new VirtualFunctionBus());
        steeringSystem.calculateTurningCircle(0);

        assertEquals(steeringSystem.getTurningCircle(), 0);
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