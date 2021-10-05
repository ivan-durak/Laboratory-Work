package ru.ssau.tk.dmitriy.laboratorywork.functions;

import org.testng.Assert;
import org.testng.annotations.Test;


public class ArrayTabulateFunctionTest {
    static final double DELTA = 0.0001;
    static double[] xValues = new double[]{3.4, 5.2, 6, 2.1};
    static double[] yValues = new double[]{-2.4, 1.2, 3, 5.1};
    static SquareFunction SqObject = new SquareFunction();
    static ArrayTabulateFunction arrayTabulateObject = new ArrayTabulateFunction(xValues, yValues);
    static ArrayTabulateFunction arrayTabulateObjectTwo = new ArrayTabulateFunction(SqObject, 1.2, 67.2, 100);

    @Test
    public static void testArrayTabulatedFunctionWithTwoParameters() {
        Assert.assertEquals(arrayTabulateObject.getCount(), 4);
    }

    @Test
    public static void testGetCount() {
        Assert.assertEquals(arrayTabulateObjectTwo.getCount(), 100);
    }

    @Test
    public static void testGetX() {
        for (int element = 0; element < 99; element++) {
            Assert.assertEquals(arrayTabulateObjectTwo.getX(element), element * (67.2 - 1.2) / 100.0 + 1.2, DELTA);
        }
    }

    @Test
    public static void testGetY() {
        for (int element = 0; element < 99; element++) {
            Assert.assertEquals(arrayTabulateObjectTwo.getY(element), SqObject.apply(arrayTabulateObjectTwo.getX(element)), DELTA);
        }
    }

    @Test
    public static void testLeftBound() {
        Assert.assertEquals(arrayTabulateObjectTwo.leftBound(), 1.2, DELTA);
    }

    @Test
    public static void testRightBound() {
        Assert.assertEquals(arrayTabulateObjectTwo.rightBound(), 67.2, DELTA);
    }

    @Test
    public static void testIndexOfX() {
        Assert.assertEquals(arrayTabulateObjectTwo.indexOfX(1.1), -1, DELTA);
        for (int element = 0; element < 99; element++) {
            Assert.assertEquals(arrayTabulateObjectTwo.indexOfX(1.2 + element * 0.66), element);
        }
    }

    @Test
    public static void testIndexOfY() {
        Assert.assertEquals(arrayTabulateObjectTwo.indexOfY(0.432), -1, DELTA);
        for (int element = 0; element < 99; element++) {
            Assert.assertEquals(arrayTabulateObjectTwo.indexOfY(SqObject.apply(arrayTabulateObjectTwo.getX(element))), element);
        }
    }

    @Test
    public static void testFloorIndexOfX() {
        for (int element = 0; element < 99; element++) {
            Assert.assertEquals(arrayTabulateObjectTwo.floorIndexOfX(1.2 + element * 0.66), element);
        }
        Assert.assertEquals(arrayTabulateObjectTwo.floorIndexOfX(1.1), 0);
        Assert.assertEquals(arrayTabulateObjectTwo.floorIndexOfX(4.6), 5);
        Assert.assertEquals(arrayTabulateObjectTwo.floorIndexOfX(67.3), 100);
    }

    @Test
    public static void testExtrapolateLeft() {
        Assert.assertEquals(arrayTabulateObjectTwo.extrapolateLeft(1.1), 1.1340000000000003, DELTA);
        Assert.assertEquals(arrayTabulateObjectTwo.extrapolateLeft(0.9), 0.5220000000000002, DELTA);
        Assert.assertEquals(arrayTabulateObjectTwo.extrapolateLeft(Double.NEGATIVE_INFINITY), Double.NEGATIVE_INFINITY);
    }

    @Test
    public static void testExtrapolateRight() {
        Assert.assertEquals(arrayTabulateObjectTwo.extrapolateRight(67.3), 4529.147999999999, DELTA);
        Assert.assertEquals(arrayTabulateObjectTwo.extrapolateRight(69), 4755.383999999999, DELTA);
        Assert.assertEquals(arrayTabulateObjectTwo.extrapolateRight(Double.POSITIVE_INFINITY), Double.POSITIVE_INFINITY);
    }
}