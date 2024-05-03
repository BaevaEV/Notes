package ui;

import org.junit.jupiter.api.*;
import ui.my_project_steps.MySteps;
import ui.pages.TestBase;
import utils.Color;
import utils.Datafaker;

import java.util.Set;

import static io.qameta.allure.Allure.step;


@DisplayName(value = "Создание новой заметки")
public class CreateNewNoteTest extends TestBase {
    MySteps mySteps = new MySteps();
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
            mySteps.goToAuthPage();
        });
        step("2. Ввод данных существующего пользователя", () -> {
            mySteps.setInfoToAuthAndClick(login, "1234");
        });
        step("3. Проверка входа в приложение ", () -> {
            mySteps.clickLoginAndCheck();
        });
    }

    @Test
    @Tag("Ui")
    @DisplayName(value = "Успешное создание заметки")
    public void createNoteHappyPathTest() {
        step("1. Создание заметки и заполнение информации", () -> {
            mySteps.clickAddNoteButton();
            mySteps.fillInfoForNote(title, content, "Красный");
        });
        step("2. Сохранение заметки", () -> {
            mySteps.clickSaveNoteButton();
        });
        step("3. Получение id созданной заметки и заполненных данных с главной страницы", () -> {
            String noteId = dbConnection.executeParameterizedQueryWithWait("/sql/selectFindNote.sql", "id", login, 200);
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
    @Tag("Ui")
    @DisplayName(value = "Создание заметок с разными цветами")
    public void createNotesWithDifferentColorsTest() {
        Set<String> colorNames = colorUtility.getColorMapping().keySet();
        for (String colorName : colorNames) {
            step("1. Создание заметки и заполнение информации", () -> {
                mySteps.clickAddNoteButton();
                mySteps.fillInfoForNote("Заметка " + colorName + "", content, colorName);
            });
            step("2. Сохранение заметки", () -> {
                mySteps.clickSaveNoteButton();
            });
            step("3. Получение id созданной заметки и заполненных данных с главной страницы", () -> {
                String noteId = dbConnection.executeParameterizedQueryWithWait("/sql/selectFindNote.sql", "id", login, 250);
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
            dbConnection.executeParameterizedUpdateWithWait("/sql/deleteNotes.sql", login, 0);
        });
        this.makeScreenshot(driver);
    }

}
