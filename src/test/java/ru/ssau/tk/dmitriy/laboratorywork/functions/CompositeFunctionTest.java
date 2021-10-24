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
        double[] arrayXValues = {3, 4, 5, 6, 7, 8, 9},
                arrayYValues = {-9, -12, -15, -18, -21, -24, -27};
        TabulatedFunction arrayFunction = new ArrayTabulateFunction(arrayXValues, arrayYValues);
        double[] linkXValues = {1, 2, 3, 4, 5, 6, 7},
                linkYValues = {2, 4, 6, 8, 10, 12, 14};
        TabulatedFunction linkFunction = new LinkedListTabulatedFunction(linkXValues, linkYValues);
        CompositeFunction compositeFunction = linkFunction.andThen(arrayFunction).andThen(squareFunction);
        Assert.assertEquals(compositeFunction.apply(3.5), 441.0);
        Assert.assertEquals(compositeFunction.apply(5), 900.0);
        Assert.assertEquals(compositeFunction.apply(0.1), 0.36, 0.00001);
    }
}