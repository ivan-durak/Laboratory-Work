package ru.ssau.tk.dmitriy.laboratorywork.operations;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.ssau.tk.dmitriy.laboratorywork.functions.*;

public class MiddleSteppingDifferentialOperatorTest {
    @Test
    public static void testDerive() {
        MiddleSteppingDifferentialOperator middleSteppingDifferentialOperator = new MiddleSteppingDifferentialOperator(3);
        Assert.assertEquals(middleSteppingDifferentialOperator.derive(new SquareFunction()).apply(10), 20.0);
        Assert.assertThrows(IllegalArgumentException.class, () -> middleSteppingDifferentialOperator.setStep(Double.NaN));
        Assert.assertThrows(IllegalArgumentException.class, () -> new MiddleSteppingDifferentialOperator(Double.POSITIVE_INFINITY));
    }
}