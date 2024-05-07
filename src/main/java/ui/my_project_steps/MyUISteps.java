package ui.my_project_steps;


import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.pages.TestBase;
import utils.Color;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;


public class MyUISteps extends TestBase{

    private WebDriver driver;
    private List<WebElement> notes;

    public MyUISteps(WebDriver driver) {
        this.driver = getDriver();
    }


    @Step("Нажимаем кнопку Регистрации")
    public void pushButtonRegistry() {
        TestBase.getRegistryPage().clickRegistryButton();

    }

    @Step("Вводим логин {login} пароль {password} и  email {email}")
    public void setInfoToRegistry(String login, String password, String email) {
        TestBase.getRegistryPage().fillInLogin(login);
        TestBase.getRegistryPage().fillInPassword(password);
        TestBase.getRegistryPage().fillInEmail(email);
    }

    @Step("Нажимаем кнопку Создать")
    public void pushButtonRegistryCreate() {
        TestBase.getRegistryPage().clickCreateButton();
    }

    @Step("Вход в приложение")
    public void goToAuthPage() {
        TestBase.getAuthPage().goToAuthPage();
    }

    @Step("Вводим логин {login} пароль {password}")
    public void setInfoToAuthAndClick(String login, String password) {
        TestBase.getAuthPage().fillInLogin(login);
        TestBase.getAuthPage().fillInPassword(password);
        TestBase.getAuthPage().clickLoginButton();
    }

    @Step("Проверка входа в приложение")
    public void clickLoginAndCheck() {
        TestBase.getAuthPage().clickLoginButton();
        Assertions.assertTrue(TestBase.getMainPage().addNoteButtonDisplayed());
    }

    @Step("Нажимаем кнопку добавления Заметки")
    public void clickAddNoteButton() {
        TestBase.getMainPage().addNoteButtonClick();
    }

    @Step("Создаем заметку {Title}")
    public void fillInfoForNote(String Title, String Content, String Color) {
        utils.Color colorUtility = new Color();
        TestBase.getNotePage().fillNoteTitleField(Title);
        TestBase.getNotePage().addNoteContent(Content);
        TestBase.getNotePage().clickAddColor(colorUtility.getColorStyle(Color));
    }

    @Step("Нажимаем кнопку сохранения Заметки")
    public void clickSaveNoteButton() {
        TestBase.getNotePage().saveNewNote();
    }


    @Step("Создание множетсва заметок {numberOfNotesToCreate}")
    public void createSoManyNotes(int numberOfNotesToCreate) {
        for (int i = 1; i <= numberOfNotesToCreate; i++) {
            createAndVerifyNote("Title " + i, "Content " + i, "Красный");
        }

    }

    private void createAndVerifyNote(String title, String content, String color) {
        TestBase.getMainPage().addNoteButtonClick();
        fillInfoForNote(title, content, color);
        TestBase.getNotePage().saveNewNote();
    }

    @Step("Подсчет заметок")
    public int countNote() {
        try {
            notes = (List<WebElement>) driver.findElements(By.xpath("//div[contains(@id,'note-container')]"));
            return notes.size();
        } catch (TimeoutException e) {
            System.out.println("Заметок больше нет");
            return 0;
        }
    }


    @Step("Удаление всех заметок")
    public void deleteAllNotes() {
        WebDriverWait wait = new WebDriverWait((WebDriver) driver, Duration.ofSeconds(20));
        notes = driver.findElements(By.xpath("//div[contains(@id,'note-container')]"));
        Iterator<WebElement> iterator = notes.iterator();
        while (iterator.hasNext()) {
            WebElement note = iterator.next();
            int m = countNote();
            if (m != 0) {
                WebElement title1 = new WebDriverWait((WebDriver) driver, Duration.ofSeconds(20))
                        .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@style,'display')]/div[" + m + "]")));
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@style,'display')]/div[" + m + "]/div[2]/img[2]"))).click();
                TestBase.getMainPage().clockDeleteYesButton();
                iterator.remove();
            } else {
                break;
            }
        }
    }
}

