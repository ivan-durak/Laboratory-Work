package ru.ssau.tk.dmitriy.laboratorywork.functions;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CompositeFunctionTest {
    @Test
    public void testOfApplyCompositeFunction() {
        IdentityFunction firstFunction = new IdentityFunction();
        SquareFunction secondFunction = new SquareFunction();
        CubeFunction thirdFunction = new CubeFunction();
        CompositeFunction complexFunction = new CompositeFunction(firstFunction, secondFunction);
        CompositeFunction complexX2Function = new CompositeFunction(complexFunction, secondFunction);
        CompositeFunction complexX3Function = new CompositeFunction(complexX2Function, thirdFunction);
        CompositeFunction complexX4Function = new CompositeFunction(complexX2Function, complexX3Function);
        double value = 5;
        Assert.assertEquals(complexFunction.apply(value), 25.0, 0.00001);
        Assert.assertEquals(complexX2Function.apply(value), 625.0, 0.00001);
        Assert.assertEquals(complexX3Function.apply(value), 244140625.0, 0.00001);
        Assert.assertEquals(complexX4Function.apply(value), 3552713678800500929355621337890625.0, 0.00001);
        Assert.assertEquals(complexFunction.apply(Double.POSITIVE_INFINITY), Double.POSITIVE_INFINITY, 0.00001);
        Assert.assertEquals(complexFunction.apply(Double.NEGATIVE_INFINITY), Double.POSITIVE_INFINITY, 0.00001);
        Assert.assertEquals(complexFunction.apply(Double.NaN), Double.NaN, 0.00001);
    }

    @Test
    public void testCompositeFunctionIncludingTabulatedFunctions() {
        SquareFunction squareFunction = new SquareFunction();
        double[] arrayXValues = {1.5, 2.1, 2.8, 3.4, 3.9, 4.6, 5.4, 6.1, 7.0},
                arrayYValues = {3.4, 5.6, 7.3, 8.9, 11.2, 12.9, 14.1, 15.6, 16.7};
        ArrayTabulateFunction arrayFunction = new ArrayTabulateFunction(arrayXValues, arrayYValues);
        double[] linkXValues = {2.6, 3.5, 4.7, 5.5, 6.9, 7.1},
                linkYValues = {12.5, 15.0, 17.8, 18.9, 21.2, 24.3};
        LinkedListTabulatedFunction linkFunction = new LinkedListTabulatedFunction(linkXValues, linkYValues);
        CompositeFunction compositeFunction = linkFunction.andThen(arrayFunction).andThen(squareFunction);
        Assert.assertEquals(compositeFunction.apply(5), 924.41335, 0.0001);
        Assert.assertEquals(compositeFunction.apply(2), 457.32612, 0.0001);
        Assert.assertEquals(compositeFunction.apply(0.1), 208.32111, 0.0001);
    }
}