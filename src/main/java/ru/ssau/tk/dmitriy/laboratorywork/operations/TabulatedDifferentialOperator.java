package ru.ssau.tk.dmitriy.laboratorywork.operations;
import ru.ssau.tk.dmitriy.laboratorywork.concurrent.SynchronizedTabulatedFunction;
import ru.ssau.tk.dmitriy.laboratorywork.functions.Point;
import ru.ssau.tk.dmitriy.laboratorywork.functions.TabulatedFunction;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.TabulatedFunctionFactory;

public class TabulatedDifferentialOperator implements DifferentialOperator<TabulatedFunction> {
    private TabulatedFunctionFactory factory;

    public TabulatedDifferentialOperator() {
        this.factory = new ArrayTabulatedFunctionFactory();
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
        Point[] points = TabulatedFunctionOperationService.asPoints(function);
        double[] xValues = new double[points.length];
        double[] yValues = new double[points.length];
        for (int element = 0; element < points.length - 1; element++) {
            xValues[element] = points[element].x;
            yValues[element] = (points[element + 1].y - points[element].y) / (points[element + 1].x - points[element].x);
        }
        yValues[points.length - 1] = yValues[points.length - 2];
        xValues[points.length - 1] = points[points.length - 1].x;
        return factory.create(xValues, yValues);
    }
    public TabulatedFunction deriveSynchronously(TabulatedFunction function) {
        Object object = new Object();

        if (function instanceof SynchronizedTabulatedFunction) {
            return ((SynchronizedTabulatedFunction) function).doSynchronously(this::derive);
        }
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = new SynchronizedTabulatedFunction(function, object);
        return synchronizedTabulatedFunction.doSynchronously(this::derive);
    }
}
