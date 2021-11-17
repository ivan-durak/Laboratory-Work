package ru.ssau.tk.dmitriy.laboratorywork.exceptions;

import java.io.Serializable;

public class DifferentLengthOfArraysException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 5598348619042613728L;

    public DifferentLengthOfArraysException() {
    }

    public DifferentLengthOfArraysException(String string) {
        super(string);
    }
}