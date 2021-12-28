package ru.ssau.tk.dmitriy.laboratorywork.ui;

import ru.ssau.tk.dmitriy.laboratorywork.functions.TabulatedFunction;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.TabulatedFunctionFactory;
import ru.ssau.tk.dmitriy.laboratorywork.operations.TabulatedDifferentialOperator;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;

public class DifferentiationWindow extends JFrame {
    private final TabulatedFunctionFactory factory;
    private TabulatedFunction sourceTableFunction;
    private TabulatedFunction deriveTableFunction;

    private final JLabel originalFunction = new JLabel("<html>Исходная<br>функция:");
    private final JLabel titleOverDerive = new JLabel("Вычислить");
    private final JLabel differentiationResult = new JLabel("<html>Результат<br>дифференцирования:");

    private final JButton createOriginalButton = new JButton("Создать");
    private final JButton saveOriginalButton = new JButton("Сохранить");
    private final JButton uploadOriginalButton = new JButton("Загрузить");
    private final BasicArrowButton deriveButton = new BasicArrowButton(BasicArrowButton.EAST);
    private final JButton returnInMainWindowButton = new JButton("Вернуть в главное окно");
    private final JButton saveDeriveButton = new JButton("Сохранить");


    private final PartEditable sourceFunction = new PartEditable();
    private final NotMutable deriveFunction = new NotMutable();

    private final JTable sourceFunctionTable = new JTable(sourceFunction);
    private final JTable deriveFunctionTable = new JTable(deriveFunction);

    public DifferentiationWindow(TabulatedFunctionFactory factory/*, Consumer<? super TabulatedFunction> callback*/) {
        super();
        setTitle("Окно дифференцирования");
        this.factory = factory;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        compose();
        addButtonsListeners();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void compose() {
        Container container = getContentPane();
        GroupLayout groupLayout = new GroupLayout(container);
        container.setLayout(groupLayout);
        setSize(700, 500);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(originalFunction)
                                .addComponent(sourceFunctionTable)
                                .addComponent(createOriginalButton)
                                .addComponent(saveOriginalButton)
                                .addComponent(uploadOriginalButton))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(titleOverDerive)
                                .addComponent(deriveButton))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(differentiationResult)
                                .addComponent(deriveFunctionTable)
                                .addComponent(saveDeriveButton)
                                .addComponent(returnInMainWindowButton)));
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(originalFunction)
                                .addComponent(sourceFunctionTable)
                                .addComponent(createOriginalButton)
                                .addComponent(saveOriginalButton)
                                .addComponent(uploadOriginalButton))
                        .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(titleOverDerive)
                                .addComponent(deriveButton))
                        .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(differentiationResult)
                                .addComponent(deriveFunctionTable)
                                .addComponent(saveDeriveButton)
                                .addComponent(returnInMainWindowButton)));
    }

    private void addButtonsListeners() {
        createOriginalButton.addActionListener(event -> {
            JLabel message = new JLabel("<html>Выберете реализацию<br>(Введите \"массив\" или \"список\")");
            String realization = JOptionPane.showInputDialog(this, message);
            switch (realization) {
                case ("массив"): {

                }
                case ("список"): {
                    new BasicMathFunctionWindow(factory, this::setSourceTableFunction);
                }
            }
        });
        saveOriginalButton.addActionListener(event -> {
            new WritingToFile(sourceTableFunction);
        });
        uploadOriginalButton.addActionListener(event -> {
            new ReadingFromFile(factory, this::setSourceTableFunction);
        });
        deriveButton.addActionListener(event -> {
            deriveTableFunction = new TabulatedDifferentialOperator().derive(sourceTableFunction);
        });
        returnInMainWindowButton.addActionListener(event -> {

        });
        saveDeriveButton.addActionListener(event -> {
            new WritingToFile(deriveTableFunction);
        });
    }

    public TabulatedFunction getSourceTableFunction() {
        return sourceTableFunction;
    }

    public void setSourceTableFunction(TabulatedFunction sourceTableFunction) {
        this.sourceTableFunction = sourceTableFunction;
    }

    public static void main() {
        DifferentiationWindow differentiationWindow = new DifferentiationWindow(new ArrayTabulatedFunctionFactory());
    }
}
