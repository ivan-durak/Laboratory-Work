package ru.ssau.tk.dmitriy.laboratorywork.functions;

import ru.ssau.tk.dmitriy.laboratorywork.exceptions.*;
import ru.ssau.tk.dmitriy.laboratorywork.operations.TabulatedFunctionOperationService;

public abstract class AbstractTabulatedFunction implements TabulatedFunction {

    protected abstract int floorIndexOfX(double x);

    protected abstract double extrapolateLeft(double x);

    protected abstract double extrapolateRight(double x);

    protected abstract double interpolate(double x, int floorIndex);

    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY) {
        return leftY + (x - leftX) * (rightY - leftY) / (rightX - leftX);
    }

    @Override
    public double apply(double x) {
        if (x < leftBound()) {
            return extrapolateLeft(x);
        } else if (x > rightBound()) {
            return extrapolateRight(x);
        }
        return indexOfX(x) == -1 ? interpolate(x, floorIndexOfX(x)) : getY(indexOfX(x));
    }

    protected static void checkLengthIsTheSame(double[] xValues, double[] yValues) {
        if (xValues.length != yValues.length) {
            throw new DifferentLengthOfArraysException();
        }
    }

    protected static void checkSorted(double[] xValues) {
        for (int element = 1; element < xValues.length; element++) {
            if (xValues[element - 1] >= xValues[element]) {
                throw new ArrayIsNotSortedException();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(getClass().getSimpleName()).append(" ").append("size = ").append(getCount()).append("\n");
        Point[] points = TabulatedFunctionOperationService.asPoints(this);
        for (Point point : points) {
            string.append("[").append(point.x).append("; ").append(point.y).append("]").append("\n");
        }
        return string.toString();
    }
}



