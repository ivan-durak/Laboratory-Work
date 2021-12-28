package ru.ssau.tk.dmitriy.laboratorywork.ui;

import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.LinkedListTabulatedFunctionFactory;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.TabulatedFunctionFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Window extends JFrame {

    private final List<Double> xValues = new ArrayList<>();
    private final List<Double> yValues = new ArrayList<>();
    private final PartEditable tableModel = new PartEditable();
    private final JTable table = new JTable(tableModel);
    private final JLabel label = new JLabel();

    private final JButton createFunctionButton = new JButton("Создать табулированную функцию из массивов");
    private final JButton settingsButton = new JButton("Настройки");
    private final JButton createMathFunctionButton = new JButton("Создать табулированную функцию с помощью другой функции");

    private final JButton openButton = new JButton("Открыть функцию");
    private final JButton saveButton = new JButton("Сохранить функцию");
    private final JButton operationButton = new JButton("Операции");
    private final JButton differential = new JButton("Производная");

    private TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();

    public Window() {
        super("Основное окно");

        Container container = getContentPane();
        container.setLayout(new FlowLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800, 580);
        container.setBackground(Color.WHITE);
        container.add(createFunctionButton);
        container.add(settingsButton);
        container.add(createMathFunctionButton);
        container.add(openButton);
        container.add(saveButton);
        container.add(operationButton);
        container.add(differential);

        label.setPreferredSize(new Dimension(10, 10));

        compose();
        addButtonListeners();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void wrapTable(int countOld, int countNew) {
        tableModel.fireTableDataChanged();
        for (int i = 0; i <= countOld; ++i) {
            if (xValues.size() != 0) xValues.remove(countOld - i);
            if (yValues.size() != 0) yValues.remove(countOld - i);
        }
        for (int i = 0; i < countNew; ++i) {
            xValues.add(tableModel.getFunction().getX(i));
            yValues.add(tableModel.getFunction().getY(i));
        }
        tableModel.fireTableDataChanged();
    }

    private void addButtonListeners() {
        createFunctionButton.addActionListener(event -> {
            try {
                int countOld = xValues.size();
                new TabulatedTableWindow(factory, tableModel::setFunction);
                int countNew = tableModel.getFunction().getCount();
                wrapTable(countOld, countNew);
            } catch (Exception e) {
                if (e instanceof NullPointerException) {
                    e.printStackTrace();
                } else
                    new ExceptionWindow(this, e);
            }
        });

        createMathFunctionButton.addActionListener(event -> {
            try {
                int countOld = xValues.size();
                new BasicMathFunctionWindow(factory, tableModel::setFunction);
                int countNew = tableModel.getFunction().getCount();
                wrapTable(countOld, countNew);
            } catch (Exception e) {
                if (e instanceof NullPointerException) {
                    e.printStackTrace();
                } else
                    new ExceptionWindow(this, e);
            }
        });

        settingsButton.addActionListener(event -> {
            try {
                new SettingsWindow(selectedFactory -> factory = selectedFactory);
            } catch (Exception e) {
                if (e instanceof NullPointerException) {
                    e.printStackTrace();
                } else
                    new ExceptionWindow(this, e);
            }
        });

        openButton.addActionListener(event -> {
            try {
                int countOld = xValues.size();
                new ReadingFromFile(factory, tableModel::setFunction);
                int countNew = tableModel.getFunction().getCount();
                wrapTable(countOld, countNew);
            } catch (Exception e) {
                if (e instanceof NullPointerException) {
                    e.printStackTrace();
                } else
                    new ExceptionWindow(this, e);
            }
        });

        saveButton.addActionListener(event -> {
            try {
                new WritingToFile(tableModel.getFunction());
            } catch (Exception e) {
                if (e instanceof NullPointerException) {
                    e.printStackTrace();
                } else
                    new ExceptionWindow(this, e);
            }
        });

        operationButton.addActionListener(event -> {
            try {
                new SimpleOperationsWindow();
            } catch (Exception e) {
                if (e instanceof NullPointerException) {
                    e.printStackTrace();
                } else
                    new ExceptionWindow(this, e);
            }
        });

        differential.addActionListener(event -> {
            try {
                new DifferentiationWindow(factory);
            } catch (Exception e) {
                if (e instanceof NullPointerException) {
                    e.printStackTrace();
                } else
                    new ExceptionWindow(this, e);
            }
        });
    }

    private void compose() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        JScrollPane tableScrollPane = new JScrollPane(table);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(label))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(createFunctionButton)
                        .addComponent(createMathFunctionButton))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(operationButton)
                        .addComponent(differential)
                        .addComponent(settingsButton)
                        .addComponent(openButton)
                        .addComponent(saveButton))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(tableScrollPane))
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(label))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(createFunctionButton)
                        .addComponent(createMathFunctionButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(operationButton)
                        .addComponent(differential)
                        .addComponent(settingsButton)
                        .addComponent(openButton)
                        .addComponent(saveButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(tableScrollPane))
        );
    }

    public static void main(String[] args) throws IOException {
        Window window = new Window();
    }
}