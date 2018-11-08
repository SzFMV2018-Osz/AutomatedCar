package hu.oe.nik.szfmv.common;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.PowertrainSystem;
import hu.oe.nik.szfmv.common.exceptions.NegativeNumberException;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class PowertrainSystemTest {

    @Test
    public void calculateActualRpmWithZeroPedalPositionTest() throws NegativeNumberException {
        var virtualFunctionBus = new VirtualFunctionBus();
        PowertrainSystem ps = new PowertrainSystem(virtualFunctionBus);

        int gaspedalPosition = 0;
        int result = PowertrainSystem.MIN_RPM;

        int valueOfTestedMethod = ps.calculateRpm(gaspedalPosition);

        assertEquals(result, valueOfTestedMethod);
    }

    @Test
    public void calculateActualRpmWithPositivePedalPositionTest() throws NegativeNumberException {
        var virtualFunctionBus = new VirtualFunctionBus();
        PowertrainSystem ps = new PowertrainSystem(virtualFunctionBus);
        Random r = new Random();


        int gaspedalPosition = r.nextInt(20) + 1;
        int result = (int) (gaspedalPosition * ((double) (PowertrainSystem.MAX_RPM - PowertrainSystem.MIN_RPM) / 100))
                + PowertrainSystem.MIN_RPM;

        int valueOfTestedMethod = ps.calculateRpm(gaspedalPosition);

        assertEquals(result, valueOfTestedMethod);
    }

    @Test(expected = NegativeNumberException.class)
    public void calculateActualRpmWithExceptionTest() throws NegativeNumberException {
        var virtualFunctionBus = new VirtualFunctionBus();
        PowertrainSystem ps = new PowertrainSystem(virtualFunctionBus);

        int wrongInput = -1;

        int valueOfTestedMethod = ps.calculateRpm(wrongInput);
    }
}