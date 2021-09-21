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
        ReverseFunction fourthFunction = new ReverseFunction();
        CompositeFunction complexFunction = new CompositeFunction(firstFunction, secondFunction);
        CompositeFunction complexX2Function = new CompositeFunction(complexFunction, secondFunction);
        CompositeFunction complexX3Function = new CompositeFunction(complexX2Function, thirdFunction);
        CompositeFunction complexX4Function = new CompositeFunction(complexX2Function, complexX3Function);
        double value = 12;
        double result = complexFunction.apply(12);
        double resultX2 = complexX2Function.apply(12);
        double resultX3 = complexX3Function.apply(12);
        double resultX4 = complexX4Function.apply(12);
        Assert.assertEquals(complexFunction.apply(value), result);
        Assert.assertEquals(complexX2Function.apply(value), resultX2);
        Assert.assertEquals(complexX3Function.apply(value), resultX3);
        Assert.assertEquals(complexX4Function.apply(value), resultX4);
    }
}