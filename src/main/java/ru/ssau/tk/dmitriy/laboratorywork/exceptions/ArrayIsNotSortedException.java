package ru.ssau.tk.dmitriy.laboratorywork.exceptions;

import java.io.Serializable;

public class ArrayIsNotSortedException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -2720406117818180115L;

    public ArrayIsNotSortedException() {
    }

    public ArrayIsNotSortedException(String string) {
        super(string);
    }
}
