package ru.ssau.tk.dmitriy.laboratorywork.operations;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.ssau.tk.dmitriy.laboratorywork.functions.ArrayTabulateFunction;
import ru.ssau.tk.dmitriy.laboratorywork.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.dmitriy.laboratorywork.functions.TabulatedFunction;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.LinkedListTabulatedFunctionFactory;

import static org.testng.Assert.*;

public class TabulatedDifferentialOperatorTest {

    @Test
    public void testGetFactory() {
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory());
        Assert.assertTrue(operator.getFactory() instanceof LinkedListTabulatedFunctionFactory);
        operator = new TabulatedDifferentialOperator();
        Assert.assertTrue(operator.getFactory() instanceof ArrayTabulatedFunctionFactory);
    }

    @Test
    public void testSetFactory() {
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory());
        Assert.assertTrue(operator.getFactory() instanceof LinkedListTabulatedFunctionFactory);
        operator.setFactory(new ArrayTabulatedFunctionFactory());
        Assert.assertTrue(operator.getFactory() instanceof ArrayTabulatedFunctionFactory);
    }

    @Test
    public void testDerive() {
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory());
        double[] xValues = new double[]{1, 2, 3, 4, 5}, yValues = new double[]{1, 4, 9, 16, 25};
        TabulatedFunction linkedListTabulateFunction = new LinkedListTabulatedFunctionFactory().create(xValues, yValues);
        TabulatedFunction tabulatedFunction = operator.derive(linkedListTabulateFunction);
        Assert.assertTrue(tabulatedFunction instanceof LinkedListTabulatedFunction);
        for (int i = 0; i < tabulatedFunction.getCount() - 1; i++) {
            Assert.assertEquals(tabulatedFunction.getX(i), xValues[i]);
            Assert.assertEquals(tabulatedFunction.getY(i), (yValues[i + 1] - yValues[i]));
        }
        Assert.assertEquals(tabulatedFunction.getX(tabulatedFunction.getCount() - 1), xValues[4]);
        Assert.assertEquals(tabulatedFunction.getY(tabulatedFunction.getCount() - 1), yValues[4] - yValues[3]);
        //TODO: доделать тест с применением нескольких видов фабрик
    }
}