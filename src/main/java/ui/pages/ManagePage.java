package ui.pages;

import org.openqa.selenium.JavascriptExecutor;

import static ui.pages.TestBase.driver;


public class ManagePage {

    public void scrollDownPage (){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

    }
}
