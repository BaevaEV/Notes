package ui;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.db.DBConnection;
import ui.my_project_steps.MySteps;
import ui.pages.TestBase;
import ui.utils.Color;
import ui.utils.Datafaker;

import java.time.Duration;

import static io.qameta.allure.Allure.step;


@DisplayName(value = "Редактирование созданных заметок")
public class EditNotesTest extends TestBase {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    MySteps mySteps = new MySteps();
    Datafaker dataFaker = new Datafaker();
    String title = dataFaker.generateTextTitle();
    String content = dataFaker.generateTextContent();
    Color colorUtility = new Color();
    DBConnection dbConnection = new DBConnection();
    String login = "BAEVA";
    String columnName = "id";
    String sqlSelectFindNote = "select id from nfaut.notes where user_id in(select id from nfaut.users where login = ? )  order by created desc limit 1";
    String sqlDeleteNotes = "delete from nfaut.notes where user_id in(select id from nfaut.users where login = ? )";
    String noteTitle;
    String noteColor;
    String noteContent;

    @BeforeEach
    public void start() {
        step("1. Вход в приложение", () -> {
            authPage.goToAuthPage();
        });
        step("2. Ввод данных существующего пользователя", () -> {
            mySteps.setInfoToAuthAndClick(login, "Start123");
        });
        step("3. Проверка входа в приложение ", () -> {
            authPage.clickLoginButton();
            Assertions.assertTrue(mainPage.addNoteButtonDisplayed());
        });
    }

        @Test
        @DisplayName(value = "Редактирование созданной заметки")
        public void editNewNotes() {
            for (int i = 0; i < 3; i++) {
                step("1. Создание заметки и заполнение информации", () -> {
                    mainPage.addNoteButtonClick();
                    mySteps.fillInfoForNote(title, content, "Красный");
                });
                step("2. Сохранение заметки", () -> {
                    notePage.saveNewNote();
                });
                step("3. Получение id созданной заметки и заполненных данных с главной страницы", () -> {
                    String noteId = dbConnection.executeParameterizedQueryWithWait(sqlSelectFindNote, columnName, login, 200);
                    noteTitle = mainPage.getNoteTitle(noteId);
                    noteContent = mainPage.getNoteContent(noteId);
                    noteColor = mainPage.getNoteColor(noteId).replace("-color","");
                });
                step("3. Сравнение и проверка отображаемых данных на соответствие изначально введенным", () -> {
                    Assertions.assertEquals(title, noteTitle, "Заголовок не совпадает с "+noteTitle+".");
                    Assertions.assertEquals(content, noteContent, "Текст заметки не совпадает с "+noteContent+".");
                    Assertions.assertEquals(colorUtility.getColorStyle("Красный"), noteColor, "Цвет не совпадает.");
                });
            }
            for (int j = 0; j < 3; j++) {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@style,'display')]/div["+(j+1)+"]/div[2]/img[1]"))).click();
                notePage.clearNoteTitleField();
                notePage.editNoteTitleField("О зиме "+j+"");
                notePage.clearNoteContentField();
                notePage.editNoteContentField(content);
                notePage.clickSaveEditableNoteButton();
                WebElement title = new WebDriverWait(driver, Duration.ofSeconds(20))
                        .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@style,'display')]/div["+(j+1)+"]")));
                String newTitle = driver.findElement(By.xpath("//div[contains(@style,'display')]/div["+(j+1)+"]//div//p")).getText();
                String newContent = driver.findElement(By.xpath("//div[contains(@style,'display')]/div["+(j+1)+"]//div//div")).getText();
                Assertions.assertEquals("О зиме "+j+"", newTitle, "Заголовок не совпадает с "+noteTitle+".");
                Assertions.assertEquals(content, newContent, "Текст заметки не совпадает с "+noteContent+".");
            }

        }


    @AfterEach
    public void afterTest() {
        step("Удаление созданной заметки из бд и закрытие соединения", () -> {
            dbConnection.executeParameterizedUpdateWithWait(sqlDeleteNotes, login, 0);
            dbConnection.closeConnection();
        });
    }
}

