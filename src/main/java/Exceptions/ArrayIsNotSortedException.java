package Exceptions;

public class ArrayIsNotSortedException extends RuntimeException {

    public ArrayIsNotSortedException() {
    }

    public ArrayIsNotSortedException(String string) {
        super(string);
    }
}
