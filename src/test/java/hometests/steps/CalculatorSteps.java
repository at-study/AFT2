package hometests.steps;
import cucumber.api.java.ru.Если;
import cucumber.api.java.ru.То;
import org.testng.Assert;
import temp.Calculator;

public class CalculatorSteps {
    private int res;
    @Если("В калькуляторе {string} числа {int} и {int}")
    public  void calculateSum(String operation,int num1, int num2){
        switch (operation)
        {
            case "сложить": res= Calculator.summ(num1,num2);break;
            case "разделить": res= Calculator.divide(num1,num2);break;
            default:throw new IllegalArgumentException("Не определена операция для чисел"+operation);
        }
    }

    @То("Результат будет равен  {int}")
    public void assertResult(int expected){
        Assert.assertEquals(res,expected);
    }
}
