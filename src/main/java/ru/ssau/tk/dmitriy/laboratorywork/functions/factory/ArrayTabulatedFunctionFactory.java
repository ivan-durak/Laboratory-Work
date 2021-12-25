package ru.ssau.tk.dmitriy.laboratorywork.functions.factory;

import ru.ssau.tk.dmitriy.laboratorywork.functions.ArrayTabulateFunction;
import ru.ssau.tk.dmitriy.laboratorywork.functions.MathFunction;
import ru.ssau.tk.dmitriy.laboratorywork.functions.TabulatedFunction;

public class ArrayTabulatedFunctionFactory implements TabulatedFunctionFactory {

    @Override
    public TabulatedFunction create(double[] xValues, double[] yValues) {
        return new ArrayTabulateFunction(xValues, yValues);
    }

    @Override
    public TabulatedFunction create(MathFunction mathFunction, double xFrom, double xTo, int count) {
        return new ArrayTabulateFunction(mathFunction, xFrom, xTo, count);
    }
}
