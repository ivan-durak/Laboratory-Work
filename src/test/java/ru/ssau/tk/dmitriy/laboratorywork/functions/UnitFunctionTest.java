package ru.ssau.tk.dmitriy.laboratorywork.functions;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UnitFunctionTest {
    @Test
    public void testOfApplyUnitFunction() {
        double value = 5;
        UnitFunction object = new UnitFunction(34);
        Assert.assertEquals(object.apply(value), 1, 0.00001);
        Assert.assertEquals(object.apply(Double.POSITIVE_INFINITY),1.0,0.00001);
        Assert.assertEquals(object.apply(Double.NEGATIVE_INFINITY),1.0,0.00001);
        Assert.assertEquals(object.apply(Double.NaN),1.0,0.00001);
    }
}