package hu.oe.nik.szfmv.common;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SteeringSystem;
import org.junit.Test;

import static org.junit.Assert.*;

public class SteeringSystemTest {
    SteeringSystem steeringSystem;

    @Test
    public void testSteeringSystemNotNull() {
        steeringSystem = new SteeringSystem(new VirtualFunctionBus());

        assertNotNull(steeringSystem);
        assertNotNull(steeringSystem.getAngularSpeed());
        assertNotNull(steeringSystem.getCar());
        assertNotNull(steeringSystem.getTurningCircle());
    }

    @Test
    public void testCalculateTurningCircle() {
        steeringSystem = new SteeringSystem(new VirtualFunctionBus());
        int oldTurningCircle = steeringSystem.getTurningCircle();
        steeringSystem.calculateTurningCircle(30);
        int newTurningCircle = steeringSystem.getTurningCircle();

        assertNotEquals(oldTurningCircle, newTurningCircle);
    }
}