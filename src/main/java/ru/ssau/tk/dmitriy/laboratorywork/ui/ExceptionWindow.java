package ru.ssau.tk.dmitriy.laboratorywork.ui;

import ru.ssau.tk.dmitriy.laboratorywork.exceptions.ArrayIsNotSortedException;
import ru.ssau.tk.dmitriy.laboratorywork.exceptions.DifferentLengthOfArraysException;
import ru.ssau.tk.dmitriy.laboratorywork.exceptions.InconsistentFunctionsException;

import javax.swing.*;
import java.awt.*;

public class ExceptionWindow {

    public ExceptionWindow(Component parentComponent, Exception exception) {
        JOptionPane.showMessageDialog(parentComponent, getMessageException(exception));
    }

    private String getMessageException(Exception exception) {
        if (exception instanceof NumberFormatException) {
            return "<html>Строка или пустая,<br> или не может быть приведена к числу";
        } else if (exception instanceof DifferentLengthOfArraysException) {
            return "Разная длина массивов";
        } else if (exception instanceof ArrayIsNotSortedException) {
            return "Массив не отсортирован";
        } else if (exception instanceof InconsistentFunctionsException) {
            return "<html>Разное количество<br> точек у функций";
        }
        return "Извините, что-то пошло не так";
    }
}
