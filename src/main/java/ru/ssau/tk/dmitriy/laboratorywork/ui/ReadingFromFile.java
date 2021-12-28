package ru.ssau.tk.dmitriy.laboratorywork.ui;

import ru.ssau.tk.dmitriy.laboratorywork.functions.TabulatedFunction;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.TabulatedFunctionFactory;
import ru.ssau.tk.dmitriy.laboratorywork.io.FunctionsIO;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.function.Consumer;

public class ReadingFromFile extends JDialog {
    private TabulatedFunctionFactory factory;


    public ReadingFromFile(TabulatedFunctionFactory factory, Consumer<? super TabulatedFunction> callback) {
        setModal(true);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.addChoosableFileFilter(
                new FileNameExtensionFilter("Bin files", "bin"));
        fileChooser.setAcceptAllFileFilterUsed(false);
        int condition = fileChooser.showOpenDialog(this);
        if (condition == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (file != null) {
                try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
                    TabulatedFunction function = FunctionsIO.readTabulatedFunction(inputStream, factory);
                    callback.accept(function);
                } catch (Exception exception) {
                    new ExceptionWindow(this, exception);
                }
            }
        }
    }

    public ReadingFromFile(Consumer<? super TabulatedFunction> callback) {
    }

    public static void main(Consumer<? super TabulatedFunction> callback) {
        new ReadingFromFile(callback);
    }
}
