package hu.oe.nik.szfmv.common;

import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CarTest {

    private ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource("car_1_red.png"));

    @Test
    public void testAutoPropertiesAreNotNull() {
        Car car = new Car("car_1_red.png");

        assertNotNull(car.getWheelbase());
        assertNotNull(car.getWidth());
    }

    @Test
    public void testAutoPropertiesEqualsIconSize() {
        Car car = new Car("car_1_red.png");

        assertEquals(car.getWidth(), icon.getIconWidth());
        assertEquals(car.getWheelbase(), icon.getIconHeight());
    }
}