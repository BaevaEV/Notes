package ui.my_project_steps;


import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Color;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;

import static ui.pages.TestBase.*;

public class MySteps {
    List<WebElement> notes;

    @Step("Нажимаем кнопку Регистрации")
    public void pushButtonRegistry() {
        registryPage.clickRegestryButton();

    }

    @Step("Вводим логин {login} пароль {password} и  email {email}")
    public void setInfoToRegistry(String login, String password, String email) {
        registryPage.fillInLogin(login);
        registryPage.fillInPassword(password);
        registryPage.fillInEmail(email);
    }

    @Step("Нажимаем кнопку Создать")
    public void pushButtonRegistryCreate() {
        registryPage.clickCreateButton();
    }

    @Step("Вход в приложение")
    public void goToAuthPage() {
        authPage.goToAuthPage();
    }

    @Step("Вводим логин {login} пароль {password}")
    public void setInfoToAuthAndClick(String login, String password) {
        authPage.fillInLogin(login);
        authPage.fillInPassword(password);
        authPage.clickLoginButton();
    }

    @Step("Проверка входа в приложение")
    public void clickLoginAndCheck() {
        authPage.clickLoginButton();
        Assertions.assertTrue(mainPage.addNoteButtonDisplayed());
    }

    @Step("Нажимаем кнопку добавления Заметки")
    public void clickAddNoteButton() {
        mainPage.addNoteButtonClick();
    }

    @Step("Создаем заметку {Title}")
    public void fillInfoForNote(String Title, String Content, String Color) {
        utils.Color colorUtility = new Color();
        notePage.fillNoteTitleField(Title);
        notePage.addNoteContent(Content);
        notePage.clickAddColor(colorUtility.getColorStyle(Color));
    }

    @Step("Нажимаем кнопку сохранения Заметки")
    public void clickSaveNoteButton() {
        notePage.saveNewNote();
    }


    @Step("Создание множетсва заметок {numberOfNotesToCreate}")
    public void createSoManyNotes(int numberOfNotesToCreate) {
        for (int i = 1; i <= numberOfNotesToCreate; i++) {
            createAndVerifyNote("Title " + i, "Content " + i, "Красный");
        }

    }

    private void createAndVerifyNote(String title, String content, String color) {
        mainPage.addNoteButtonClick();
        fillInfoForNote(title, content, color);
        notePage.saveNewNote();
    }

    @Step("Подсчет заметок")
    public int countNote() {
        try {
            notes = driver.findElements(By.xpath("//div[contains(@id,'note-container')]"));
            return notes.size();
        } catch (TimeoutException e) {
            System.out.println("Заметок больше нет");
            return 0;
        }
    }


    @Step("Удаление всех заметок")
    public void deleteAllNotes() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        notes = driver.findElements(By.xpath("//div[contains(@id,'note-container')]"));
        Iterator<WebElement> iterator = notes.iterator();
        while (iterator.hasNext()) {
            WebElement note = iterator.next();
            int m = countNote();
            if (m != 0) {
                WebElement title1 = new WebDriverWait(driver, Duration.ofSeconds(20))
                        .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@style,'display')]/div[" + m + "]")));
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@style,'display')]/div[" + m + "]/div[2]/img[2]"))).click();
                mainPage.clockDeleteYesButton();
                iterator.remove();
            } else {
                break;
            }
        }
    }
}

