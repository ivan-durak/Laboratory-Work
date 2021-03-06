package ru.ssau.tk.dmitriy.laboratorywork.functions.factory;

import ru.ssau.tk.dmitriy.laboratorywork.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.dmitriy.laboratorywork.functions.MathFunction;
import ru.ssau.tk.dmitriy.laboratorywork.functions.TabulatedFunction;

public class LinkedListTabulatedFunctionFactory implements TabulatedFunctionFactory {

    @Override
    public TabulatedFunction create(double[] xValues, double[] yValues) {
        return new LinkedListTabulatedFunction(xValues, yValues);
    }

    @Override
    public TabulatedFunction create(MathFunction mathFunction, double xFrom, double xTo, int count) {
        return new LinkedListTabulatedFunction(mathFunction, xFrom, xTo, count);
    }
}
