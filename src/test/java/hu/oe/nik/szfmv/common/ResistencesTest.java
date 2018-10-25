package hu.oe.nik.szfmv.common;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResistencesTest {

    private Vector testFDrag;
    private Vector testFRolling;
    private Vector testVelocityVektor;

    @Test
    public void testCalculateAirResistance() {
        testFDrag = new Vector();
        testVelocityVektor = new Vector();

        testVelocityVektor.setX(0);
        testVelocityVektor.setY(0);
        testFDrag.setX(0);
        testFDrag.setY(0);
        assertEquals(testFDrag, Resistences.calculateAirResistance(testVelocityVektor));

        testVelocityVektor.setX(12);
        testVelocityVektor.setY(5);
        testFDrag.setX(-66);
        testFDrag.setY(-27);
        assertEquals(testFDrag, Resistences.calculateAirResistance(testVelocityVektor));
    }

    @Test
    public void testCalulateRollingResistance() {
        testFRolling = new Vector();
        testVelocityVektor = new Vector();

        testVelocityVektor.setX(0);
        testVelocityVektor.setY(0);
        testFRolling.setX(0);
        testFRolling.setY(0);
        assertEquals(testFRolling, Resistences.calulateRollingResistance(testVelocityVektor));

        testVelocityVektor.setX(4);
        testVelocityVektor.setY(3);
        testFRolling.setX(-51);
        testFRolling.setY(-38);
        assertEquals(testFRolling, Resistences.calulateRollingResistance(testVelocityVektor));
    }
}