package hometests.steps;

import cucumber.api.java.ru.То;
import org.testng.Assert;
import redmine.managers.Context;

public class AssertionSteps {
    @То("Значение переменной {string} равно  {int}")
    public void assertResult(String stashId,int expected){
        int res=Context.getStash().get(stashId,Integer.class);
        Assert.assertEquals(res,expected);
    }
}
