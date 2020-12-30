package testNG;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CalculatorTest {
    @DataProvider(name = "positiveProvider", parallel = true)
    public static Object[][] positiveProvider() {
        return new Object[][]{
                {6, 2, 3},
                {8, 2, 4},
                {6, 1, 6},
                {6, 2, 3},
        };
    }

    @DataProvider(name = "negativeProvider", parallel = true)
    public static Object[][] negativeProvider() {
        return new Object[][]{
                {6, 0},
                {0, 0}
        };
    }

    @Test(testName = "Положительный тест калькулятора", dataProvider = "positiveProvider")
    public void positiveCalculationTest(int number1, int number2, int expectedResult) {
        int actualResult = Calculator.divide(number1, number2);
        Assert.assertEquals(actualResult, expectedResult);

    }

    @Test(testName = "Негативный тест калькулятора/Деление на 0/", dataProvider = "negativeProvider",
            expectedExceptions = {ArithmeticException.class})
    public void negativeCalculationTest(int number1, int number2) {
        Calculator.divide(number1, number2);
    }
}
