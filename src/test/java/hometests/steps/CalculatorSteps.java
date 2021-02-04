package hometests.steps;

import cucumber.api.java.ru.Если;
import cucumber.api.java.ru.То;
import org.testng.Assert;
import temp.Calculator;

public class CalculatorSteps {
    private int res;
    @Если("В калькуляторе сложить числа {int} и {int}")
    public  void calculateSum(int num1, int num2){
        res= Calculator.summ(num1,num2);
    }
    @То("Сумма будет равна {int}")
    public void assertResult(int expected){
        Assert.assertEquals(res,expected);
    }
}
