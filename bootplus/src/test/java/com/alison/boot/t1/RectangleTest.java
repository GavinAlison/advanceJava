package com.alison.boot.t1;

import org.junit.Assert;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

public class RectangleTest {

    @Test
    public void testGetArea() {
        double expectArea = 100.0D;
        Rectangle rectangle = PowerMockito.mock(Rectangle.class);
        PowerMockito.when(rectangle.getArea()).thenReturn(expectArea);
        double actualArea = rectangle.getArea();
        Assert.assertEquals("not equals", expectArea, actualArea, 1E-6D);
        ReflectionTestUtils.setField(rectangle, "width", 10.0D);
        System.out.println(rectangle.getWidth());
    }
}