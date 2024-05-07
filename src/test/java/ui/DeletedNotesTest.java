package ui;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.my_project_steps.MyUISteps;
import ui.pages.TestBase;

import java.time.Duration;

import static io.qameta.allure.Allure.step;

@DisplayName(value = "Удаление созданных заметок")
public class DeletedNotesTest extends TestBase {
    WebDriverWait wait = new WebDriverWait((WebDriver) driver, Duration.ofSeconds(20));
    MyUISteps myUISteps = new MyUISteps(getDriver());
    String login = "BAEVA";

    @BeforeEach
    public void start() {
        step("1. Вход в приложение", () -> {
            myUISteps.goToAuthPage();
        });
        step("2. Ввод данных существующего пользователя", () -> {
            myUISteps.setInfoToAuthAndClick(login, "Start123");
        });
        step("3. Проверка входа в приложение ", () -> {
            myUISteps.clickLoginAndCheck();
        });
        step("4. Создание множества заметок", () -> {
            myUISteps.createSoManyNotes(3);
        });

    }

    @Test
    @Tag("Ui")
    @DisplayName(value = "Удаление всех имеющихся заметок")
    public void deleteNotesHappyPathTest () {
        step("5. Удаление всех заметок", () -> {
            myUISteps.deleteAllNotes();
        });
        step("6. Проверка наличия заметок", () -> {
            Assertions.assertEquals(myUISteps.countNote(), 0, "Заметки еще остались!");
        });
    }

    @AfterEach
    public void afterTest() {
        this.makeScreenshot(driver.get());
    }

}
