package ru.ssau.tk.dmitriy.laboratorywork.functions;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class IdentityFunctionTest {
    @Test
    public void testOfApply() {
        double value = 45;
        IdentityFunction object = new IdentityFunction();
        Assert.assertEquals(object.apply(value), value);
    }
}