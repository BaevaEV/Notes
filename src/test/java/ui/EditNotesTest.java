package ui;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.my_project_steps.MyUISteps;
import ui.pages.TestBase;
import utils.Color;
import utils.Datafaker;

import java.time.Duration;

import static io.qameta.allure.Allure.step;


@DisplayName(value = "Редактирование созданных заметок")
public class EditNotesTest extends TestBase {
    WebDriverWait wait = new WebDriverWait((WebDriver) driver, Duration.ofSeconds(20));
    MyUISteps myUISteps = new MyUISteps(getDriver());
    Datafaker dataFaker = new Datafaker();
    String title = dataFaker.generateTextTitle();
    String content = dataFaker.generateTextContent();
    Color colorUtility = new Color();
    String login = "Katerina";
    String noteTitle;
    String noteColor;
    String noteContent;

    @BeforeEach
    public void start() {
        step("1. Вход в приложение", () -> {
            TestBase.getAuthPage().goToAuthPage();
        });
        step("2. Ввод данных существующего пользователя", () -> {
            myUISteps.setInfoToAuthAndClick(login, "1234");
        });
        step("3. Проверка входа в приложение ", () -> {
            TestBase.getAuthPage().clickLoginButton();
            Assertions.assertTrue(TestBase.getMainPage().addNoteButtonDisplayed());
        });
    }

        @Test
        @Tag("Ui")
        @DisplayName(value = "Редактирование созданной заметки")
        public void editNewNotes() {
            for (int i = 0; i < 3; i++) {
                step("1. Создание заметки и заполнение информации", () -> {
                    myUISteps.clickAddNoteButton();
                    myUISteps.fillInfoForNote(title, content, "Красный");
                });
                step("2. Сохранение заметки", () -> {
                    myUISteps.clickSaveNoteButton();
                });
                step("3. Получение id созданной заметки и заполненных данных с главной страницы", () -> {
                    String noteId = dbConnection.executeParameterizedQueryWithWait("/sql/selectFindNote.sql", "id", login, 200);
                    noteTitle = TestBase.getMainPage().getNoteTitle(noteId);
                    noteContent = TestBase.getMainPage().getNoteContent(noteId);
                    noteColor = TestBase.getMainPage().getNoteColor(noteId).replace("-color","");
                });
                step("3. Сравнение и проверка отображаемых данных на соответствие изначально введенным", () -> {
                    Assertions.assertEquals(title, noteTitle, "Заголовок не совпадает с "+noteTitle+".");
                    Assertions.assertEquals(content, noteContent, "Текст заметки не совпадает с "+noteContent+".");
                    Assertions.assertEquals(colorUtility.getColorStyle("Красный"), noteColor, "Цвет не совпадает.");
                });
            }
            step("4. Редактирование заметки", () -> {
                for (int j = 0; j < 3; j++) {
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@style,'display')]/div["+(j+1)+"]/div[2]/img[1]"))).click();
                    TestBase.getNotePage().clearNoteTitleField();
                    TestBase.getNotePage().editNoteTitleField("О зиме "+j+"");
                    TestBase.getNotePage().clearNoteContentField();
                    TestBase.getNotePage().editNoteContentField(content);
                    TestBase.getNotePage().clickSaveEditableNoteButton();
                WebElement title = new WebDriverWait((driver.get()), Duration.ofSeconds(20))
                        .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@style,'display')]/div["+(j+1)+"]")));
                String newTitle = (driver.get()).findElement(By.xpath("//div[contains(@style,'display')]/div["+(j+1)+"]//div//p")).getText();
                String newContent = (driver.get()).findElement(By.xpath("//div[contains(@style,'display')]/div["+(j+1)+"]//div//div")).getText();
                Assertions.assertEquals("О зиме "+j+"", newTitle, "Заголовок не совпадает с "+noteTitle+".");
                Assertions.assertEquals(content, newContent, "Текст заметки не совпадает с "+noteContent+".");
            }
            });

        }


    @AfterEach
    public void afterTest() {
        step("Удаление созданной заметки из бд и закрытие соединения", () -> {
            dbConnection.executeParameterizedUpdateWithWait("/sql/deleteNotes.sql", login, 0);
        });
        this.makeScreenshot(driver.get());
    }
}

