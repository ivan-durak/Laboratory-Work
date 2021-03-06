package ru.ssau.tk.dmitriy.laboratorywork.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.ssau.tk.dmitriy.laboratorywork.functions.*;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.*;

import java.io.*;
import java.text.*;
import java.util.*;

public final class FunctionsIO {

    private FunctionsIO() {
        throw new UnsupportedOperationException();
    }

    public static void writeTabulatedFunction(BufferedWriter writer, TabulatedFunction function) {
        PrintWriter printWriter = new PrintWriter(writer);
        printWriter.println(function.getCount());
        for (Point point : function) {
            printWriter.printf("%f %f\n", point.x, point.y);
        }
        printWriter.flush();
    }

    public static void writeTabulatedFunction(BufferedOutputStream outputStream, TabulatedFunction function) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(function.getCount());
        for (Point point : function) {
            dataOutputStream.writeDouble(point.x);
            dataOutputStream.writeDouble(point.y);
        }
        dataOutputStream.flush();
    }

    public static TabulatedFunction readTabulatedFunction(BufferedReader reader, TabulatedFunctionFactory factory) throws IOException {
        int count = Integer.parseInt(reader.readLine());
        double[] xValues = new double[count];
        double[] yValues = new double[count];
        NumberFormat numberFormatter = NumberFormat.getInstance(Locale.forLanguageTag("ru"));
        String line;
        try {
            for (int index = 0; index < count; index++) {
                line = reader.readLine();
                xValues[index] = numberFormatter.parse(line.split(" ")[0]).doubleValue();
                yValues[index] = numberFormatter.parse(line.split(" ")[1]).doubleValue();
            }
        } catch (ParseException exception) {
            throw new IOException(exception);
        }
        return factory.create(xValues, yValues);
    }

    public static TabulatedFunction readTabulatedFunction(BufferedInputStream inputStream, TabulatedFunctionFactory factory) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        int count = dataInputStream.readInt();
        double[] xValues = new double[count];
        double[] yValues = new double[count];
        for (int index = 0; index < count; index++) {
            xValues[index] = dataInputStream.readDouble();
            yValues[index] = dataInputStream.readDouble();
        }
        return factory.create(xValues, yValues);
    }

    public static void serialize(BufferedOutputStream stream, TabulatedFunction function) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(stream);
        objectOutputStream.writeObject(function);
        objectOutputStream.flush();
    }

    public static TabulatedFunction deserialize(BufferedInputStream stream) throws IOException, ClassNotFoundException {
        return (TabulatedFunction) new ObjectInputStream(stream).readObject();
    }

    public static void serializeJson(BufferedWriter writer, ArrayTabulateFunction function) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        writer.write(objectMapper.writeValueAsString(function));
        writer.flush();
    }

    public static ArrayTabulateFunction deserializeJson(BufferedReader reader) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readerFor(ArrayTabulateFunction.class).readValue(reader);
    }
}
