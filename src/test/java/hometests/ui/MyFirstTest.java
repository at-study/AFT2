package hometests.ui;


import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.managers.Manager;
import redmine.model.user.User;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.LoginPage;
import redmine.utils.StringGenerators;

import java.io.FileOutputStream;
import java.io.IOException;

import static redmine.managers.Manager.*;
import static redmine.managers.Manager.driverQuit;
import static redmine.ui.pages.Pages.getPage;

public class MyFirstTest {
    User user;

    @BeforeMethod
    public void prepareFixture() {
        user = new User().generate();
        openPage("login");
    }

    @Test
    public void myFirstLoginTest() throws IOException {
        getPage(LoginPage.class).login(user.getLogin(), user.getPassword());
        Assert.assertEquals(getPage(HeaderPage.class).loggedAs(), "Вошли как " + user.getLogin());
        // byte[] screenshot=Manager.takesScreenshot();
        // FileOutputStream stream=new FileOutputStream("target\\"+ StringGenerators.randomEnglishLowerString(12)+".png");
        //  stream.write(screenshot);
        // stream.flush();
        //  stream.close();
    }

    @AfterMethod
    public void tearDown() {
        driverQuit();
    }
}