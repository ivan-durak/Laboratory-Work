package ru.ssau.tk.dmitriy.laboratorywork.functions.factory;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.ssau.tk.dmitriy.laboratorywork.functions.ArrayTabulateFunction;
import ru.ssau.tk.dmitriy.laboratorywork.functions.TabulatedFunction;

import static org.testng.Assert.*;

public class ArrayTabulatedFunctionFactoryTest {

    @Test
    public void testCreate() {
        double[] arrayOfX = {2, 3, 5}, arrayOfY = {4, 6, 9};
        TabulatedFunctionFactory functionFactory = new ArrayTabulatedFunctionFactory();
        Assert.assertTrue(functionFactory.create(arrayOfX, arrayOfY) instanceof ArrayTabulateFunction);
    }
}