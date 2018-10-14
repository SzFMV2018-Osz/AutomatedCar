package hu.oe.nik.szfmv.common;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.PowertrainSystem;
import hu.oe.nik.szfmv.common.exceptions.NegativeNumberException;
import org.junit.*;
import org.junit.experimental.theories.suppliers.TestedOn;


import java.util.Random;

import static org.junit.Assert.assertEquals;

public class PowertrainSystemTest {

    @Test
    public void calculateActualRpmWithZeroPedalPositionTest() throws NegativeNumberException {
        PowertrainSystem ps = new PowertrainSystem(new VirtualFunctionBus());

        int gaspedalPosition = 0;
        int result = PowertrainSystem.EXPECTED_RPM;

        int valueOfTestedMethod = ps.calculateActualRpm(gaspedalPosition);

        assertEquals(result, valueOfTestedMethod);
    }

    @Test
    public void calculateActualRpmWithPositivePedalPositionTest() throws NegativeNumberException {
        PowertrainSystem ps = new PowertrainSystem(new VirtualFunctionBus());
        Random r = new Random();


        int gaspedalPosition = r.nextInt(20) + 1;
        int result = (int)(gaspedalPosition * ((double) (PowertrainSystem.MAX_RPM - PowertrainSystem.EXPECTED_RPM) / 100))
                + PowertrainSystem.EXPECTED_RPM;

        int valueOfTestedMethod = ps.calculateActualRpm(gaspedalPosition);

        assertEquals(result, valueOfTestedMethod);
    }

    @Test(expected = NegativeNumberException.class)
    public void calculateActualRpmWithExceptionTest() throws NegativeNumberException {
        PowertrainSystem ps = new PowertrainSystem(new VirtualFunctionBus());

        int wrongInput = -1;

        int valueOfTestedMethod = ps.calculateActualRpm(wrongInput);
    }
}
