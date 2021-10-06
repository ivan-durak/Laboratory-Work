package ru.ssau.tk.dmitriy.laboratorywork.functions;

import java.util.Arrays;

public class ArrayTabulateFunction extends AbstractTabulatedFunction implements Insertable, Removable {

    private double[] xValues;
    private double[] yValues;
    private int count = 0;
    
    public ArrayTabulateFunction(double[] xValues, double[] yValues) {

        count = xValues.length;
        this.xValues = Arrays.copyOf(xValues, count);
        this.yValues = Arrays.copyOf(yValues, count);
    }

    public ArrayTabulateFunction(MathFunction source, double xFrom, double xTo, int count) {
        double intervalSplittingStep = (xTo - xFrom) / count;
        double[] xValues = new double[count];
        double[] yValues = new double[count];
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
        return xValues[index];
    }

    @Override
    public double getY(int index) {
        return yValues[index];
    }

    @Override
    public void setY(int index, double value) {
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
            if (Math.abs(xValues[element] - x) < 0.0001) return element;
        }
        return -1;
    }

    @Override
    public int indexOfY(double y) {
        for (int element = 0; element < this.count; element++) {
            if (Math.abs(yValues[element] - y) < 0.0001) return element;
        }
        return -1;
    }

    @Override
    public int floorIndexOfX(double x) {
        for (int element = 0; element < this.count; element++) {
            if (Math.abs(xValues[element] - x) < 0.0001) return element;
        }
        for (int element = 0; element < this.count; element++) {
            if (x < xValues[element] && element != 0) return element - 1;
            if (x < xValues[element]) return 0;
        }
        return this.count;
    }

    @Override
    public double extrapolateLeft(double x) {
        return yValues[0] + (yValues[1] - yValues[0]) * (x - xValues[0]) / (xValues[1] - xValues[0]);
    }

    @Override
    public double extrapolateRight(double x) {
        return yValues[count - 2] + ((yValues[count - 1] - yValues[count - 2]) * (x - xValues[count - 2])) / (xValues[count - 1] - xValues[count - 2]);
    }

    @Override
    public double interpolate(double x, int floorIndex) {
        return yValues[floorIndex] + (yValues[floorIndex + 1] - yValues[floorIndex]) * (x - xValues[floorIndex]) / (xValues[floorIndex + 1] - xValues[floorIndex]);
    }

    @Override
    public void insert(double x, double y) {
        if (indexOfX(x) != -1) { //если х найден в таблице
            setY(indexOfX(x), y);//устанавливаем его значение
        } else {                 //если не найден
            if (xValues.length - count == 0) {
                double[] newXValues = new double[count + 5];
                double[] newYValues = new double[count + 5];
                System.arraycopy(xValues, 0, newXValues, 0, count);
                System.arraycopy(yValues, 0, newYValues, 0, count);
                xValues = newXValues;
                yValues = newYValues;
            }
            if (x < leftBound()) { //х меньше левой границы
                double help = extrapolateLeft(x);//если мы сначала скопируем, то там будет 0 и получится неправильное значение
                System.arraycopy(xValues, 0, xValues, 1, count);
                System.arraycopy(yValues, 0, yValues, 1, count);
                xValues[0] = x;
                yValues[0] = help;
                count++;
                return;
            }
            if (x > rightBound()) {//х больше правой границы
                xValues[count] = x;
                yValues[count] = extrapolateRight(x);
                count++;
                return;
            }  //дальше код, который выполнится при условии, что есть еще не занятые ячейки в массиве и х находится
            int nearestIndex = floorIndexOfX(x);  //между табличными значениями х
            double help = interpolate(x, nearestIndex);
            System.arraycopy(xValues, nearestIndex + 1, xValues, nearestIndex + 2, (count - 1) - nearestIndex);
            System.arraycopy(yValues, nearestIndex + 1, yValues, nearestIndex + 2, (count - 1) - nearestIndex);
            xValues[nearestIndex + 1] = x;
            yValues[nearestIndex + 1] = help;
            count++;
        }
    }

    @Override
    public void remove(int index) {
        System.arraycopy(xValues, index + 1, xValues, index, (count - 1) - index);
        System.arraycopy(yValues, index + 1, yValues, index, (count - 1) - index);
        count--;
    }
}

