package redmine.utils;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class BrowserUtils {
    public static  boolean isElementPresent(WebElement element){
        try {
           return element.isDisplayed();
        }catch (NoSuchElementException exception){
            return false;
        }
    }
}
