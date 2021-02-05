package hometests.steps;

import cucumber.api.java.ru.Если;
import redmine.managers.Context;
import temp.Calculator;

public class ZCalculatorSteps {
    @Если("В калькуляторе {string} числа {int} и {int} и сохранить результат в переменную {string}")
    public void calculateSum(String operation, int num1, int num2, String resStashId) {
        int res;
        switch (operation) {
            case "сложить":
                res = Calculator.summ(num1, num2);
                Context.put(resStashId, res);
                break;
            case "разделить":
                res = Calculator.divide(num1, num2);
                Context.put(resStashId, res);
                break;
            default:
                throw new IllegalArgumentException("Не определена операция для чисел" + operation);
        }
    }
}
