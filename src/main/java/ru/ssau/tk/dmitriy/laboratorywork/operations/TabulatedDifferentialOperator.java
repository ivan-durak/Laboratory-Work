package ru.ssau.tk.dmitriy.laboratorywork.operations;

import ru.ssau.tk.dmitriy.laboratorywork.functions.Point;
import ru.ssau.tk.dmitriy.laboratorywork.functions.TabulatedFunction;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.TabulatedFunctionFactory;

public class TabulatedDifferentialOperator implements DifferentialOperator<TabulatedFunction> {
    TabulatedFunctionFactory factory;

    public TabulatedDifferentialOperator() {
        factory = new ArrayTabulatedFunctionFactory();
    }

    public TabulatedDifferentialOperator(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }

    public TabulatedFunctionFactory getFactory() {
        return factory;
    }

    public void setFactory(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }

    @Override
    public TabulatedFunction derive(TabulatedFunction function) {
        double[] xValues = new double[function.getCount()],//
                yValues = new double[function.getCount()];
        Point[] points = TabulatedFunctionOperationService.asPoints(function);
        for (int i = 0; i < xValues.length; i++) {
            xValues[i] = points[i].x;
            yValues[i] = (i == xValues.length - 1 ? yValues[i - 1] : (points[i + 1].y - points[i].y) / (points[i + 1].x - points[i].x));
        }
        return factory.create(xValues, yValues);
    }
}
