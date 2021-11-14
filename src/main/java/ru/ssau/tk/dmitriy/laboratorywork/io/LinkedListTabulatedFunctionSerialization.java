package ru.ssau.tk.dmitriy.laboratorywork.io;

import ru.ssau.tk.dmitriy.laboratorywork.functions.TabulatedFunction;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.LinkedListTabulatedFunctionFactory;
import ru.ssau.tk.dmitriy.laboratorywork.operations.TabulatedDifferentialOperator;

import java.io.*;

public class LinkedListTabulatedFunctionSerialization {
    public static void main(String[] args) {
        try (BufferedOutputStream bufferedOutput = new BufferedOutputStream(
                new FileOutputStream("output/serialized linked list functions.bin"))) {
            TabulatedFunction function = new LinkedListTabulatedFunctionFactory().create(
                    new double[]{1, 2, 3, 4, 5}, new double[]{3, 6, 9, 12, 15});
            TabulatedFunction firstDerivative = new TabulatedDifferentialOperator().derive(function);
            TabulatedFunction secondDerivative = new TabulatedDifferentialOperator().derive(firstDerivative);
            FunctionsIO.serialize(bufferedOutput, function);
            FunctionsIO.serialize(bufferedOutput, firstDerivative);
            FunctionsIO.serialize(bufferedOutput, secondDerivative);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedInputStream bufferedInput = new BufferedInputStream(new FileInputStream("output/serialized linked list functions.bin"));
            TabulatedFunction firstFunction = FunctionsIO.deserialize(bufferedInput);
            TabulatedFunction secondFunction = FunctionsIO.deserialize(bufferedInput);
            TabulatedFunction thirdFunction = FunctionsIO.deserialize(bufferedInput);
            System.out.println(firstFunction.toString());
            System.out.println(secondFunction.toString());
            System.out.println(thirdFunction.toString());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
