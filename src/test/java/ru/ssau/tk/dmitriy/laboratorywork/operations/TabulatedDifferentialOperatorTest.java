package ru.ssau.tk.dmitriy.laboratorywork.operations;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.ssau.tk.dmitriy.laboratorywork.functions.ArrayTabulateFunction;
import ru.ssau.tk.dmitriy.laboratorywork.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.dmitriy.laboratorywork.functions.TabulatedFunction;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.dmitriy.laboratorywork.functions.factory.LinkedListTabulatedFunctionFactory;
public class TabulatedDifferentialOperatorTest {
    public static final double ACCURACY = 0.00001;

    private final double[] xValues = new double[]{1, 2, 3, 4, 5};
    private final double[] yValues = new double[]{2, 4, 6, 8, 10};

    @Test
    public void testDerive() {
        TabulatedFunction linkedListTabulatedFunction = new LinkedListTabulatedFunction(xValues, yValues);
        TabulatedDifferentialOperator differentialOperator = new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory());
        TabulatedFunction diffFunctionList = differentialOperator.derive(linkedListTabulatedFunction);

        Assert.assertEquals(diffFunctionList.getX(0), 1, ACCURACY);
        Assert.assertEquals(diffFunctionList.getX(4), 5, ACCURACY);
        Assert.assertEquals(diffFunctionList.getY(0), 2, ACCURACY);
        Assert.assertEquals(diffFunctionList.getY(4), 2, ACCURACY);
        Assert.assertTrue(diffFunctionList instanceof LinkedListTabulatedFunction);

        TabulatedFunction arrayTabulatedFunction = new ArrayTabulateFunction(xValues, yValues);
        TabulatedDifferentialOperator differentialOperator1 = new TabulatedDifferentialOperator(new ArrayTabulatedFunctionFactory());
        TabulatedFunction diffFunctionArray = differentialOperator1.derive(arrayTabulatedFunction);

        Assert.assertEquals(diffFunctionArray.getX(0), 1, ACCURACY);
        Assert.assertEquals(diffFunctionArray.getX(4), 5, ACCURACY);
        Assert.assertEquals(diffFunctionArray.getY(0), 2, ACCURACY);
        Assert.assertEquals(diffFunctionArray.getY(4), 2, ACCURACY);
        Assert.assertTrue(diffFunctionArray instanceof ArrayTabulateFunction);
    }

    @Test
    public void testDeriveSynchronously() {
        TabulatedFunction linkedListTabulatedFunction = new LinkedListTabulatedFunction(xValues, yValues);
        TabulatedDifferentialOperator differentialOperator = new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory());
        TabulatedFunction diffFunctionList = differentialOperator.deriveSynchronously(linkedListTabulatedFunction);
        TabulatedFunction arrayTabulatedFunction = new ArrayTabulateFunction(xValues, yValues);
        TabulatedDifferentialOperator differentialOperator1 = new TabulatedDifferentialOperator(new ArrayTabulatedFunctionFactory());
        TabulatedFunction diffFunctionArray = differentialOperator1.deriveSynchronously(arrayTabulatedFunction);

        Assert.assertEquals(diffFunctionList.getX(0), 1, ACCURACY);
        Assert.assertEquals(diffFunctionList.getX(4), 5, ACCURACY);
        Assert.assertEquals(diffFunctionList.getY(0), 2, ACCURACY);
        Assert.assertEquals(diffFunctionList.getY(4), 2, ACCURACY);

        Assert.assertEquals(diffFunctionArray.getX(0), 1, ACCURACY);
        Assert.assertEquals(diffFunctionArray.getX(4), 5, ACCURACY);
        Assert.assertEquals(diffFunctionArray.getY(0), 2, ACCURACY);
        Assert.assertEquals(diffFunctionArray.getY(4), 2, ACCURACY);
    }
}
