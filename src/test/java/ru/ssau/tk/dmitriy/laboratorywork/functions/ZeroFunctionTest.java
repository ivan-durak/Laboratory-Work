package ru.ssau.tk.dmitriy.laboratorywork.functions;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ZeroFunctionTest {
    @Test
    public void testOfApplyZeroFunction() {
        double value = 4;
        ZeroFunction object = new ZeroFunction(0);
        Assert.assertEquals(object.apply(value), 0,0.000001);
    }
}