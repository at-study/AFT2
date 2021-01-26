package redmine.utils;

import io.qameta.allure.Step;
import org.testng.Assert;

public class Asserts {
    @Step("Сравнение переменных actual: {0} ,  expected:  {1}")
    public static void assertEquals(Object actual,Object expected){
        Assert.assertEquals(actual,expected);
    }
    @Step("Присутствует поле ID равное: {0} ")
    public static void assertNotNull(Object object){
        Assert.assertNotNull(object);
    }

}
