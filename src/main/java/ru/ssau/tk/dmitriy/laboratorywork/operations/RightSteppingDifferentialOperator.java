package ru.ssau.tk.dmitriy.laboratorywork.operations;

import ru.ssau.tk.dmitriy.laboratorywork.functions.MathFunction;

public class RightSteppingDifferentialOperator extends SteppingDifferentialOperator {

    public RightSteppingDifferentialOperator(double step) {
        super(step);
    }

    @Override
    public MathFunction derive(MathFunction function) {
        return new MathFunction() {

            @Override
            public double apply(double x) {
                return (function.apply(x + step) - function.apply(x)) / step;
            }

        };
    }
}