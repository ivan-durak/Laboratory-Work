package ru.ssau.tk.dmitriy.laboratorywork.functions;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ReverseFunctionTest {
    @Test
    public void testOfApplyCubeFunction() {
        double value = 28;
        ReverseFunction object = new ReverseFunction();
        Assert.assertEquals(object.apply(value), 1/value);
    }
}