package hu.oe.nik.szfmv.automatedcar.sensors;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

public class RadarSensorTest {

    @Test
    public void locateSensorTriangle() {
        RadarSensor testRadar = new RadarSensor(new VirtualFunctionBus());

        Assert.assertNotNull(testRadar);
    }
}