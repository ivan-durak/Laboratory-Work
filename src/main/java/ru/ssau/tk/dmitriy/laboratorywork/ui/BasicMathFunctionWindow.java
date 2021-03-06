package ru.ssau.tk.dmitriy.laboratorywork.ui;

import ru.ssau.tk.dmitriy.laboratorywork.functions.*;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.TabulatedFunctionFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Consumer;

public class BasicMathFunctionWindow extends JDialog {
    private final TabulatedFunctionFactory factory;

    private final JComboBox<String> functionsComboBox = new JComboBox<>();
    private final HashMap<String, MathFunction> functionsMap = new HashMap<>();

    private final JTextField fromField = new JTextField();
    private final JTextField toField = new JTextField();
    private final JTextField countOfPointsField = new JTextField();

    private final JLabel chooseFunctionLabel = new JLabel("<html>Выберите <br> функцию");
    private final JLabel fromLabel = new JLabel("От:");
    private final JLabel toLabel = new JLabel("До:");
    private final JLabel countOfPointsLabel = new JLabel("<html>Количество<br>точек:");

    private final JButton createButton = new JButton("Создать");

    public BasicMathFunctionWindow(TabulatedFunctionFactory factory, Consumer<? super TabulatedFunction> callback) {
        super();
        setTitle("Создание функции из ранее написанных");
        this.factory = factory;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        fillComboBox();
        Container container = getContentPane();
        container.add(functionsComboBox);
        container.add(fromField);
        container.add(toField);
        container.add(countOfPointsField);
        container.add(chooseFunctionLabel);
        container.add(fromLabel);
        container.add(toLabel);
        container.add(countOfPointsLabel);
        container.add(createButton);
        addButtonListener(callback);
        compose();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void compose() {
        Container container = getContentPane();
        GroupLayout layoutManager = new GroupLayout(container);
        container.setLayout(layoutManager);
        layoutManager.setAutoCreateGaps(true);
        layoutManager.setAutoCreateContainerGaps(true);
        setSize(500, 130);
        layoutManager.setHorizontalGroup(
                layoutManager.createSequentialGroup()
                        .addGroup(layoutManager.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(chooseFunctionLabel)
                                .addComponent(functionsComboBox))
                        .addGroup(layoutManager.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(fromLabel)
                                .addComponent(fromField))
                        .addGroup(layoutManager.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(toLabel)
                                .addComponent(toField))
                        .addGroup(layoutManager.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(countOfPointsLabel)
                                .addComponent(countOfPointsField))
                        .addComponent(createButton));
        layoutManager.setVerticalGroup(
                layoutManager.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layoutManager.createSequentialGroup()
                                .addGap(10)
                                .addComponent(chooseFunctionLabel)
                                .addComponent(functionsComboBox))
                        .addGroup(layoutManager.createSequentialGroup()
                                .addGap(27)
                                .addComponent(fromLabel)
                                .addComponent(fromField))
                        .addGroup(layoutManager.createSequentialGroup()
                                .addGap(27)
                                .addComponent(toLabel)
                                .addComponent(toField))
                        .addGroup(layoutManager.createSequentialGroup()
                                .addGap(11)
                                .addComponent(countOfPointsLabel)
                                .addComponent(countOfPointsField))
                        .addComponent(createButton));
    }

    private void fillComboBox() {
        functionsMap.put("Нулевая функция", new ZeroFunction());
        functionsMap.put("Единичная функция", new UnitFunction());
        functionsMap.put("Квадратичная функция", new SquareFunction());
        functionsMap.put("Синусоидальная функция", new SinFunction());
        functionsMap.put("Косинусоидальная функция", new CosFunction());
        functionsMap.put("Обратная функция", new ReverseFunction());
        functionsMap.put("Логарифмическая функция", new LogFunction());
        functionsMap.put("Тождественная функция", new IdentityFunction());
        functionsMap.put("Экспоненциальная функция", new ExponentFunction());
        functionsMap.put("Кубическая функция", new CubeFunction());
        functionsMap.put("Константная функция", new ConstantFunction(2));
        String[] strings = new String[functionsMap.size()];
        int i = 0;
        for (String string : functionsMap.keySet()) {
            strings[i++] = string;
        }
        Arrays.sort(strings);
        for (String string : strings) {
            functionsComboBox.addItem(string);
        }
    }

    private double getUserConstant() {
        JLabel screen = new JLabel("Введите константу");
        String constant = JOptionPane.showInputDialog(screen);
        return Double.parseDouble(constant);
    }

    private void addButtonListener(Consumer<? super TabulatedFunction> callback) {
        createButton.addActionListener(event -> {
            try {
                if ("Константная функция" == functionsComboBox.getSelectedItem()) {
                    functionsMap.put("Константная функция", new ConstantFunction(getUserConstant()));
                }
                MathFunction mathFunction = functionsMap.get(functionsComboBox.getSelectedItem());
                double xFrom = Double.parseDouble(fromField.getText());
                double xTo = Double.parseDouble(toField.getText());
                int count = Integer.parseInt(countOfPointsField.getText());
                callback.accept(factory.create(mathFunction, xFrom, xTo, count));
                dispose();
            } catch (Exception exception) {
                new ExceptionWindow(this, exception);
            }
        });
    }
}
