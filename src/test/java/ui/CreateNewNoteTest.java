package ui;

import org.junit.jupiter.api.*;
import ui.db.DBConnection;
import ui.my_project_steps.MySteps;
import ui.pages.TestBase;
import ui.utils.Color;
import ui.utils.Datafaker;

import java.util.Set;

import static io.qameta.allure.Allure.step;


@DisplayName(value = "Создание новой заметки")
public class CreateNewNoteTest extends TestBase {
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
    @DisplayName(value = "Успешное создание заметки")
    public void createNoteHappyPathTest() {
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
            noteColor = mainPage.getNoteColor(noteId).replace("-color", "");
        });
        step("3. Сравнение и проверка отображаемых данных на соответствие изначально введенным", () -> {
            Assertions.assertEquals(title, noteTitle, "Заголовок не совпадает с " + noteTitle + ".");
            Assertions.assertEquals(content, noteContent, "Текст заметки не совпадает с " + noteContent + ".");
            Assertions.assertEquals(colorUtility.getColorStyle("Красный"), noteColor, "Цвет не совпадает.");
        });
    }


    @Test
    @DisplayName(value = "Создание заметок с разными цветами")
    public void createNotesWithDifferentColorsTest() {
        Set<String> colorNames = colorUtility.getColorMapping().keySet();
        for (String colorName : colorNames) {
            step("1. Создание заметки и заполнение информации", () -> {
                mainPage.addNoteButtonClick();
                mySteps.fillInfoForNote("Заметка " + colorName + "", content, colorName);
            });
            step("2. Сохранение заметки", () -> {
                notePage.saveNewNote();
            });
            step("3. Получение id созданной заметки и заполненных данных с главной страницы", () -> {
                String noteId = dbConnection.executeParameterizedQueryWithWait(sqlSelectFindNote, columnName, login, 250);
                noteTitle = mainPage.getNoteTitle(noteId);
                noteContent = mainPage.getNoteContent(noteId);
                noteColor = mainPage.getNoteColor(noteId).replace("-color", "");
            });
            step("4. Сравнение и проверка отображаемых данных на соответствие изначально введенным", () -> {
                Assertions.assertEquals("Заметка " + colorName, noteTitle, "Заголовок не совпадает.");
                Assertions.assertEquals(content, noteContent, "Содержание не совпадает.");
                Assertions.assertEquals(colorUtility.getColorStyle(colorName), noteColor, "Цвет не совпадает.");
            });
        }
    }


    @AfterEach
    public void afterTest() {
        step("Удаление созданной заметки из бд и закрытие соединения", () -> {
            dbConnection.executeParameterizedUpdateWithWait(sqlDeleteNotes, login, 0);
            dbConnection.closeConnection();
        });
        this.makeScreenshot(driver);
    }

}
