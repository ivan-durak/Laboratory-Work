package ru.ssau.tk.dmitriy.laboratorywork.operations;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.ssau.tk.dmitriy.laboratorywork.functions.*;

public class RightSteppingDifferentialOperatorTest {
    @Test
    public static void testDerive() {
        RightSteppingDifferentialOperator rightSteppingDifferentialOperator = new RightSteppingDifferentialOperator(2);
        Assert.assertEquals(rightSteppingDifferentialOperator.derive(new SquareFunction()).apply(5), 12.0);
        Assert.assertThrows(IllegalArgumentException.class, () -> rightSteppingDifferentialOperator.setStep(Double.NaN));
        Assert.assertThrows(IllegalArgumentException.class, () -> new RightSteppingDifferentialOperator(Double.POSITIVE_INFINITY));
    }
}