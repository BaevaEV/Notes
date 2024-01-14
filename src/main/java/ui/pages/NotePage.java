package ui.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Text;

import java.time.Duration;


public class NotePage {
    private WebDriver driver;
    WebDriverWait wait;


    public NotePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "note-modal-title-new_empty")
     private WebElement noteTitleField;

    @FindBy(xpath = "//div[@class='ModalCard_cardBodyInput__ghZU0 modal-title h4']")
    private WebElement savedNoteTitleField;

    @FindBy(xpath = "//div[@class='modal-content']//div[contains(text(),'Содержание')]")
    private WebElement addContentButton;

    @FindBy(id = "note-modal-content-new_empty")
    private WebElement noteContentField;

    @FindBy(xpath = "//div[@class='ModalCard_cardBodyInput__ghZU0']")
    private WebElement savedNoteContentField;

    @FindBy(id = "palette-btn-new_empty")
    private WebElement addColorButton;

    @FindBy(id = "palette-color-#fff475")
    private WebElement addNoteColorButton;

    @FindBy(id = "note-modal-save-btn-new_empty")
    private WebElement saveNewNoteButton;

    @FindBy(xpath = "//button[@class='ModalCard_cardButton__g2ndB btn btn-primary']")
    private WebElement saveEditableNoteButton;



    public void fillNoteTitleField(String Title){
            noteTitleField.sendKeys(Title);
        }

        public void addNoteContent(String Content){
            new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(240))
                    .ignoring(NoSuchElementException.class)
                    .ignoring(ElementClickInterceptedException.class)
                    .until(ExpectedConditions.elementToBeClickable(addContentButton));
            addContentButton.click();
            noteContentField.sendKeys(Content);;
        }

        public void clickAddColor(String colorStyle){
            addColorButton.click();;
            WebElement colorElement = driver.findElement(By.xpath("//div[@style='"+colorStyle+"']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", colorElement);
        }

        public void saveNewNote(){
            saveNewNoteButton.click();;
        }
        public void clickSaveEditableNoteButton(){
            saveEditableNoteButton.click();;
    }

        public Boolean saveButtonIsDisplayed() {
            saveNewNoteButton.isDisplayed();
            return true;
        }

        public void clearNoteTitleField(){
        savedNoteTitleField.clear();
        }

        public void editNoteTitleField(String Title){
        savedNoteTitleField.sendKeys(Title);
    }

    public void clearNoteContentField(){
        savedNoteContentField.clear();
    }

    public void editNoteContentField(String Content) {
        savedNoteContentField.sendKeys(Content);
    }

}
