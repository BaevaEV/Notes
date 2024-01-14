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

public class RegesrtyPage {
    private WebDriver driver;
    WebDriverWait wait;


    @FindBy(xpath = "//button[string()='Зарегистрироваться']")
    private WebElement regestryButton;

    @FindBy(xpath = "//input[@placeholder='Логин']")
    private WebElement loginField;

    @FindBy(xpath = "//input[@placeholder='Пароль']")
    private WebElement passwordField;

    @FindBy(xpath = "//label[text()='E-mail']//following::input")
    private WebElement mailField;

    @FindBy(xpath = "//button[text()='Создать']")
    private WebElement createButton;


    public RegesrtyPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        PageFactory.initElements(driver, this);
    }


    public void fillInLogin(String Login) {
        loginField.sendKeys(Login);
    }

    public void fillInPassword(String password) {
        passwordField.sendKeys(password);
    }

    public void fillInEmail(String email) {
        mailField.sendKeys(email);
    }

    public void clickRegestryButton() {
        new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(240))
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementClickInterceptedException.class)
                .until(ExpectedConditions.elementToBeClickable(regestryButton));
        regestryButton.click();
    }

    public void clickCreateButton() {
        createButton.click();
    }
}
