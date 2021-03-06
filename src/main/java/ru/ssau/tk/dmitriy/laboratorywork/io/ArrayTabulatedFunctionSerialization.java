package ru.ssau.tk.dmitriy.laboratorywork.io;

import ru.ssau.tk.dmitriy.laboratorywork.functions.*;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.dmitriy.laboratorywork.operations.*;

import java.io.*;

public class ArrayTabulatedFunctionSerialization {

    public static void main(String[] args) {
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                new FileOutputStream("output/serialized array functions.bin"));
             BufferedInputStream bufferedInputStream = new BufferedInputStream(
                     new FileInputStream("output/serialized array functions.bin"))) {
            TabulatedFunction arrayTabulated = new ArrayTabulateFunction(new double[]{1, 2, 3, 4, 5}, new double[]{6, 7, 8, 9, 10});
            TabulatedDifferentialOperator tabulatedDifferentialOperator = new TabulatedDifferentialOperator(new ArrayTabulatedFunctionFactory());
            TabulatedFunction firstDerivative = tabulatedDifferentialOperator.derive(arrayTabulated);
            TabulatedFunction secondDerivative = tabulatedDifferentialOperator.derive(firstDerivative);
            FunctionsIO.serialize(bufferedOutputStream, arrayTabulated);
            FunctionsIO.serialize(bufferedOutputStream, firstDerivative);
            FunctionsIO.serialize(bufferedOutputStream, secondDerivative);

            System.out.println(FunctionsIO.deserialize(bufferedInputStream));
            System.out.println(FunctionsIO.deserialize(bufferedInputStream));
            System.out.println(FunctionsIO.deserialize(bufferedInputStream));
        } catch (ClassNotFoundException | IOException exception) {
            exception.printStackTrace();
        }
    }
}