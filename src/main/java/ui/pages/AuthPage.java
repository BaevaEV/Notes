package ui.pages;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AuthPage {
    private WebDriver driver;
    WebDriverWait wait;


    public String urlPage = "http://172.24.120.5:8081/login";

    @FindBy(id ="login-input")
    private WebElement loginField;

    @FindBy(id ="password-input")
    private WebElement passwordField;

    @FindBy(id ="form_auth_button")
    private WebElement loginButton;


    public AuthPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(550));
        PageFactory.initElements(driver,this);
    }

    public void goToAuthPage(){
        driver.get(urlPage);
    }

    public void fillInLogin(String LoginText){
        loginField.sendKeys(LoginText);
    }

    public void fillInPassword(String passwordText){
        passwordField.sendKeys(passwordText);
    }

    public void clickLoginButton(){
        new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(60))
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementClickInterceptedException.class)
                .until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();    }

}
