package hu.oe.nik.szfmv.common;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SteeringSystem;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class DynamicMovingTest {

    private DynamicMoving moving;

    @Test
    public void MovingNotNull() {
        moving = new DynamicMoving(new SteeringSystem(new VirtualFunctionBus()));

        assertNotNull(moving);
        assertNotNull(moving.getVector());
        assertNotNull(moving.getSteeringSystem());
    }

    @Test
    public void testNewVectorAccelerationChange() {
        moving = new DynamicMoving(new SteeringSystem(new VirtualFunctionBus()));

        int oldSpeed = moving.getSpeed();
        moving.calculateNewVector(2);

        assertEquals(moving.getSpeed(), oldSpeed + 2, 0.001);
    }

    @Test
    public void testNewVectorCoordinates() {
        moving = new DynamicMoving(new SteeringSystem(new VirtualFunctionBus()));

        Point oldPoint = moving.getVector();
        moving.calculateNewVector(100);

        assertNotEquals(oldPoint, moving.getVector());
    }
}