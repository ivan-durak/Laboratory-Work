package ru.ssau.tk.dmitriy.laboratorywork.functions;

import org.testng.Assert;
import org.testng.annotations.Test;


public class MathFunctionTest {
    @Test
    public void testAndThen() {


        ConstantFunction constantFunction = new ConstantFunction(2);
        IdentityFunction identityFunction = new IdentityFunction();
        ReverseFunction reverseFunction = new ReverseFunction();
        CubeFunction cubeFunction = new CubeFunction();
        SquareFunction squareFunction = new SquareFunction();
        double number = 2.5;


        Assert.assertEquals(identityFunction.andThen(reverseFunction).andThen(cubeFunction).apply(number), 0.064, 0.000000001);
        Assert.assertEquals(constantFunction.andThen(cubeFunction).andThen(squareFunction).apply(number), 64, 0.00000001);
        Assert.assertEquals(constantFunction.andThen(cubeFunction).andThen(squareFunction).apply(Double.NaN),64.0,  0.00001);
        Assert.assertEquals(constantFunction.andThen(cubeFunction).andThen(squareFunction).apply(Double.NEGATIVE_INFINITY),64,0.00001);
        Assert.assertEquals(constantFunction.andThen(cubeFunction).andThen(squareFunction).apply(Double.POSITIVE_INFINITY),64,0.00001);
        Assert.assertEquals(identityFunction.andThen(reverseFunction).andThen(cubeFunction).apply(Double.NaN),Double.NaN,0.0001);
        Assert.assertEquals(identityFunction.andThen(reverseFunction).andThen(cubeFunction).apply(Double.NEGATIVE_INFINITY),0.0,0.00001);
        Assert.assertEquals(identityFunction.andThen(reverseFunction).andThen(cubeFunction).apply(Double.POSITIVE_INFINITY),0.0,0.00001);

    }

}