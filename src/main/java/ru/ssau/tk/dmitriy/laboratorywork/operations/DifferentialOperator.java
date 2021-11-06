package ru.ssau.tk.dmitriy.laboratorywork.operations;

import ru.ssau.tk.dmitriy.laboratorywork.functions.*;

public interface DifferentialOperator<T extends MathFunction> {

    T derive(T function);
}