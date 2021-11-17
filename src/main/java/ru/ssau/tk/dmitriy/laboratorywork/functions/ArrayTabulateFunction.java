package ru.ssau.tk.dmitriy.laboratorywork.functions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayTabulateFunction extends AbstractTabulatedFunction implements Insertable, Removable, Serializable {
    private static final long serialVersionUID = 7927110438597458408L;
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    private double[] xValues;
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    private double[] yValues;
    private int count = 0;

    @JsonCreator
    public ArrayTabulateFunction(@JsonProperty(value = "xValues") double[] xValues, @JsonProperty(value = "yValues") double[] yValues) {
        if (xValues.length < 2 | yValues.length < 2) {
            throw new IllegalArgumentException("the length of the table is less than the minimum");
        }
        AbstractTabulatedFunction.checkLengthIsTheSame(xValues, yValues);
        AbstractTabulatedFunction.checkSorted(xValues);
        count = xValues.length;
        this.xValues = Arrays.copyOf(xValues, count);
        this.yValues = Arrays.copyOf(yValues, count);
    }

    public ArrayTabulateFunction(MathFunction source, double xFrom, double xTo, int count) {
        if (count < 2) {
            throw new IllegalArgumentException("the length of the table is less than the minimum");
        }
        if (xFrom >= xTo) {
            throw new IllegalArgumentException("incorrect end and start coordinates");
        }
        double[] xValues = new double[count];
        double[] yValues = new double[count];
        double intervalSplittingStep = (xTo - xFrom) / (count - 1);
        xValues[0] = xFrom;
        yValues[0] = source.apply(xFrom);
        xValues[count - 1] = xTo;
        yValues[count - 1] = source.apply(xTo);
        for (int element = 1; element < count - 1; element++) {
            xValues[element] = xValues[element - 1] + intervalSplittingStep;
            yValues[element] = source.apply(xValues[element]);
        }
        this.count = count;
        this.xValues = Arrays.copyOf(xValues, count);
        this.yValues = Arrays.copyOf(yValues, count);
    }

    @Override
    public int getCount() {
        return this.count;
    }

    @Override
    public double getX(int index) {
        if ((index < 0) | (index >= count)) {
            throw new IllegalArgumentException("The invalid index");
        }
        return xValues[index];
    }

    @Override
    public double getY(int index) {
        if ((index < 0) | (index >= count)) {
            throw new IllegalArgumentException("The invalid index");
        }
        return yValues[index];
    }

    @Override
    public void setY(int index, double value) {
        if ((index < 0) | (index >= count)) {
            throw new IllegalArgumentException("The invalid index");
        }
        yValues[index] = value;
    }

    @Override
    public double leftBound() {
        return xValues[0];
    }

    @Override
    public double rightBound() {
        return xValues[count - 1];
    }

    @Override
    public int indexOfX(double x) {
        for (int element = 0; element < this.count; element++) {
            if (xValues[element] == x) {
                return element;
            }
        }
        return -1;
    }

    @Override
    public int indexOfY(double y) {
        for (int element = 0; element < this.count; element++) {
            if (yValues[element] == y) {
                return element;
            }
        }
        return -1;
    }

    @Override
    public int floorIndexOfX(double x) {
        for (int element = 0; element < this.count; element++) {
            if (xValues[element] == x) {
                return element;
            }
        }
        for (int element = 0; element < this.count; element++) {
            if (x < xValues[element] && element != 0) {
                return element - 1;
            }
            if (x < xValues[element]) {
                throw new IllegalArgumentException("x is less than the left border");
            }
        }
        return this.count;
    }

    @Override
    public double extrapolateLeft(double x) {
        return super.interpolate(x, xValues[0], xValues[1], yValues[0], yValues[1]);
    }

    @Override
    public double extrapolateRight(double x) {
        return super.interpolate(x, xValues[count - 2], xValues[count - 1], yValues[count - 2], yValues[count - 1]);
    }

    @Override
    public double interpolate(double x, int floorIndex) {
        return super.interpolate(x, xValues[floorIndex], xValues[floorIndex + 1], yValues[floorIndex], yValues[floorIndex + 1]);
    }

    @Override
    public void insert(double x, double y) {
        if (indexOfX(x) != -1) { //если х найден в таблице
            setY(indexOfX(x), y);
        } else {
            if (xValues.length - count == 0) {
                double[] newXValues = new double[count + 5];
                double[] newYValues = new double[count + 5];
                System.arraycopy(xValues, 0, newXValues, 0, count);
                System.arraycopy(yValues, 0, newYValues, 0, count);
                xValues = newXValues;
                yValues = newYValues;
            }
            int index;
            if (x < leftBound()) { //х меньше левой границы
                index = 0;
            } else if (x > rightBound()) { //х больше правой границы
                index = count;
            } else { //х в интервале таблицы
                index = floorIndexOfX(x) + 1;
            }
            System.arraycopy(xValues, index, xValues, index + 1, count - index);
            System.arraycopy(yValues, index, yValues, index + 1, count - index);
            xValues[index] = x;
            yValues[index] = y;
            count++;
        }
    }

    @Override
    public void remove(int index) {
        if ((index < 0) | (index >= count)) {
            throw new IllegalArgumentException("The invalid index");
        }
        System.arraycopy(xValues, index + 1, xValues, index, (count - 1) - index);
        System.arraycopy(yValues, index + 1, yValues, index, (count - 1) - index);
        count--;
    }

    @Override
    public Iterator<Point> iterator() {
        return new Iterator<>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < count;
            }

            @Override
            public Point next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return new Point(xValues[index], yValues[index++]);
            }
        };
    }
}

