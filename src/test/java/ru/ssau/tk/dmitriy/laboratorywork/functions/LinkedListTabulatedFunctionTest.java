package ru.ssau.tk.dmitriy.laboratorywork.functions;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.ssau.tk.dmitriy.laboratorywork.exceptions.InterpolationException;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListTabulatedFunctionTest {
    private final static double DELTA = 0.00001;

    @Test
    public void testGetCount() {
        double[] xValues = {2, 3, 4, 5, 6, 7, 8, 9, 10}, yValues = {3, 5, 8, 5, 67, 2, 3, 6, 7};
        TabulatedFunction link = new LinkedListTabulatedFunction(xValues, yValues);
        Assert.assertEquals(link.getCount(), 9);
    }

    @Test
    public void testLeftBound() {
        double[] xValues = {2, 3, 4, 5, 6, 7, 8, 9, 10}, yValues = {3, 5, 8, 5, 67, 2, 3, 6, 7};
        TabulatedFunction link = new LinkedListTabulatedFunction(xValues, yValues);
        Assert.assertEquals(link.leftBound(), 2.0);
    }

    @Test
    public void testRightBound() {
        double[] xValues = {2, 3, 4, 5, 6, 7, 8, 9, 10}, yValues = {3, 5, 8, 5, 67, 2, 3, 6, 7};
        TabulatedFunction link = new LinkedListTabulatedFunction(xValues, yValues);
        Assert.assertEquals(link.rightBound(), 10.0);
    }

    @Test
    public void testGetX() {
        double[] xValues = {2, 3, 4, 5, 6, 7, 8, 9, 10}, yValues = {3, 5, 8, 5, 67, 2, 3, 6, 7};
        TabulatedFunction link = new LinkedListTabulatedFunction(xValues, yValues);
        Assert.assertEquals(link.getX(0), 2.0);
        Assert.assertEquals(link.getX(5), 7.0);
        Assert.assertEquals(link.getX(8), 10.0);
        Assert.assertThrows(IllegalArgumentException.class, () -> link.getX(-2));
    }

    @Test
    public void testGetY() {
        double[] xValues = {2, 3, 4, 5, 6, 7, 8, 9, 10}, yValues = {3, 5, 8, 5, 67, 2, 3, 6, 7};
        TabulatedFunction link = new LinkedListTabulatedFunction(xValues, yValues);
        Assert.assertEquals(link.getY(0), 3.0);
        Assert.assertEquals(link.getY(5), 2.0);
        Assert.assertEquals(link.getY(8), 7.0);
        Assert.assertThrows(IllegalArgumentException.class, () -> link.getY(-1));
    }

    @Test
    public void testSetY() {
        double[] xValues = {2, 3, 4, 5, 6, 7, 8, 9, 10}, yValues = {3, 5, 8, 5, 67, 2, 3, 6, 7};
        TabulatedFunction link = new LinkedListTabulatedFunction(xValues, yValues);
        link.setY(0, 45);
        link.setY(4, 6);
        link.setY(3, 10);
        Assert.assertEquals(link.getY(0), 45.0);
        Assert.assertEquals(link.getY(4), 6.0);
        Assert.assertEquals(link.getY(3), 10.0);
        Assert.assertThrows(IllegalArgumentException.class, () -> link.setY(-2, -10));
    }

    @Test
    public void testIndexOfX() {
        double[] xValues = {2, 3, 4, 5, 6, 7, 8, 9, 10}, yValues = {3, 5, 8, 5, 67, 2, 3, 6, 7};
        TabulatedFunction link = new LinkedListTabulatedFunction(xValues, yValues);
        Assert.assertEquals(link.indexOfX(2), 0);
        Assert.assertEquals(link.indexOfX(5), 3);
        Assert.assertEquals(link.indexOfX(1), -1);
    }

    @Test
    public void testIndexOfY() {
        SquareFunction squareFunction = new SquareFunction();
        ReverseFunction reverseFunction = new ReverseFunction();
        CompositeFunction compositeFunction = new CompositeFunction(reverseFunction, squareFunction);
        TabulatedFunction link = new LinkedListTabulatedFunction(compositeFunction, 10, 25, 16);
        Assert.assertEquals(link.indexOfY(17.5), -1);
        Assert.assertEquals(link.indexOfY(link.getY(6)), 6);
        Assert.assertEquals(link.indexOfY(link.getY(13)), 13);
    }

    @Test
    public void testFloorIndexOfX() {
        SquareFunction squareFunction = new SquareFunction();
        IdentityFunction identityFunction = new IdentityFunction();
        CompositeFunction compositeFunction = new CompositeFunction(identityFunction, squareFunction);
        AbstractTabulatedFunction link = new LinkedListTabulatedFunction(compositeFunction, 10, 25, 16);
        Assert.assertEquals(link.floorIndexOfX(14.5), 4);
        Assert.assertEquals(link.floorIndexOfX(19.2), 9);
        Assert.assertEquals(link.floorIndexOfX(24.6), 14);
        Assert.assertThrows(IllegalArgumentException.class, () -> link.floorIndexOfX(7));
    }

    @Test
    public void testExtrapolateLeft() {
        SquareFunction squareFunction = new SquareFunction();
        IdentityFunction identityFunction = new IdentityFunction();
        CompositeFunction compositeFunction = new CompositeFunction(identityFunction, squareFunction);
        AbstractTabulatedFunction link = new LinkedListTabulatedFunction(compositeFunction, 10, 25, 16);
        Assert.assertEquals(link.extrapolateLeft(9), 79.0);
        Assert.assertEquals(link.extrapolateLeft(7.5), 47.5);
    }

    @Test
    public void testExtrapolateRight() {
        SquareFunction squareFunction = new SquareFunction();
        IdentityFunction identityFunction = new IdentityFunction();
        CompositeFunction compositeFunction = new CompositeFunction(identityFunction, squareFunction);
        AbstractTabulatedFunction link = new LinkedListTabulatedFunction(compositeFunction, 10, 25, 16);
        Assert.assertEquals(link.extrapolateRight(26), 674.0);
        Assert.assertEquals(link.extrapolateRight(28.2), 781.8);
    }

    @Test
    public void testInterpolateWithTwoParameters() {
        SquareFunction squareFunction = new SquareFunction();
        IdentityFunction identityFunction = new IdentityFunction();
        CompositeFunction compositeFunction = new CompositeFunction(identityFunction, squareFunction);
        AbstractTabulatedFunction link = new LinkedListTabulatedFunction(compositeFunction, 10, 25, 16);
        Assert.assertEquals(link.interpolate(14.5, link.floorIndexOfX(14.5)), 210.5);
        Assert.assertEquals(link.interpolate(22.3, link.floorIndexOfX(22.3)), 497.5, 0.00001);
        Assert.assertThrows(InterpolationException.class, () -> link.interpolate(15.1, 3));
    }

    @Test
    public void testInterpolateWithFiveParameters() {
        SquareFunction squareFunction = new SquareFunction();
        IdentityFunction identityFunction = new IdentityFunction();
        CompositeFunction compositeFunction = new CompositeFunction(identityFunction, squareFunction);
        LinkedListTabulatedFunction link = new LinkedListTabulatedFunction(compositeFunction, 10, 25, 16);
        Assert.assertEquals(link.interpolate(12.5, link.floorNodeOfX(12.5).x, link.floorNodeOfX(12.5).next.x,//
                link.floorNodeOfX(12.5).y, link.floorNodeOfX(12.5).next.y), 156.5);
        Assert.assertEquals(link.interpolate(22.7, link.floorNodeOfX(22.7).x, link.floorNodeOfX(22.7).next.x,//
                link.floorNodeOfX(22.7).y, link.floorNodeOfX(22.7).next.y), 515.5);
    }

    @Test
    public void testApply() {
        double[] xValues = {2.3, 3.1, 4.6, 5.3, 6.7, 7.2, 8.0}, yValues = {1.0, 3.4, 5.2, 6.9, 7.5, 8.4, 9.8};
        TabulatedFunction link = new LinkedListTabulatedFunction(xValues, yValues);
        Assert.assertEquals(link.apply(4.6), 5.2); // ?? ???????????? ?? ??????????????
        Assert.assertEquals(link.apply(2), 0.1, 0.00001); //?? ???????????? ?????????? ??????????????
        Assert.assertEquals(link.apply(8.5), 10.675); //?? ???????????? ???????????? ??????????????
        Assert.assertEquals(link.apply(6), 7.2); //?? ???????????? ???????????????????? ??????????????????
    }

    @Test
    public void testInsert() {
        double[] xValues = {2.3, 3.1, 4.6, 5.3, 6.7, 7.2, 8.0}, yValues = {1.0, 3.4, 5.2, 6.9, 7.5, 8.4, 9.8};
        LinkedListTabulatedFunction link = new LinkedListTabulatedFunction(xValues, yValues);
        Assert.assertEquals(link.getCount(), 7);
        link.insert(2.3, 9);
        link.insert(3.1, 10);
        Assert.assertEquals(link.getY(0), 9.0);
        Assert.assertEquals(link.getY(1), 10.0);
        Assert.assertEquals(link.getCount(), 7);
        Assert.assertEquals(link.getY(2), 5.2);
        link.insert(4, 4.5);
        Assert.assertEquals(link.getCount(), 8);
        Assert.assertEquals(link.getY(2), 4.5);
        link.insert(9, 1);
        Assert.assertEquals(link.getCount(), 9);
        Assert.assertEquals(link.rightBound(), 9.0);
        Assert.assertEquals(link.getY(link.getCount() - 1), 1.0);
        link.insert(2, 11);
        Assert.assertEquals(link.getCount(), 10);
        Assert.assertEquals(link.getX(0), 2.0);
        Assert.assertEquals(link.getY(0), 11.0);
    }

    @Test
    public void testRemove() {
        double[] xValues = {1, 2, 3, 4, 5, 6}, yValues = {2, 4, 6, 8, 10, 12};
        LinkedListTabulatedFunction link = new LinkedListTabulatedFunction(xValues, yValues);
        Assert.assertEquals(link.getCount(), 6);
        Assert.assertEquals(link.getX(3), 4.0);
        Assert.assertEquals(link.getY(3), 8.0);
        link.remove(3);
        Assert.assertEquals(link.getCount(), 5);
        Assert.assertEquals(link.getX(3), 5.0);
        Assert.assertEquals(link.getY(3), 10.0);
    }

    @Test
    public void testIterator() {
        TabulatedFunction tabulatedFunction = new LinkedListTabulatedFunction(new double[]{1, 2, 3, 4, 5},//
                new double[]{2, 4, 6, 8, 10});
        Iterator<Point> iterator = tabulatedFunction.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Point point = iterator.next();
            Assert.assertEquals(point.x, tabulatedFunction.getX(i), DELTA);
            Assert.assertEquals(point.y, tabulatedFunction.getY(i), DELTA);
            i++;
        }
        Assert.assertThrows(NoSuchElementException.class, iterator::next);
        Assert.assertEquals(i, 5);
        i = 0;
        for (Point point : tabulatedFunction) {
            Assert.assertEquals(point.x, tabulatedFunction.getX(i), DELTA);
            Assert.assertEquals(point.y, tabulatedFunction.getY(i), DELTA);
            i++;
        }
        Assert.assertEquals(i, 5);
    }
}