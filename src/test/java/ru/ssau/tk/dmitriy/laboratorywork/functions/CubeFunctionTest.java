package ru.ssau.tk.dmitriy.laboratorywork.functions;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CubeFunctionTest {
    @Test
    public void testOfApplyCubeFunction() {
        double value = 28;
        CubeFunction object = new CubeFunction();
        Assert.assertEquals(object.apply(value), value * value *value);
    }
}