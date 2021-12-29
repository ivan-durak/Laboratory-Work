package ru.ssau.tk.dmitriy.laboratorywork.ui;

import ru.ssau.tk.dmitriy.laboratorywork.functions.TabulatedFunction;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.TabulatedFunctionFactory;
import ru.ssau.tk.dmitriy.laboratorywork.operations.TabulatedDifferentialOperator;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DifferentiationWindow extends JFrame {
    private final TabulatedFunctionFactory factory;
    private TabulatedFunction sourceFunction;

    private final JLabel originalFunction = new JLabel("<html>Исходная<br>функция:");
    private final JLabel titleOverDerive = new JLabel("Вычислить");
    private final JLabel differentiationResult = new JLabel("<html>Результат<br>дифференцирования:");

    private final JButton createOriginalButton = new JButton("Создать");
    private final JButton saveOriginalButton = new JButton("Сохранить");
    private final JButton uploadOriginalButton = new JButton("Загрузить");
    private final BasicArrowButton deriveButton = new BasicArrowButton(BasicArrowButton.EAST);
    private final JButton saveDeriveButton = new JButton("Сохранить");

    private final PartEditable sourceTableFunction = new PartEditable();
    private final NotMutable deriveTableFunction = new NotMutable();

    private final JTable sourceFunctionTable = new JTable(sourceTableFunction);
    private final JTable deriveFunctionTable = new JTable(deriveTableFunction);

    private final JScrollPane sourceFunctionScroll = new JScrollPane(sourceFunctionTable);
    private final JScrollPane deriveFunctionScroll = new JScrollPane(deriveFunctionTable);

    public DifferentiationWindow(TabulatedFunctionFactory factory) {
        super();
        setTitle("Окно дифференцирования");
        this.factory = factory;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Container container = getContentPane();
        container.add(originalFunction);
        container.add(titleOverDerive);
        container.add(differentiationResult);
        container.add(createOriginalButton);
        container.add(saveOriginalButton);
        container.add(uploadOriginalButton);
        container.add(deriveButton);
        container.add(saveDeriveButton);
        container.add(sourceFunctionScroll);
        container.add(deriveFunctionScroll);
        compose();
        addButtonsListeners();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void compose() {
        Container container = getContentPane();
        GroupLayout groupLayout = new GroupLayout(container);
        container.setLayout(groupLayout);
        setSize(700, 500);
        sourceFunctionTable.setSize(180, 350);
        deriveFunctionTable.setSize(180, 350);
        deriveButton.setSize(50, 150);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(originalFunction)
                                .addComponent(sourceFunctionScroll)
                                .addComponent(createOriginalButton)
                                .addComponent(saveOriginalButton)
                                .addComponent(uploadOriginalButton))
                        .addGap(25)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(titleOverDerive)
                                .addComponent(deriveButton))
                        .addGap(25)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(differentiationResult)
                                .addComponent(deriveFunctionScroll)
                                .addComponent(saveDeriveButton)));
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(originalFunction)
                                .addComponent(sourceFunctionScroll)
                                .addComponent(createOriginalButton)
                                .addComponent(saveOriginalButton)
                                .addComponent(uploadOriginalButton))
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(16)
                                .addComponent(titleOverDerive)
                                .addGap(140)
                                .addComponent(deriveButton)
                                .addGap(190))
                        .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(differentiationResult)
                                .addComponent(deriveFunctionScroll)
                                .addComponent(saveDeriveButton)
                                .addGap(64)));
    }

    private void addButtonsListeners() {

        createOriginalButton.addActionListener(event -> {
            JLabel message = new JLabel("<html>Выберете способ создания<br>(Введите \"массив\" или \"функция\")");
            String realization = JOptionPane.showInputDialog(this, message);
            switch (realization) {
                case ("массив"): {
                    new TabulatedTableWindow(factory, function -> {
                        sourceFunction = function;
                        sourceTableFunction.setFunction(sourceFunction);
                    });
                    break;
                }
                case ("функция"): {
                    new BasicMathFunctionWindow(factory, function -> {
                        sourceFunction = function;
                        sourceTableFunction.setFunction(sourceFunction);
                    });
                    break;
                }
            }
            sourceTableFunction.fireTableDataChanged();
        });

        saveOriginalButton.addActionListener(event -> {
            new WritingToFile(sourceFunction);
        });

        uploadOriginalButton.addActionListener(event -> {
            new ReadingFromFile(factory, function -> {
                sourceFunction = function;
                sourceTableFunction.setFunction(sourceFunction);
            });
            sourceTableFunction.fireTableDataChanged();
        });

        deriveButton.addActionListener(event -> {
            deriveTableFunction.setFunction(new TabulatedDifferentialOperator().derive(sourceFunction));
            deriveTableFunction.fireTableDataChanged();
        });

        saveDeriveButton.addActionListener(event -> new WritingToFile(deriveTableFunction.getFunction()));
    }
}
