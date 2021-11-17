package ru.ssau.tk.dmitriy.laboratorywork.exceptions;

import java.io.Serializable;

public class InconsistentFunctionsException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1846093761577285758L;

    public InconsistentFunctionsException() {
    }

    public InconsistentFunctionsException(String string) {
        super(string);
    }
}
