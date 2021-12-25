package ru.ssau.tk.dmitriy.laboratorywork.functions;

public class ExponentFunction implements MathFunction {
    @Override
    public double apply(double x) {
        return Math.exp(x);
    }
}
