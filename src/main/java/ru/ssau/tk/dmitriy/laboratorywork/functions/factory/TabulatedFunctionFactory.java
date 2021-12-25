package ru.ssau.tk.dmitriy.laboratorywork.functions.factory;

import ru.ssau.tk.dmitriy.laboratorywork.functions.MathFunction;
import ru.ssau.tk.dmitriy.laboratorywork.functions.StrictTabulatedFunction;
import ru.ssau.tk.dmitriy.laboratorywork.functions.TabulatedFunction;

public interface TabulatedFunctionFactory {
    TabulatedFunction create(double[] xValues, double[] yValues);

    TabulatedFunction create(MathFunction mathFunction, double xFrom, double xTo, int count);

    default TabulatedFunction createStrict(double[] xValues, double[] yValues) {
        return new StrictTabulatedFunction(create(xValues, yValues));
    }
}