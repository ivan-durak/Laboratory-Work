package ru.ssau.tk.dmitriy.laboratorywork.io;

import ru.ssau.tk.dmitriy.laboratorywork.functions.ArrayTabulateFunction;

import java.io.*;

public class ArrayTabulatedJsonSerialization {
    public static void main(String[] args) {
        ArrayTabulateFunction arrayTabulateFunction = new ArrayTabulateFunction(new double[]{1, 2, 3, 4, 5}, new double[]{5, 10, 15, 20, 25});
        try (BufferedWriter bufferedOutput = new BufferedWriter(new FileWriter("output/serialized json array fun-on.Json"));
             BufferedReader bufferedInput = new BufferedReader(new FileReader("output/serialized json array fun-on.Json"))) {
            FunctionsIO.serializeJson(bufferedOutput, arrayTabulateFunction);
            System.out.println(FunctionsIO.deserializeJson(bufferedInput).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
