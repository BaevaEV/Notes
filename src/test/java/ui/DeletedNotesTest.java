package ui;

import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.my_project_steps.MySteps;
import ui.pages.TestBase;

import java.time.Duration;

import static io.qameta.allure.Allure.step;

@DisplayName(value = "Удаление созданных заметок")
public class DeletedNotesTest extends TestBase {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    MySteps mySteps = new MySteps();
    String login = "BAEVA";
    int numberOfNotesToCreate = 5;

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
        step("4. Создание множества заметок", () -> {
            mySteps.createSoManyNotes(3);
        });

    }

    @Test
    @DisplayName(value = "Удаление всех имеющихся заметок")
    public void deleteNotesHappyPathTest () {
        step("5. Удаление всех заметок", () -> {
            mySteps.deleteAllNotes();
        });
        step("6. Проверка наличия заметок", () -> {
            Assertions.assertEquals(mySteps.countNote(), 0, "Заметки еще остались!");
        });
    }

    @AfterEach
    public void afterTest() {
        this.makeScreenshot(driver);
    }

}
