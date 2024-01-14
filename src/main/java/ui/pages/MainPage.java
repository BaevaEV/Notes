package ui.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class MainPage {
    private WebDriver driver;
    WebDriverWait wait;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        PageFactory.initElements(driver, this);
    }


    @FindBy(xpath = "//div[contains(@class, 'Card_containerNew')]")
    private WebElement addNoteButton;

    @FindBy(xpath = "//div[contains(@class,'Card_fullscreenBtnBar')]//img[2]")
    private WebElement deleteNote;

    @FindBy(xpath = "//img[@id='logout-btn']")
    private WebElement exit;

    @FindBy(xpath = "//button[string()='Да']")
    private WebElement deleteYesButton;

    public void addNoteButtonClick() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false)", addNoteButton);
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        try {
            addNoteButton.click();
        } catch (ElementClickInterceptedException e) {
            new FluentWait<>(driver)
                    .withTimeout(Duration.ofMinutes(5))
                    .ignoring(NoSuchElementException.class)
                    .ignoring(ElementClickInterceptedException.class)
                    .until(ExpectedConditions.elementToBeClickable(addNoteButton));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'Card_containerNew')]")));
            addNoteButton.click();
        }
    }


    public Boolean addNoteButtonDisplayed() {
        addNoteButton.isDisplayed();
        return true;
    }

    public void exitClick() {
        exit.click();
    }

    public String getNoteTitle(String noteId) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[@id='note-title-" + noteId + "']")));
        return driver.findElement(By.xpath("//p[@id='note-title-" + noteId + "']")).getText();
    }

    public String getNoteContent(String noteId) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='note-content-" + noteId + "']//div")));
        return driver.findElement(By.xpath("//div[@id='note-content-" + noteId + "']//div")).getText();
    }

    public String getNoteColor(String noteId) {
        return driver.findElement(By.xpath("//div[@id='note-container-" + noteId + "']")).getAttribute("style");
    }

    public By clickDeleteNote() {
        wait.until(ExpectedConditions.visibilityOf(deleteNote));
        deleteNote.click();
        return null;
    }

    public void clockDeleteYesButton() {
        wait.until(ExpectedConditions.visibilityOf(deleteYesButton));
        deleteYesButton.click();
    }

}
