package hometests.steps;

import cucumber.api.java.ru.То;
import org.testng.Assert;
import redmine.managers.Context;
import redmine.ui.pages.HeaderPage;
import redmine.utils.BrowserUtils;

import static redmine.ui.pages.Pages.getPage;

public class ZAssertionSteps {
    @То("Значение переменной {string} равно  {int}")
    public void assertResult(String stashId,int expected){
        int  res=Context.getStash().get(stashId,Integer.class);
        Assert.assertEquals(res,expected);
    }
    @То("На главной странице отображается поле \"Проекты\"")
    public void assertProjectElementIsDisplayed(){
       Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).projects));
    }
}
