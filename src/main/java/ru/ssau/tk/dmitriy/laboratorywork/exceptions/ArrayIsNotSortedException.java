package ru.ssau.tk.dmitriy.laboratorywork.exceptions;

public class ArrayIsNotSortedException extends RuntimeException {

    public ArrayIsNotSortedException() {
    }

    public ArrayIsNotSortedException(String string) {
        super(string);
    }
}
