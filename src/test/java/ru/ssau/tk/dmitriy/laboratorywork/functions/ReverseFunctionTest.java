package ru.ssau.tk.dmitriy.laboratorywork.functions;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ReverseFunctionTest {
    @Test
    public void testOfApplyReverseFunction() {
        double value = 28;
        ReverseFunction object = new ReverseFunction();
        Assert.assertEquals(object.apply(value), 0.0357142857,0.00001);
        Assert.assertEquals(object.apply(Double.POSITIVE_INFINITY),0.0,0.00001);
        Assert.assertEquals(object.apply(Double.NEGATIVE_INFINITY),0.0,0.00001);
        Assert.assertEquals(object.apply(Double.NaN),Double.NaN,0.00001);
        Assert.assertEquals(object.apply(0),Double.POSITIVE_INFINITY,0.00001);
    }
}