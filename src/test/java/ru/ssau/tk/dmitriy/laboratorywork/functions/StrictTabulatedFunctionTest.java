package ru.ssau.tk.dmitriy.laboratorywork.functions;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Iterator;

public class StrictTabulatedFunctionTest {

    @Test
    public void testGetCount() {
        double[] arrayOfX = {1, 3, 5, 7, 9}, arrayOfY = {4, 8, 12, 16, 20};
        TabulatedFunction tabulatedFunction = new ArrayTabulateFunction(arrayOfX, arrayOfY);
        TabulatedFunction wrapper = new StrictTabulatedFunction(tabulatedFunction);
        Assert.assertEquals(wrapper.getCount(), 5);
    }

    @Test
    public void testGetX() {
        double[] arrayOfX = {1, 3, 5, 7, 9}, arrayOfY = {4, 8, 12, 16, 20};
        TabulatedFunction tabulatedFunction = new LinkedListTabulatedFunction(arrayOfX, arrayOfY);
        TabulatedFunction wrapper = new StrictTabulatedFunction(tabulatedFunction);
        for (int i = 0; i < wrapper.getCount(); i++) {
            Assert.assertEquals(wrapper.getX(i), arrayOfX[i]);
        }
    }

    @Test
    public void testGetY() {
        double[] arrayOfX = {1, 3, 5, 7, 9}, arrayOfY = {4, 8, 12, 16, 20};
        TabulatedFunction tabulatedFunction = new LinkedListTabulatedFunction(arrayOfX, arrayOfY);
        TabulatedFunction wrapper = new StrictTabulatedFunction(tabulatedFunction);
        for (int i = 0; i < wrapper.getCount(); i++) {
            Assert.assertEquals(wrapper.getY(i), arrayOfY[i]);
        }
    }

    @Test
    public void testSetY() {
        double[] arrayOfX = {1, 3, 5, 7, 9}, arrayOfY = {4, 8, 12, 16, 20};
        TabulatedFunction tabulatedFunction = new ArrayTabulateFunction(arrayOfX, arrayOfY);
        TabulatedFunction wrapper = new StrictTabulatedFunction(tabulatedFunction);
        wrapper.setY(3, 18);
        wrapper.setY(1, 5);
        wrapper.setY(4, 100);
        Assert.assertEquals(wrapper.getY(1), 5.0);
        Assert.assertEquals(wrapper.getY(3), 18.0);
        Assert.assertEquals(wrapper.getY(4), 100.0);
    }

    @Test
    public void testIndexOfX() {
        double[] arrayOfX = {1, 3, 5, 7, 9}, arrayOfY = {4, 8, 12, 16, 20};
        TabulatedFunction tabulatedFunction = new ArrayTabulateFunction(arrayOfX, arrayOfY);
        TabulatedFunction wrapper = new StrictTabulatedFunction(tabulatedFunction);
        Assert.assertEquals(wrapper.indexOfX(3), 1);
        Assert.assertEquals(wrapper.indexOfX(9), 4);
        Assert.assertEquals(wrapper.indexOfX(11), -1);
    }

    @Test
    public void testIndexOfY() {
        double[] arrayOfX = {1, 3, 5, 7, 9}, arrayOfY = {4, 8, 12, 16, 20};
        TabulatedFunction tabulatedFunction = new LinkedListTabulatedFunction(arrayOfX, arrayOfY);
        TabulatedFunction wrapper = new StrictTabulatedFunction(tabulatedFunction);
        Assert.assertEquals(wrapper.indexOfY(8), 1);
        Assert.assertEquals(wrapper.indexOfY(16), 3);
        Assert.assertEquals(wrapper.indexOfY(25), -1);
    }

    @Test
    public void testLeftBound() {
        double[] arrayOfX = {1, 3, 5, 7, 9}, arrayOfY = {4, 8, 12, 16, 20};
        TabulatedFunction tabulatedFunction = new ArrayTabulateFunction(arrayOfX, arrayOfY);
        TabulatedFunction wrapper = new StrictTabulatedFunction(tabulatedFunction);
        Assert.assertEquals(wrapper.leftBound(), 1.0);
    }

    @Test
    public void testRightBound() {
        double[] arrayOfX = {1, 3, 5, 7, 9}, arrayOfY = {4, 8, 12, 16, 20};
        TabulatedFunction tabulatedFunction = new ArrayTabulateFunction(arrayOfX, arrayOfY);
        TabulatedFunction wrapper = new StrictTabulatedFunction(tabulatedFunction);
        Assert.assertEquals(wrapper.rightBound(), 9.0);
    }

    @Test
    public void testIterator() {
        double[] arrayOfX = {1, 3, 5, 7, 9}, arrayOfY = {4, 8, 12, 16, 20};
        TabulatedFunction tabulatedFunction = new LinkedListTabulatedFunction(arrayOfX, arrayOfY);
        TabulatedFunction wrapper = new StrictTabulatedFunction(tabulatedFunction);
        Iterator<Point> iterator = wrapper.iterator();
        int i = 0;
        for (Point point : wrapper) {
            Assert.assertEquals(point.x, wrapper.getX(i));
            Assert.assertEquals(point.y, wrapper.getY(i));
            i++;
        }
        i = 0;
        while (iterator.hasNext()) {
            Point point = iterator.next();
            Assert.assertEquals(point.x, wrapper.getX(i));
            Assert.assertEquals(point.y, wrapper.getY(i));
            i++;
        }
    }

    @Test
    public void testApply() {
        double[] arrayOfX = {1, 3, 5, 7, 9}, arrayOfY = {4, 8, 12, 16, 20};
        TabulatedFunction tabulatedFunction = new LinkedListTabulatedFunction(arrayOfX, arrayOfY);
        TabulatedFunction wrapper = new StrictTabulatedFunction(tabulatedFunction);
        Assert.assertEquals(wrapper.apply(7), 16.0);
        Assert.assertEquals(wrapper.apply(1), 4.0);
        Assert.assertThrows(UnsupportedOperationException.class, () -> {
            wrapper.apply(10);
        });
    }
}