package ru.ssau.tk.dmitriy.laboratorywork.functions;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class ArrayTabulateFunctionTest {
    private static final double DELTA = 0.0001;
    private static final double STEP = (67.2 - 1.2) / 99.0;
    private static final double[] xValues = new double[]{6.4, 5.4, 6, 4.3};
    private static final double[] yValues = new double[]{-2.7, 1.4, 3, 5.1};
    private static final SquareFunction SquareObject = new SquareFunction();
    private static final ArrayTabulateFunction arrayTabulatedObject = new ArrayTabulateFunction(xValues, yValues);
    private static final ArrayTabulateFunction arrayTabulatedObjectTwo = new ArrayTabulateFunction(SquareObject, 1.2, 67.2, 100);


    @Test
    public void testArrayTabulatedFunctionWithTwoParameters() {
        double[] xValues = new double[]{2.1, 3.4, 5.2, 6};
        double[] yValues = new double[]{-2.4, 1.2, 3, 5.1};
        TabulatedFunction arrayTabulateObject = new ArrayTabulateFunction(xValues, yValues);
        Assert.assertEquals(arrayTabulateObject.getCount(), 4);
    }

    @Test
    public void testGetCount() {
        SquareFunction sqObject = new SquareFunction();
        TabulatedFunction arrayTabulateObjectTwo = new ArrayTabulateFunction(sqObject, 1.2, 67.2, 101);
        Assert.assertEquals(arrayTabulateObjectTwo.getCount(), 101);
    }

    @Test
    public void testGetX() {
        SquareFunction sqObject = new SquareFunction();
        TabulatedFunction arrayTabulateObjectTwo = new ArrayTabulateFunction(sqObject, 1.2, 67.2, 101);
        for (int element = 0; element < 99; element++) {
            Assert.assertEquals(arrayTabulateObjectTwo.getX(element), element * (67.2 - 1.2) / 100.0 + 1.2, DELTA);
        }
        Assert.assertThrows(IllegalArgumentException.class, () -> arrayTabulateObjectTwo.getX(-2));
    }

    @Test
    public void testGetY() {
        SquareFunction sqObject = new SquareFunction();
        TabulatedFunction arrayTabulateObjectTwo = new ArrayTabulateFunction(sqObject, 1.2, 67.2, 101);
        for (int element = 0; element < 99; element++) {
            Assert.assertEquals(arrayTabulateObjectTwo.getY(element), sqObject.apply(arrayTabulateObjectTwo.getX(element)), DELTA);
        }
        Assert.assertThrows(IllegalArgumentException.class, () -> arrayTabulateObjectTwo.getY(-2));
    }

    @Test
    public void testSetY() {
        double[] xValues = new double[]{2.1, 3.4, 5.2, 6};
        double[] yValues = new double[]{-2.4, 1.2, 3, 5.1};
        TabulatedFunction array = new ArrayTabulateFunction(xValues, yValues);
        for (int i = 0; i < yValues.length; i++) {
            Assert.assertEquals(array.getY(i), yValues[i]);
        }
        array.setY(1, 3.9);
        array.setY(3, 11);
        Assert.assertEquals(array.getY(1), 3.9);
        Assert.assertEquals(array.getY(3), 11.0);
        Assert.assertThrows(IllegalArgumentException.class, () -> array.setY(-5, -200));
    }

    @Test
    public void testLeftBound() {
        SquareFunction sqObject = new SquareFunction();
        TabulatedFunction arrayTabulateObjectTwo = new ArrayTabulateFunction(sqObject, 1.2, 67.2, 101);
        Assert.assertEquals(arrayTabulateObjectTwo.leftBound(), 1.2, DELTA);
    }

    @Test
    public void testRightBound() {
        SquareFunction sqObject = new SquareFunction();
        TabulatedFunction arrayTabulateObjectTwo = new ArrayTabulateFunction(sqObject, 1.2, 67.2, 101);
        Assert.assertEquals(arrayTabulateObjectTwo.rightBound(), 67.2, DELTA);
    }

    @Test
    public void testIndexOfX() {
        SquareFunction sqObject = new SquareFunction();
        TabulatedFunction arrayTabulateObjectTwo = new ArrayTabulateFunction(sqObject, 1.2, 67.2, 101);
        Assert.assertEquals(arrayTabulateObjectTwo.indexOfX(1.1), -1, DELTA);
        for (int element = 0; element < 99; element++) {
            Assert.assertEquals(arrayTabulateObjectTwo.indexOfX(1.2 + element * 0.66), element);
        }
    }

    @Test
    public void testIndexOfY() {
        SquareFunction sqObject = new SquareFunction();
        TabulatedFunction arrayTabulateObjectTwo = new ArrayTabulateFunction(sqObject, 1.2, 67.2, 101);
        Assert.assertEquals(arrayTabulateObjectTwo.indexOfY(0.432), -1, DELTA);
        for (int element = 0; element < 99; element++) {
            Assert.assertEquals(arrayTabulateObjectTwo.indexOfY(sqObject.apply(arrayTabulateObjectTwo.getX(element))), element);
        }
    }

    @Test
    public void testFloorIndexOfX() {
        SquareFunction sqObject = new SquareFunction();
        AbstractTabulatedFunction arrayTabulateObjectTwo = new ArrayTabulateFunction(sqObject, 1.2, 67.2, 101);
        for (int element = 0; element < 99; element++) {
            Assert.assertEquals(arrayTabulateObjectTwo.floorIndexOfX(1.2 + element * 0.66), element);
        }
        Assert.assertEquals(arrayTabulateObjectTwo.floorIndexOfX(4.6), 5);
        Assert.assertEquals(arrayTabulateObjectTwo.floorIndexOfX(67.3), 101);
        Assert.assertThrows(IllegalArgumentException.class, () -> arrayTabulateObjectTwo.floorIndexOfX(-2));
    }

    @Test
    public void testExtrapolateLeft() {
        SquareFunction sqObject = new SquareFunction();
        AbstractTabulatedFunction arrayTabulateObjectTwo = new ArrayTabulateFunction(sqObject, 1.2, 67.2, 101);
        Assert.assertEquals(arrayTabulateObjectTwo.extrapolateLeft(1.1), 1.1340000000000003, DELTA);
        Assert.assertEquals(arrayTabulateObjectTwo.extrapolateLeft(0.9), 0.5220000000000002, DELTA);
        Assert.assertEquals(arrayTabulateObjectTwo.extrapolateLeft(Double.NEGATIVE_INFINITY), Double.NEGATIVE_INFINITY);
    }

    @Test
    public void testExtrapolateRight() {
        SquareFunction sqObject = new SquareFunction();
        AbstractTabulatedFunction arrayTabulateObjectTwo = new ArrayTabulateFunction(sqObject, 1, 15, 15);
        Assert.assertEquals(arrayTabulateObjectTwo.extrapolateRight(15.5), 239.5, DELTA);
        Assert.assertEquals(arrayTabulateObjectTwo.extrapolateRight(16), 254.0, DELTA);
        Assert.assertEquals(arrayTabulateObjectTwo.extrapolateRight(Double.POSITIVE_INFINITY), Double.POSITIVE_INFINITY);
    }

    @Test
    public void testInsert() {
        double[] xValues = new double[]{2.1, 3.4, 5.2, 6};
        double[] yValues = new double[]{-2.4, 1.2, 3, 5.1};
        ArrayTabulateFunction array = new ArrayTabulateFunction(xValues, yValues);
        array.insert(3.4, 5); //если х найден в массиве
        Assert.assertEquals(array.getY(1), 5.0);
        double[] newYValues = new double[]{-2.4, 5.0, 3, 5.1};
        for (int i = 0; i < xValues.length; i++) {
            Assert.assertEquals(array.getX(i), xValues[i], DELTA);
        }
        for (int i = 0; i < newYValues.length; i++) {
            Assert.assertEquals(array.getY(i), newYValues[i], DELTA);
        }
        Assert.assertEquals(array.getCount(), 4);
        array.insert(2, 2.5);//если х меньше левой границы
        Assert.assertEquals(array.getCount(), 5);
        Assert.assertEquals(array.getX(0), 2.0);
        Assert.assertEquals(array.getY(0), 2.5, DELTA);
        double[] newXValues = new double[]{2.0, 2.1, 3.4, 5.2, 6};
        newYValues = new double[]{2.5, -2.4, 5.0, 3, 5.1};
        for (int i = 0; i < newXValues.length; i++) {
            Assert.assertEquals(array.getX(i), newXValues[i], DELTA);
        }
        for (int i = 0; i < newYValues.length; i++) {
            Assert.assertEquals(array.getY(i), newYValues[i], DELTA);
        }
        Assert.assertEquals(array.getCount(), 5);
        array.insert(7, 4);  //если х больше правой границы
        Assert.assertEquals(array.getCount(), 6);
        Assert.assertEquals(array.getX(5), 7.0, DELTA);
        Assert.assertEquals(array.getY(5), 4.0, DELTA);
        newXValues = new double[]{2.0, 2.1, 3.4, 5.2, 6, 7};
        newYValues = new double[]{2.5, -2.4, 5.0, 3, 5.1, 4};
        for (int i = 0; i < newXValues.length; i++) {
            Assert.assertEquals(array.getX(i), newXValues[i], DELTA);
        }
        for (int i = 0; i < newYValues.length; i++) {
            Assert.assertEquals(array.getY(i), newYValues[i], DELTA);
        }
        Assert.assertEquals(array.getCount(), 6);
        array.insert(4, 2.9); //если х внутри интервала значений
        Assert.assertEquals(array.getCount(), 7);
        Assert.assertEquals(array.getX(3), 4.0);
        Assert.assertEquals(array.getY(3), 2.9, DELTA);
        newXValues = new double[]{2.0, 2.1, 3.4, 4, 5.2, 6, 7};
        newYValues = new double[]{2.5, -2.4, 5.0, 2.9, 3, 5.1, 4};
        for (int i = 0; i < newXValues.length; i++) {
            Assert.assertEquals(array.getX(i), newXValues[i], DELTA);
        }
        for (int i = 0; i < newYValues.length; i++) {
            Assert.assertEquals(array.getY(i), newYValues[i], DELTA);
        }
    }

    @Test
    public void testRemove() {
        double[] newXValues = new double[]{2.1, 3.4, 5.2, 6};
        double[] newYValues = new double[]{-2.4, 1.2, 3, 5.1};
        ArrayTabulateFunction array = new ArrayTabulateFunction(newXValues, newYValues);
        Assert.assertEquals(array.getCount(), 4);
        for (int i = 0; i < newXValues.length; i++) {
            Assert.assertEquals(array.getX(i), newXValues[i]);
        }
        for (int i = 0; i < newYValues.length; i++) {
            Assert.assertEquals(array.getY(i), newYValues[i]);
        }
        array.remove(2);
        Assert.assertEquals(array.getCount(), 3);
        Assert.assertEquals(array.getY(2), 5.1);
        Assert.assertEquals(array.getX(2), 6.0);
        newXValues = new double[]{2.1, 3.4, 6};
        newYValues = new double[]{-2.4, 1.2, 5.1};
        for (int i = 0; i < newXValues.length; i++) {
            Assert.assertEquals(array.getX(i), newXValues[i]);
        }
        for (int i = 0; i < newYValues.length; i++) {
            Assert.assertEquals(array.getY(i), newYValues[i]);
        }
        Assert.assertThrows(IllegalArgumentException.class, () -> array.remove(-3));
    }

    @Test
    public void testApply() {
        double[] xValues = new double[]{2.0, 2.9, 3.6, 4.5, 5.7, 6.3, 7.1};
        double[] yValues = new double[]{5.9, 5.4, 4.9, 4.2, 3.7, 3.1, 2.4};
        MathFunction array = new ArrayTabulateFunction(xValues, yValues);
        Assert.assertEquals(array.apply(4.5), 4.2); //х найден в таблице
        Assert.assertEquals(array.apply(1.8), 6.01111, DELTA); // х меньше левой границы
        Assert.assertEquals(array.apply(7.5), 2.05, DELTA); // х больше правой границы
        Assert.assertEquals(array.apply(5), 3.99166, DELTA); //х внутри некоторого интервала
    }
    @Test
    public static void testIterator() {
        Iterator<Point> iterator = arrayTabulatedObject.iterator();
        int element = 0;
        while (iterator.hasNext()) {
            Point point = iterator.next();
            Assert.assertEquals(point.x, arrayTabulatedObject.getX(element), DELTA);
            Assert.assertEquals(point.y, arrayTabulatedObject.getY(element++), DELTA);
        }
        Assert.assertThrows(NoSuchElementException.class, iterator::next);
        Assert.assertEquals(element, arrayTabulatedObject.getCount());
        element = 0;
        for (Point point : arrayTabulatedObject) {
            Assert.assertEquals(point.x, arrayTabulatedObject.getX(element), DELTA);
            Assert.assertEquals(point.y, arrayTabulatedObject.getY(element++), DELTA);
        }
        Assert.assertEquals(element, arrayTabulatedObject.getCount());
    }
}