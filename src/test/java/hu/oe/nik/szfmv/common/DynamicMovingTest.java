package hu.oe.nik.szfmv.common;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class DynamicMovingTest {

    DynamicMoving moving;

    @Test
    public void MovingNotNull(){
        moving = new DynamicMoving();

        assertNotNull(moving);
        assertNotNull(moving.getAcceleration());
        assertNotNull(moving.getAngle());
        assertNotNull(moving.getSpeed());
        assertNotNull(moving.getVector());
    }

    @Test
    public void testNewVectorAccelerationChange(){
        moving = new DynamicMoving();

        int oldSpeed = moving.getSpeed();
        moving.CalculateNewVector(2);

        assertEquals(moving.getSpeed(), oldSpeed + 2 );
    }

    @Test
    public void testNewVectorCoordinates(){
        moving = new DynamicMoving();

        Point oldPoint = moving.getVector();
        moving.CalculateNewVector(100);

        assertNotEquals( oldPoint , moving.getVector());
    }
}