package ru.ssau.tk.dmitriy.laboratorywork.ui;

import ru.ssau.tk.dmitriy.laboratorywork.functions.*;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.TabulatedFunctionFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

public class BasicMathFunctionWindow extends JDialog {
    private final TabulatedFunctionFactory factory;

    private final JComboBox<String> functionsComboBox = new JComboBox<>();
    private final HashMap<String, MathFunction> functionsMap = new HashMap<>();

    private final JTextField fromField = new JTextField();
    private final JTextField toField = new JTextField();
    private final JTextField countOfPointsField = new JTextField();

    private final JLabel chooseFunctionLabel = new JLabel("<html>Выберите <br>или создайте<br> функцию");
    private final JLabel fromLabel = new JLabel("От:");
    private final JLabel toLabel = new JLabel("До:");
    private final JLabel countOfPointsLabel = new JLabel("<html>Количество<br>точек:");

    private final JButton createButton = new JButton("Создать");

    public BasicMathFunctionWindow(TabulatedFunctionFactory factory) {
        super();
        setTitle("Создание функции из ранее написанных");
        this.factory = factory;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        fillComboBox();
        addButtonListener();
        compose();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void compose() {
        Container container = getContentPane();
        GroupLayout layoutManager = new GroupLayout(container);
        container.setLayout(layoutManager);
        layoutManager.setAutoCreateGaps(true);
        layoutManager.setAutoCreateContainerGaps(true);
        setSize(450, 180);
        layoutManager.setHorizontalGroup(
                layoutManager.createSequentialGroup()
                        .addGroup(layoutManager.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(chooseFunctionLabel)
                                .addGap(10)
                                .addComponent(functionsComboBox))
                        .addGroup(layoutManager.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(fromLabel)
                                .addGap(10)
                                .addComponent(fromField))
                        .addGroup(layoutManager.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(toLabel)
                                .addComponent(toField))
                        .addGroup(layoutManager.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(countOfPointsLabel)
                                .addComponent(countOfPointsField))
                        .addComponent(createButton));
        layoutManager.setVerticalGroup(
                layoutManager.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addGroup(layoutManager.createSequentialGroup()
                                .addComponent(chooseFunctionLabel)
                                .addComponent(functionsComboBox))
                        .addGroup(layoutManager.createSequentialGroup()
                                .addComponent(fromLabel)
                                .addComponent(fromField))
                        .addGroup(layoutManager.createSequentialGroup()
                                .addComponent(toLabel)
                                .addComponent(toField))
                        .addGroup(layoutManager.createSequentialGroup()
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

    private void addButtonListener() {
        createButton.addActionListener(event -> {
            try {
                if ("Тождественная функция" == functionsComboBox.getSelectedItem()) {
                    functionsMap.put("Тождественная функция", new ConstantFunction(getUserConstant()));
                }
                MathFunction mathFunction = functionsMap.get(functionsComboBox.getSelectedItem());
                double xFrom = Double.parseDouble(fromField.getText());
                double xTo = Double.parseDouble(toField.getText());
                int count = Integer.parseInt(countOfPointsField.getText());
                //TODO: должно как-то передаваться в вызывающее окно
                System.out.println(factory.create(mathFunction, xFrom, xTo, count));
            } catch (Exception exception) {
                new ExceptionWindow(this, exception);
            }
        });
    }

    public static void main(String[] args) {
        BasicMathFunctionWindow functionWindow = new BasicMathFunctionWindow(new ArrayTabulatedFunctionFactory());
    }
}
