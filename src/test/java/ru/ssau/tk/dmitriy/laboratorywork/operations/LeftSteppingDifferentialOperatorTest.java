package ru.ssau.tk.dmitriy.laboratorywork.operations;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.ssau.tk.dmitriy.laboratorywork.functions.*;

public class LeftSteppingDifferentialOperatorTest {

    @Test
    public static void testDerive() {
        LeftSteppingDifferentialOperator leftSteppingDifferentialOperator = new LeftSteppingDifferentialOperator(5);
        Assert.assertEquals(leftSteppingDifferentialOperator.derive(new SquareFunction()).apply(9),0.2);
        Assert.assertThrows(IllegalArgumentException.class, () -> leftSteppingDifferentialOperator.setStep(-5.0));
        Assert.assertThrows(IllegalArgumentException.class, () -> new LeftSteppingDifferentialOperator(Double.NaN));
    }
}