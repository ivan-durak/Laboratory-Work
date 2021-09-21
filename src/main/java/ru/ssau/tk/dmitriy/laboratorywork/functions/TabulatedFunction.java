package ru.ssau.tk.dmitriy.laboratorywork.functions;

public interface TabulatedFunction extends MathFunction {
    int getCountOfTabulatedValues();

    double getValueOfX(int index);

    double getValueOfY(int index);

    void setValueOfY(int index, double value);

    int indexOfX(double x);

    int indexOfY(double y);

    double leftBound();

    double rightBound();
}
