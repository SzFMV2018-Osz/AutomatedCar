package hu.oe.nik.szfmv.common;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class DynamicMovingTest {

    DynamicMoving moving;

    @Test
    public void MovingNotNull() {
        moving = new DynamicMoving();

        assertNotNull(moving);
        assertNotNull(moving.getAcceleration());
        assertNotNull(moving.getSpeed());
        assertNotNull(moving.getVector());
        assertNotNull(moving.getCar());
        assertNotNull(moving.getTurningCircle());
    }

    @Test
    public void testCalculateTurningCircle() {
        moving = new DynamicMoving();
        int oldTurningCircle = moving.getTurningCircle();
        moving.calculateTurningCircle(30);
        int newTurningCircle = moving.getTurningCircle();

        assertNotEquals(oldTurningCircle, newTurningCircle);
    }

    @Test
    public void testNewVectorAccelerationChange() {
        moving = new DynamicMoving();

        int oldSpeed = moving.getSpeed();
        moving.calculateNewVector(2);

        assertEquals(moving.getSpeed(), oldSpeed + 2, 0.001);
    }

    @Test
    public void testNewVectorCoordinates() {
        moving = new DynamicMoving();

        Point oldPoint = moving.getVector();
        moving.calculateNewVector(100);

        assertNotEquals(oldPoint, moving.getVector());
    }
}