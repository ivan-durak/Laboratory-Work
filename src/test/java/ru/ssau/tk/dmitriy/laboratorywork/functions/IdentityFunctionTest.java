package ru.ssau.tk.dmitriy.laboratorywork.functions;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class IdentityFunctionTest {
    @Test
    public void testOfApplyIdentityFunction() {
        double value = 45;
        IdentityFunction object = new IdentityFunction();
        Assert.assertEquals(object.apply(value), value);
        Assert.assertEquals(object.apply(Double.POSITIVE_INFINITY), Double.POSITIVE_INFINITY, 0.00001);
        Assert.assertEquals(object.apply(Double.NEGATIVE_INFINITY), Double.NEGATIVE_INFINITY, 0.00001);
        Assert.assertEquals(object.apply(Double.NaN), Double.NaN, 0.00001);
    }
}