package ru.ssau.tk.dmitriy.laboratorywork.functions;

public class CubeFunction implements MathFunction {
    @Override
    public double apply(double x) {
        return x * x * x;
    }
}
