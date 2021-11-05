package ru.ssau.tk.dmitriy.laboratorywork.functions.factory;

import ru.ssau.tk.dmitriy.laboratorywork.functions.TabulatedFunction;

public interface TabulatedFunctionFactory {
    TabulatedFunction create(double[] xValues, double[] yValues);
}