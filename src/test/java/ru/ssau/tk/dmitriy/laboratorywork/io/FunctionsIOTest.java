package ru.ssau.tk.dmitriy.laboratorywork.io;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import ru.ssau.tk.dmitriy.laboratorywork.functions.*;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.*;

import java.io.*;

public class FunctionsIOTest {
    public TabulatedFunction arrayTabulated = new ArrayTabulateFunction(new double[]{1, 2, 3, 4, 5.5, 6.9}, new double[]{7, 13, 15, 20, 25, 32});

    @Test
    public void testCharacterStreams() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("temp/test character streams.txt"));
        FunctionsIO.writeTabulatedFunction(bufferedWriter, arrayTabulated);
        BufferedReader bufferedReader = new BufferedReader(new FileReader("temp/test character streams.txt"));
        TabulatedFunction listTabulated = FunctionsIO.readTabulatedFunction(bufferedReader, new LinkedListTabulatedFunctionFactory());
        Assert.assertEquals(listTabulated.getCount(), arrayTabulated.getCount());
        for (int index = 0; index < listTabulated.getCount(); index++) {
            Assert.assertEquals(listTabulated.getX(index), arrayTabulated.getX(index));
            Assert.assertEquals(listTabulated.getY(index), arrayTabulated.getY(index));
        }
        Assert.assertThrows(FileNotFoundException.class, () -> new FileReader("temp/test.txt"));
    }

    @Test
    public void testByteStreams() throws IOException {
        TabulatedFunction tabulatedFunction = new ArrayTabulateFunction(new double[]{2, 3, 5, 7, 9}, new double[]{5, 8, 17, 21, 15});
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("temp/test byte streams.bin"));
        FunctionsIO.writeTabulatedFunction(bufferedOutputStream, tabulatedFunction);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("temp/test byte streams.bin"));
        TabulatedFunction listTabulated = FunctionsIO.readTabulatedFunction(bufferedInputStream, new LinkedListTabulatedFunctionFactory());
        Assert.assertEquals(listTabulated.getCount(), tabulatedFunction.getCount());
        for (int index = 0; index < listTabulated.getCount(); index++) {
            Assert.assertEquals(listTabulated.getX(index), tabulatedFunction.getX(index));
            Assert.assertEquals(listTabulated.getY(index), tabulatedFunction.getY(index));
        }
    }

    @Test
    public void testSerializeDeserialize() throws IOException, ClassNotFoundException {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("temp/test serialize.bin"));
        FunctionsIO.serialize(bufferedOutputStream, arrayTabulated);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("temp/test serialize.bin"));
        TabulatedFunction listTabulated = FunctionsIO.deserialize(bufferedInputStream);
        Assert.assertEquals(listTabulated.getCount(), arrayTabulated.getCount());
        for (int index = 0; index < listTabulated.getCount(); index++) {
            Assert.assertEquals(listTabulated.getX(index), arrayTabulated.getX(index));
            Assert.assertEquals(listTabulated.getY(index), arrayTabulated.getY(index));
        }
        Assert.assertThrows(EOFException.class, () -> FunctionsIO.deserialize(bufferedInputStream));
    }

    @Test
    public void testSerializeDeserializeJson() throws IOException {
        ArrayTabulateFunction arrayTabulateFunction = new ArrayTabulateFunction(new double[]{1, 2, 3, 4, 5}, new double[]{6, 12, 18, 24, 30});
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("temp/test serialize Json.Json"));
        BufferedReader bufferedReader = new BufferedReader(new FileReader("temp/test serialize Json.Json"));
        FunctionsIO.serializeJson(bufferedWriter, arrayTabulateFunction);
        ArrayTabulateFunction deserializedArray = FunctionsIO.deserializeJson(bufferedReader);
        int i = 0;
        Assert.assertEquals(deserializedArray.getCount(), arrayTabulateFunction.getCount());
        for (Point point : deserializedArray) {
            Assert.assertEquals(point.x, arrayTabulateFunction.getX(i));
            Assert.assertEquals(point.y, arrayTabulateFunction.getY(i++));
        }
    }

    @AfterClass
    public void deleteAllFilesFolder() {
        File[] files = new File("temp").listFiles();
        if (files != null) {
            for (File currentFile : files) {
                if (currentFile.isFile() && currentFile.delete()) {
                    System.out.println("Done!");
                }
            }
        }
    }
}