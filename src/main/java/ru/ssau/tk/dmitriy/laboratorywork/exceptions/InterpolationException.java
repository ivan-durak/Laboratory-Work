package ru.ssau.tk.dmitriy.laboratorywork.exceptions;

import java.io.Serializable;

public class InterpolationException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -7240796741303596083L;

    public InterpolationException() {
    }

    public InterpolationException(String string) {
        super(string);
    }
}