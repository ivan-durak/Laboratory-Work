package ru.ssau.tk.dmitriy.laboratorywork.functions;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class SquareFunctionTest {
    @Test
    public void testOfApplySquareFunction() {
        double value = 72;
        SquareFunction object = new SquareFunction();
        Assert.assertEquals(object.apply(value), value * value);
    }
}