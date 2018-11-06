package hu.oe.nik.szfmv.common;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class ResistencesTest {

    private Point testFDrag;
    private Point testFRolling;
    private Point testVelocityVektor;

    @Test
    public void testCalculateAirResistance() {
        testFDrag = new Point();
        testVelocityVektor = new Point();

        testVelocityVektor.x = 0;
        testVelocityVektor.y = 0;
        testFDrag.x = 0;
        testFDrag.y = 0;
        assertEquals(testFDrag, Resistences.calculateAirResistance(testVelocityVektor));

        testVelocityVektor.x = 12;
        testVelocityVektor.y = 5;
        testFDrag.x = -66;
        testFDrag.y = -27;
        assertEquals(testFDrag, Resistences.calculateAirResistance(testVelocityVektor));
    }

    @Test
    public void testCalulateRollingResistance() {
        testFRolling = new Point();
        testVelocityVektor = new Point();

        testVelocityVektor.x = 0;
        testVelocityVektor.y = 0;
        testFRolling.x = 0;
        testFRolling.y = 0;
        assertEquals(testFRolling, Resistences.calulateRollingResistance(testVelocityVektor));

        testVelocityVektor.x = 4;
        testVelocityVektor.y = 3;
        testFRolling.x = -51;
        testFRolling.y = -38;
        assertEquals(testFRolling, Resistences.calulateRollingResistance(testVelocityVektor));
    }
}