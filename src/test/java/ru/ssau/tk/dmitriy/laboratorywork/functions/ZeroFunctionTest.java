package ru.ssau.tk.dmitriy.laboratorywork.functions;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ZeroFunctionTest {
    @Test
    public void testOfApplyZeroFunction() {
        double value = 10;
        ZeroFunction object = new ZeroFunction(4);
        Assert.assertEquals(object.apply(value), 0, 0.00001);
        Assert.assertEquals(object.apply(Double.POSITIVE_INFINITY), 0.0, 0.00001);
        Assert.assertEquals(object.apply(Double.NEGATIVE_INFINITY), 0.0, 0.00001);
        Assert.assertEquals(object.apply(Double.NaN), 0.0, 0.00001);
    }
}