package ru.ssau.tk.dmitriy.laboratorywork.ui;

import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.LinkedListTabulatedFunctionFactory;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.TabulatedFunctionFactory;


import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.function.Consumer;
import java.io.IOException;

public class SettingsWindow extends JDialog {
    private final JLabel title = new JLabel("<html>Хранение функции<br> на основе: ");

    private final ButtonGroup buttonGroup = new ButtonGroup();
    private final JRadioButton arrayButton = new JRadioButton("Массива");
    private final JRadioButton listButton = new JRadioButton("Двусвязного списка");

    private final JButton confirmationButton = new JButton("Сохранить");

    private final HashMap<JRadioButton, TabulatedFunctionFactory> chooseFactory = new HashMap<>();

    public SettingsWindow(Consumer<? super TabulatedFunctionFactory> callback) {
        super();
        setTitle("Настройки");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);
        compose();
        addButtonListener(callback);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void compose() {
        Container container = getContentPane();
        GroupLayout groupLayout = new GroupLayout(container);
        container.setLayout(groupLayout);
        setSize(300, 150);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        fillMap();
        fillButtonGroup();
        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(title)
                                .addComponent(arrayButton)
                                .addComponent(listButton))
                        .addComponent(confirmationButton));
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(title)
                                .addComponent(arrayButton)
                                .addComponent(listButton))
                        .addComponent(confirmationButton));
    }

    private void addButtonListener(Consumer<? super TabulatedFunctionFactory> callback) {
        confirmationButton.addActionListener(event -> {
            callback.accept(chooseFactory.get(buttonGroup.getSelection()));
            this.dispose();
        });
    }

    private void fillMap() {
        chooseFactory.put(arrayButton, new ArrayTabulatedFunctionFactory());
        chooseFactory.put(listButton, new LinkedListTabulatedFunctionFactory());
    }

    private void fillButtonGroup() {
        buttonGroup.add(arrayButton);
        arrayButton.setSelected(true);
        buttonGroup.add(listButton);
    }
    public static void main(TabulatedFunctionFactory factory) throws IOException {
        SettingsWindow frame = new SettingsWindow((Consumer<? super TabulatedFunctionFactory>) factory);
        frame.setVisible(true);
    }
}
