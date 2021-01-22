package hometests.ui;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.user.User;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.LoginPage;
import static redmine.managers.Manager.*;
import static redmine.managers.Manager.driverQuit;
import static redmine.ui.pages.Pages.getPage;

public class MyFirstTest {
    User user;

    @BeforeMethod
    public void prepareFixture(){
        user=new User().generate();
        openPage("login");
    }

    @Test
    public void myFirstLoginTest() throws InterruptedException {
        getPage(LoginPage.class).login(user.getLogin(), user.getPassword());
        System.out.println(user.getLogin());
        System.out.println(user.getHashedPassword());
        System.out.println(user.getPassword());
        Assert.assertEquals(getPage(HeaderPage.class).loggedAs(),"Вошли как "+user.getLogin());
        Thread.sleep(500_000_000_000_000L) ;
    }

    @AfterMethod
    public void tearDown(){
        driverQuit();
    }
}