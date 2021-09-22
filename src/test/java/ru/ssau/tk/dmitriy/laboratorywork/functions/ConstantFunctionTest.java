package ru.ssau.tk.dmitriy.laboratorywork.functions;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ConstantFunctionTest {
    @Test
    public void testOfApplyConstantFunction() {

        ConstantFunction object = new ConstantFunction(28);
        Assert.assertEquals(object.apply(26),28,0.0000001);
    }
}