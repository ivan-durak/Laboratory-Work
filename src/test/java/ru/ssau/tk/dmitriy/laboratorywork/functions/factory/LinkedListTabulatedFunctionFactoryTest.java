package ru.ssau.tk.dmitriy.laboratorywork.functions.factory;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.ssau.tk.dmitriy.laboratorywork.functions.LinkedListTabulatedFunction;

import static org.testng.Assert.*;

public class LinkedListTabulatedFunctionFactoryTest {

    @Test
    public void testCreate() {
        double[] arrayOfX = {1, 5, 7}, arrayOfY = {3, 8, 10};
        TabulatedFunctionFactory functionFactory = new LinkedListTabulatedFunctionFactory();
        Assert.assertTrue(functionFactory.create(arrayOfX, arrayOfY) instanceof LinkedListTabulatedFunction);
    }
}