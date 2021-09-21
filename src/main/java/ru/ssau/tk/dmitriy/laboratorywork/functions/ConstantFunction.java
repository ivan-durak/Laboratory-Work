package ru.ssau.tk.dmitriy.laboratorywork.functions;

public class ConstantFunction implements MathFunction {
    private final double constant;

    public ConstantFunction(double theConstant) {
        constant = theConstant;
    }

    public double getConstant() {
        return constant;
    }

    @Override
    public double apply(double x) {
        return constant;
    }
}
