package redmine.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HeaderPage {
    private WebDriver driver;
    private WebElement home;
    private WebElement projects;
    private WebElement loggeAs;

    public HeaderPage(WebDriver driver){
        this.driver=driver;
        home=driver.findElement(By.xpath("//a[@class='home']"));;
        projects=driver.findElement(By.xpath("//a[@class='projects']"));
        loggeAs=driver.findElement(By.xpath("//div[@id='loggedas']"));
    }

    public String loggedAs(){
        return  loggeAs.getText();
    }
}
