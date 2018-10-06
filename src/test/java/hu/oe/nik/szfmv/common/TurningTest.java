package hu.oe.nik.szfmv.common;

import org.junit.Test;

import static org.junit.Assert.*;

public class TurningTest {

    Turning turning;

    @Test
    public void testTurningNotNull(){
        turning = new Turning();

        assertNotNull(turning);
        assertNotNull(turning.getCar());
        assertNotNull(turning.getTurningCircle());
    }

    @Test
    public void testCalculateTurningCircle(){
        turning = new Turning();
        int oldTurningCircle = turning.getTurningCircle();
        turning.CalculateTurningCircle(30);
        int newTurningCircle = turning.getTurningCircle();

        assertNotEquals(oldTurningCircle,newTurningCircle);
    }
}