package ru.ssau.tk.dmitriy.laboratorywork.functions;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class LinkedListTabulatedFunctionTest {

    @Test
    public void testGetCount() {
        double[] xValues = {2, 3, 4, 5, 6, 7, 8, 9, 10}, yValues = {3, 5, 8, 5, 67, 2, 3, 6, 7};
        LinkedListTabulatedFunction link = new LinkedListTabulatedFunction(xValues, yValues);
        Assert.assertEquals(link.getCount(), 9);
    }

    @Test
    public void testLeftBound() {
        double[] xValues = {2, 3, 4, 5, 6, 7, 8, 9, 10}, yValues = {3, 5, 8, 5, 67, 2, 3, 6, 7};
        LinkedListTabulatedFunction link = new LinkedListTabulatedFunction(xValues, yValues);
        Assert.assertEquals(link.leftBound(), 2.0);
    }

    @Test
    public void testRightBound() {
        double[] xValues = {2, 3, 4, 5, 6, 7, 8, 9, 10}, yValues = {3, 5, 8, 5, 67, 2, 3, 6, 7};
        LinkedListTabulatedFunction link = new LinkedListTabulatedFunction(xValues, yValues);
        Assert.assertEquals(link.rightBound(), 10.0);
    }

    @Test
    public void testGetX() {
        double[] xValues = {2, 3, 4, 5, 6, 7, 8, 9, 10}, yValues = {3, 5, 8, 5, 67, 2, 3, 6, 7};
        LinkedListTabulatedFunction link = new LinkedListTabulatedFunction(xValues, yValues);
        Assert.assertEquals(link.getX(0), 2.0);
        Assert.assertEquals(link.getX(5), 7.0);
        Assert.assertEquals(link.getX(8), 10.0);
    }

    @Test
    public void testGetY() {
        double[] xValues = {2, 3, 4, 5, 6, 7, 8, 9, 10}, yValues = {3, 5, 8, 5, 67, 2, 3, 6, 7};
        LinkedListTabulatedFunction link = new LinkedListTabulatedFunction(xValues, yValues);
        Assert.assertEquals(link.getY(0), 3.0);
        Assert.assertEquals(link.getY(5), 2.0);
        Assert.assertEquals(link.getY(8), 7.0);
    }

    @Test
    public void testSetY() {
        double[] xValues = {2, 3, 4, 5, 6, 7, 8, 9, 10}, yValues = {3, 5, 8, 5, 67, 2, 3, 6, 7};
        LinkedListTabulatedFunction link = new LinkedListTabulatedFunction(xValues, yValues);
        link.setY(0, 45);
        link.setY(4, 6);
        link.setY(3, 10);
        Assert.assertEquals(link.getY(0), 45.0);
        Assert.assertEquals(link.getY(4), 6.0);
        Assert.assertEquals(link.getY(3), 10.0);
    }

    @Test
    public void testIndexOfX() {
        double[] xValues = {2, 3, 4, 5, 6, 7, 8, 9, 10}, yValues = {3, 5, 8, 5, 67, 2, 3, 6, 7};
        LinkedListTabulatedFunction link = new LinkedListTabulatedFunction(xValues, yValues);
        Assert.assertEquals(link.indexOfX(2), 0);
        Assert.assertEquals(link.indexOfX(5), 3);
        Assert.assertEquals(link.indexOfX(1), -1);
    }

    @Test
    public void testIndexOfY() {
        SquareFunction squareFunction = new SquareFunction();
        ReverseFunction reverseFunction = new ReverseFunction();
        CompositeFunction compositeFunction = new CompositeFunction(reverseFunction, squareFunction);
        LinkedListTabulatedFunction link = new LinkedListTabulatedFunction(compositeFunction, 10, 25, 15);
        Assert.assertEquals(link.indexOfY(17.5), -1);
        Assert.assertEquals(link.indexOfY(link.getY(6)), 6);
        Assert.assertEquals(link.indexOfY(link.getY(13)), 13);
    }

    @Test
    public void testFloorIndexOfX() {
        SquareFunction squareFunction = new SquareFunction();
        IdentityFunction identityFunction = new IdentityFunction();
        CompositeFunction compositeFunction = new CompositeFunction(identityFunction, squareFunction);
        LinkedListTabulatedFunction link = new LinkedListTabulatedFunction(compositeFunction, 10, 25, 15);
        Assert.assertEquals(link.floorIndexOfX(14.5), 4);
        Assert.assertEquals(link.floorIndexOfX(19.2), 9);
        Assert.assertEquals(link.floorIndexOfX(24.6), 14);
        Assert.assertEquals(link.floorIndexOfX(15.6), link.indexOfX(link.floorNodeOfX(15.6).x));
        Assert.assertEquals(link.floorIndexOfX(19.2), link.indexOfX(link.floorNodeOfX(19.2).x));
    }

    @Test
    public void testExtrapolateLeft() {
        SquareFunction squareFunction = new SquareFunction();
        IdentityFunction identityFunction = new IdentityFunction();
        CompositeFunction compositeFunction = new CompositeFunction(identityFunction, squareFunction);
        LinkedListTabulatedFunction link = new LinkedListTabulatedFunction(compositeFunction, 10, 25, 15);
        Assert.assertEquals(link.extrapolateLeft(9), 79.0);
        Assert.assertEquals(link.extrapolateLeft(7.5), 47.5);
        double[] xValues = {1}, yValues = {23};
        LinkedListTabulatedFunction linkedListTabulatedFunction = new LinkedListTabulatedFunction(xValues, yValues);
        Assert.assertEquals(linkedListTabulatedFunction.extrapolateLeft(1), 1.0);
    }

    @Test
    public void testExtrapolateRight() {
        SquareFunction squareFunction = new SquareFunction();
        IdentityFunction identityFunction = new IdentityFunction();
        CompositeFunction compositeFunction = new CompositeFunction(identityFunction, squareFunction);
        LinkedListTabulatedFunction link = new LinkedListTabulatedFunction(compositeFunction, 10, 25, 15);
        Assert.assertEquals(link.extrapolateRight(26), 674.0);
        Assert.assertEquals(link.extrapolateRight(28.2), 781.8);
        double[] xValues = {5}, yValues = {23};
        LinkedListTabulatedFunction linkedListTabulatedFunction = new LinkedListTabulatedFunction(xValues, yValues);
        Assert.assertEquals(linkedListTabulatedFunction.extrapolateRight(1), 5.0);
    }

    @Test
    public void testInterpolateWithTwoParameters() {
        SquareFunction squareFunction = new SquareFunction();
        IdentityFunction identityFunction = new IdentityFunction();
        CompositeFunction compositeFunction = new CompositeFunction(identityFunction, squareFunction);
        LinkedListTabulatedFunction link = new LinkedListTabulatedFunction(compositeFunction, 10, 25, 15);
        Assert.assertEquals(link.interpolate(14.5, link.floorIndexOfX(14.5)), 210.5);
        Assert.assertEquals(link.interpolate(22.3, link.floorIndexOfX(22.3)), 497.5, 0.00001);
        double[] xValues = {5}, yValues = {23};
        LinkedListTabulatedFunction linkedListTabulatedFunction = new LinkedListTabulatedFunction(xValues, yValues);
        Assert.assertEquals(linkedListTabulatedFunction.extrapolateRight(1), 5.0);
        Assert.assertEquals(linkedListTabulatedFunction.interpolate(3, linkedListTabulatedFunction.floorIndexOfX(3)), 5.0);
    }

    @Test
    public void testInterpolateWithFiveParameters() {
        SquareFunction squareFunction = new SquareFunction();
        IdentityFunction identityFunction = new IdentityFunction();
        CompositeFunction compositeFunction = new CompositeFunction(identityFunction, squareFunction);
        LinkedListTabulatedFunction link = new LinkedListTabulatedFunction(compositeFunction, 10, 25, 15);
        Assert.assertEquals(link.interpolate(12.5, link.floorNodeOfX(12.5).x, link.floorNodeOfX(12.5).next.x,//
                link.floorNodeOfX(12.5).y, link.floorNodeOfX(12.5).next.y), 156.5);
        Assert.assertEquals(link.interpolate(22.7, link.floorNodeOfX(22.7).x, link.floorNodeOfX(22.7).next.x,//
                link.floorNodeOfX(22.7).y, link.floorNodeOfX(22.7).next.y), 515.5);
    }
}