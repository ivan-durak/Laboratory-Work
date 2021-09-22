package ru.ssau.tk.dmitriy.laboratorywork.functions;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class SquareFunctionTest {
    @Test
    public void testOfApplySquareFunction() {
        double value = 72;
        SquareFunction object = new SquareFunction();
        Assert.assertEquals(object.apply(value), 5184.0);
        Assert.assertEquals(object.apply(Double.POSITIVE_INFINITY),Double.POSITIVE_INFINITY,0.00001);
        Assert.assertEquals(object.apply(Double.NEGATIVE_INFINITY),Double.POSITIVE_INFINITY,0.00001);
        Assert.assertEquals(object.apply(Double.NaN),Double.NaN,0.00001);
        Assert.assertEquals(object.apply(0),0,0.00001);
    }
}