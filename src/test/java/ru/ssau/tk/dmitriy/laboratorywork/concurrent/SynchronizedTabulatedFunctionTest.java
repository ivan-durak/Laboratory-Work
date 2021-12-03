package ru.ssau.tk.dmitriy.laboratorywork.concurrent;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.ssau.tk.dmitriy.laboratorywork.functions.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SynchronizedTabulatedFunctionTest {
    private static final double DELTA = 0.00001;

    @Test
    public void testGetCount() {
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = new SynchronizedTabulatedFunction(
                new LinkedListTabulatedFunction(new SquareFunction(), 1, 10, 10));
        Assert.assertEquals(synchronizedTabulatedFunction.getCount(), 10);
    }

    @Test
    public void testGetX() {
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = new SynchronizedTabulatedFunction(
                new ArrayTabulateFunction(new SquareFunction(), 1, 10, 10));
        Assert.assertEquals(synchronizedTabulatedFunction.getX(1), 2.0);
        Assert.assertEquals(synchronizedTabulatedFunction.getX(4), 5.0);
        Assert.assertEquals(synchronizedTabulatedFunction.getX(7), 8.0);
    }

    @Test
    public void testGetY() {
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = new SynchronizedTabulatedFunction(
                new LinkedListTabulatedFunction(new SquareFunction(), 1, 10, 10));
        Assert.assertEquals(synchronizedTabulatedFunction.getY(2), 9.0);
        Assert.assertEquals(synchronizedTabulatedFunction.getY(5), 36.0);
        Assert.assertEquals(synchronizedTabulatedFunction.getY(8), 81.0);
    }

    @Test
    public void testSetY() {
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = new SynchronizedTabulatedFunction(
                new ArrayTabulateFunction(new double[]{2, 4, 6, 8, 10}, new double[]{4, 16, 36, 64, 100}));
        synchronizedTabulatedFunction.setY(0, 0);
        synchronizedTabulatedFunction.setY(2, 0);
        synchronizedTabulatedFunction.setY(3, 0);
        Assert.assertEquals(synchronizedTabulatedFunction.getY(0), 0.0);
        Assert.assertEquals(synchronizedTabulatedFunction.getY(2), 0.0);
        Assert.assertEquals(synchronizedTabulatedFunction.getY(3), 0.0);
    }

    @Test
    public void testIndexOfX() {
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = new SynchronizedTabulatedFunction(
                new LinkedListTabulatedFunction(new double[]{1, 3, 5, 7, 9}, new double[]{2, 10, 26, 50, 82}));
        Assert.assertEquals(synchronizedTabulatedFunction.indexOfX(1), 0);
        Assert.assertEquals(synchronizedTabulatedFunction.indexOfX(5), 2);
        Assert.assertEquals(synchronizedTabulatedFunction.indexOfX(6), -1);
    }

    @Test
    public void testIndexOfY() {
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = new SynchronizedTabulatedFunction(
                new ArrayTabulateFunction(new IdentityFunction(), 1, 10, 10));
        Assert.assertEquals(synchronizedTabulatedFunction.indexOfY(2), 1);
        Assert.assertEquals(synchronizedTabulatedFunction.indexOfY(5), 4);
        Assert.assertEquals(synchronizedTabulatedFunction.indexOfY(7), 6);
    }

    @Test
    public void testLeftBound() {
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = new SynchronizedTabulatedFunction(
                new LinkedListTabulatedFunction(new SquareFunction(), 1, 10, 10));
        Assert.assertEquals(synchronizedTabulatedFunction.leftBound(), 1.0);
    }

    @Test
    public void testRightBound() {
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = new SynchronizedTabulatedFunction(
                new LinkedListTabulatedFunction(new SquareFunction(), 1, 10, 10));
        Assert.assertEquals(synchronizedTabulatedFunction.rightBound(), 10.0);
    }

    @Test
    public void testIterator() {
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = new SynchronizedTabulatedFunction(
                new ArrayTabulateFunction(new SquareFunction(), 1, 10, 10));
        Iterator<Point> iterator = synchronizedTabulatedFunction.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Point point = iterator.next();
            Assert.assertEquals(point.x, synchronizedTabulatedFunction.getX(i));
            Assert.assertEquals(point.y, synchronizedTabulatedFunction.getY(i));
            i++;
        }
        Assert.assertThrows(NoSuchElementException.class, iterator::next);
        Assert.assertEquals(i, 10);
        i = 0;
        for (Point point : synchronizedTabulatedFunction) {
            Assert.assertEquals(point.x, synchronizedTabulatedFunction.getX(i));
            Assert.assertEquals(point.y, synchronizedTabulatedFunction.getY(i));
            i++;
        }
        Assert.assertEquals(i, 10);
    }

    @Test
    public void testApply() {
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = new SynchronizedTabulatedFunction(
                new ArrayTabulateFunction(new SquareFunction(), 1, 10, 10));
        Assert.assertEquals(synchronizedTabulatedFunction.apply(0.5), -0.5);//жесть, ну и левая экстраполяция
        Assert.assertEquals(synchronizedTabulatedFunction.apply(11), 119.0);//ну и точность блин
        Assert.assertEquals(synchronizedTabulatedFunction.apply(5), 25.0);
        Assert.assertEquals(synchronizedTabulatedFunction.apply(6.5), 42.5);
    }
}