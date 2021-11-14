package ru.ssau.tk.dmitriy.laboratorywork.io;

import ru.ssau.tk.dmitriy.laboratorywork.functions.TabulatedFunction;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.LinkedListTabulatedFunctionFactory;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TabulatedFunctionFileOutputStream {
    public static void main(String[] args) {
        try (BufferedOutputStream arrayFile = new BufferedOutputStream(new FileOutputStream("output/array function.bin"));
             BufferedOutputStream listFile = new BufferedOutputStream(new FileOutputStream("output/linked list function.bin"))) {
            ArrayTabulatedFunctionFactory arrayFactory = new ArrayTabulatedFunctionFactory();
            LinkedListTabulatedFunctionFactory listFactory = new LinkedListTabulatedFunctionFactory();
            TabulatedFunction firstTabulatedFunction = arrayFactory.create(new double[]{1, 2, 3, 4, 5}, new double[]{4, 8, 12, 16, 20});
            TabulatedFunction secondTabulatedFunction = listFactory.create(new double[]{1, 2, 3, 4, 5}, new double[]{4, 8, 12, 16, 20});
            FunctionsIO.writeTabulatedFunction(arrayFile, firstTabulatedFunction);
            FunctionsIO.writeTabulatedFunction(listFile, secondTabulatedFunction);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
