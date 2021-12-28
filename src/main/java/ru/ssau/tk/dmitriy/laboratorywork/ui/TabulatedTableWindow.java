package ru.ssau.tk.dmitriy.laboratorywork.ui;

import ru.ssau.tk.dmitriy.laboratorywork.exceptions.ArrayIsNotSortedException;
import ru.ssau.tk.dmitriy.laboratorywork.exceptions.DifferentLengthOfArraysException;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.dmitriy.laboratorywork.functions.TabulatedFunction;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.TabulatedFunctionFactory;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

public class TabulatedTableWindow extends JDialog {

    private final List<Double> xValues = new ArrayList<>();
    private final List<Double> yValues = new ArrayList<>();
    private final AbstractTableModel tableModel = new Table(xValues, yValues);
    private final JTable table = new JTable(tableModel);

    private final JLabel label = new JLabel("Введите количество точек:");
    private final JTextField countField = new JTextField();
    private final JButton inputButton = new JButton("Ввести");
    private final JButton createFunctionButton = new JButton("Создать");

    private TabulatedFunction tabulatedFunction;

    public TabulatedTableWindow(TabulatedFunctionFactory factory, Consumer<? super TabulatedFunction> callback) {
        super();
        setTitle("Создание функции из массива");

        Container container = getContentPane();
        container.setLayout(new FlowLayout());
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        container.setBackground(Color.PINK);
        container.add(label);
        container.add(inputButton);
        container.add(createFunctionButton);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setBackground(Color.WHITE);
        table.setGridColor(Color.PINK);

        label.setForeground(Color.WHITE);

        inputButton.setBackground(Color.WHITE);
        inputButton.setForeground(Color.PINK);

        createFunctionButton.setBackground(Color.WHITE);
        createFunctionButton.setForeground(Color.PINK);

        compose();
        addButtonListeners(factory, callback);

        setModal(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void clearingTheTable(int count) {
        for (int i = 0; i < count; ++i) {
            xValues.remove(count - (i + 1));
            yValues.remove(count - (i + 1));
            tableModel.fireTableDataChanged();
        }
    }

    private void addButtonListeners(TabulatedFunctionFactory factory, Consumer<? super TabulatedFunction> callback) {
        inputButton.addActionListener(event -> {
            try {
                createFunctionButton.setEnabled(false);
                int count = Integer.parseInt(countField.getText());
                clearingTheTable(tableModel.getRowCount());
                for (int i = 0; i < count; i++) {
                    xValues.add(0.);
                    yValues.add(0.);
                    tableModel.fireTableDataChanged();
                }
                if (tableModel.getRowCount() > 1) {
                    createFunctionButton.setEnabled(true);
                } else {
                    throw new DifferentLengthOfArraysException();
                }
            } catch (Exception e) {
                new ExceptionWindow(this, e);
            }
        });

        createFunctionButton.addActionListener(event -> {
            try {
                double[] x = new double[xValues.size()];
                double[] y = new double[xValues.size()];
                x[0] = xValues.get(0);
                y[0] = yValues.get(0);
                for (int i = 1; i < xValues.size(); i++) {
                    if (xValues.get(i - 1) > xValues.get(i)) {
                        throw new ArrayIsNotSortedException();
                    }
                    x[i] = xValues.get(i);
                    y[i] = yValues.get(i);
                }
                tabulatedFunction = factory.create(x, y);
                callback.accept(tabulatedFunction);
                setVisible(true);
                this.dispose();
            } catch (Exception e) {
                new ExceptionWindow(this, e);
            }
        });
    }

    void compose() {
        Container container = getContentPane();
        GroupLayout layout = new GroupLayout(container);
        container.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        JScrollPane tableScrollPane = new JScrollPane(table);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(label)
                        .addComponent(countField)
                        .addComponent(inputButton))
                .addComponent(tableScrollPane)
                .addComponent(createFunctionButton)
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(label)
                        .addComponent(countField)
                        .addComponent(inputButton))
                .addComponent(tableScrollPane)
                .addComponent(createFunctionButton)
        );
    }
}