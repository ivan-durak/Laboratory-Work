package ru.ssau.tk.dmitriy.laboratorywork.io;

import ru.ssau.tk.dmitriy.laboratorywork.functions.TabulatedFunction;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.LinkedListTabulatedFunctionFactory;
import ru.ssau.tk.dmitriy.laboratorywork.operations.TabulatedDifferentialOperator;

import java.io.*;

public class TabulatedFunctionFileInputStream {
    public static void main(String[] args) {
        try (BufferedInputStream fileInput = new BufferedInputStream(new FileInputStream("input/binary function.bin"))) {
            TabulatedFunction tabulatedFunction = FunctionsIO.readTabulatedFunction(fileInput, new ArrayTabulatedFunctionFactory());
            System.out.println(tabulatedFunction.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Введите размер и значения функции");
            TabulatedFunction tabulatedFunction = FunctionsIO.readTabulatedFunction(inputReader, new LinkedListTabulatedFunctionFactory());
            System.out.println((new TabulatedDifferentialOperator().derive(tabulatedFunction)).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
