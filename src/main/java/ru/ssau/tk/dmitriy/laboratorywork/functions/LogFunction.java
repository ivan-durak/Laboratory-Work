package ru.ssau.tk.dmitriy.laboratorywork.functions;

public class LogFunction implements MathFunction {
    @Override
    public double apply(double x) {
        return Math.log(x);
    }
}
