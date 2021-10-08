package ru.ssau.tk.dmitriy.laboratorywork.functions;

import org.testng.Assert;
import org.testng.annotations.Test;


public class ArrayTabulateFunctionTest {
    private static final double DELTA = 0.0001;
    public static double[] xValues = new double[]{2.1, 3.4, 5.2, 6};
    public static double[] yValues = new double[]{-2.4, 1.2, 3, 5.1};
    public static SquareFunction sqObject = new SquareFunction();
    public static ArrayTabulateFunction arrayTabulateObject = new ArrayTabulateFunction(xValues, yValues);
    public static ArrayTabulateFunction arrayTabulateObjectTwo = new ArrayTabulateFunction(sqObject, 1.2, 67.2, 100);

    @Test
    public void testArrayTabulatedFunctionWithTwoParameters() {
        Assert.assertEquals(arrayTabulateObject.getCount(), 4);
    }

    @Test
    public void testGetCount() {
        Assert.assertEquals(arrayTabulateObjectTwo.getCount(), 100);
    }

    @Test
    public void testGetX() {
        for (int element = 0; element < 99; element++) {
            Assert.assertEquals(arrayTabulateObjectTwo.getX(element), element * (67.2 - 1.2) / 100.0 + 1.2, DELTA);
        }
    }

    @Test
    public void testGetY() {
        for (int element = 0; element < 99; element++) {
            Assert.assertEquals(arrayTabulateObjectTwo.getY(element), sqObject.apply(arrayTabulateObjectTwo.getX(element)), DELTA);
        }
    }

    @Test
    public void testLeftBound() {
        Assert.assertEquals(arrayTabulateObjectTwo.leftBound(), 1.2, DELTA);
    }

    @Test
    public void testRightBound() {
        Assert.assertEquals(arrayTabulateObjectTwo.rightBound(), 67.2, DELTA);
    }

    @Test
    public void testIndexOfX() {
        Assert.assertEquals(arrayTabulateObjectTwo.indexOfX(1.1), -1, DELTA);
        for (int element = 0; element < 99; element++) {
            Assert.assertEquals(arrayTabulateObjectTwo.indexOfX(1.2 + element * 0.66), element);
        }
    }

    @Test
    public void testIndexOfY() {
        Assert.assertEquals(arrayTabulateObjectTwo.indexOfY(0.432), -1, DELTA);
        for (int element = 0; element < 99; element++) {
            Assert.assertEquals(arrayTabulateObjectTwo.indexOfY(sqObject.apply(arrayTabulateObjectTwo.getX(element))), element);
        }
    }

    @Test
    public void testFloorIndexOfX() {
        for (int element = 0; element < 99; element++) {
            Assert.assertEquals(arrayTabulateObjectTwo.floorIndexOfX(1.2 + element * 0.66), element);
        }
        Assert.assertEquals(arrayTabulateObjectTwo.floorIndexOfX(1.1), 0);
        Assert.assertEquals(arrayTabulateObjectTwo.floorIndexOfX(4.6), 5);
        Assert.assertEquals(arrayTabulateObjectTwo.floorIndexOfX(67.3), 100);
    }

    @Test
    public void testExtrapolateLeft() {
        Assert.assertEquals(arrayTabulateObjectTwo.extrapolateLeft(1.1), 1.1340000000000003, DELTA);
        Assert.assertEquals(arrayTabulateObjectTwo.extrapolateLeft(0.9), 0.5220000000000002, DELTA);
        Assert.assertEquals(arrayTabulateObjectTwo.extrapolateLeft(Double.NEGATIVE_INFINITY), Double.NEGATIVE_INFINITY);
    }

    @Test
    public void testExtrapolateRight() {
        Assert.assertEquals(arrayTabulateObjectTwo.extrapolateRight(67.3), 4529.147999999999, DELTA);
        Assert.assertEquals(arrayTabulateObjectTwo.extrapolateRight(69), 4755.383999999999, DELTA);
        Assert.assertEquals(arrayTabulateObjectTwo.extrapolateRight(Double.POSITIVE_INFINITY), Double.POSITIVE_INFINITY);
    }

    @Test
    public void testInsert() {
        arrayTabulateObject.insert(3.4, 5); //если х найден в массиве
        Assert.assertEquals(arrayTabulateObject.getY(1), 5.0);
        Assert.assertEquals(arrayTabulateObject.getCount(), 4);
        arrayTabulateObject.insert(2, 2.5);//если х меньше левой границы
        Assert.assertEquals(arrayTabulateObject.getCount(), 5);
        Assert.assertEquals(arrayTabulateObject.getX(0), 2.0);
        Assert.assertEquals(arrayTabulateObject.getY(0), -2.9692307, DELTA);
        arrayTabulateObject.insert(7, 4);  //если х больше правой границы
        Assert.assertEquals(arrayTabulateObject.getX(5), 7.0, DELTA);
        Assert.assertEquals(arrayTabulateObject.getY(5), 7.725, DELTA);
        Assert.assertEquals(arrayTabulateObject.getCount(), 6);
        arrayTabulateObject.insert(4, 2.9); //если х внутри интервала значений
        Assert.assertEquals(arrayTabulateObject.getCount(), 7);
        Assert.assertEquals(arrayTabulateObject.getX(3), 4.0);
        Assert.assertEquals(arrayTabulateObject.getY(3), 4.33333, DELTA);
    }

    @Test
    public void testRemove() {
        double[] xValues = new double[]{2.1, 3.4, 5.2, 6};
        double[] yValues = new double[]{-2.4, 1.2, 3, 5.1};
        ArrayTabulateFunction arrayTabulateObjectThree = new ArrayTabulateFunction(xValues, yValues);
        Assert.assertEquals(arrayTabulateObjectThree.getCount(), 4);
        arrayTabulateObjectThree.remove(2);
        Assert.assertEquals(arrayTabulateObjectThree.getCount(), 3);
        Assert.assertEquals(arrayTabulateObjectThree.getX(2), 6.0);
        Assert.assertEquals(arrayTabulateObjectThree.getY(2), 5.1);
    }
}