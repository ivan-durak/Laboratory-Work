package ru.ssau.tk.dmitriy.laboratorywork.ui;

import ru.ssau.tk.dmitriy.laboratorywork.functions.TabulatedFunction;
import ru.ssau.tk.dmitriy.laboratorywork.io.FunctionsIO;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class WritingToFile extends JDialog {

    public WritingToFile(TabulatedFunction function) {
        setModal(true);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.addChoosableFileFilter(
                new FileNameExtensionFilter("Bin files", "bin"));
        fileChooser.setAcceptAllFileFilterUsed(false);
        int condition = fileChooser.showSaveDialog(this);
        if (condition == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (file != null) {
                try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
                    FunctionsIO.writeTabulatedFunction(outputStream, function);
                } catch (Exception exception) {
                    new ExceptionWindow(this, exception);
                }
            }
        }
    }
}
