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

public class MyFirstTest {
    User user;

    @BeforeMethod
    public void prepareFixture(){
        user=new User().generate();
        openPage("login");
    }

    @Test
    public void myFirstLoginTest() {
        new LoginPage().login("admin","admin123");
        Assert.assertEquals(new HeaderPage().loggedAs(),"Вошли как admin");
    }

    @AfterMethod
    public void tearDown(){
        driverQuit();
    }
}