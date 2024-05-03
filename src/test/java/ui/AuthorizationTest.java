package ui;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ui.my_project_steps.MySteps;
import ui.pages.TestBase;
import utils.Datafaker;

import static io.qameta.allure.Allure.step;
@Execution(ExecutionMode.CONCURRENT)
@DisplayName(value = "Авторизация пользователя")
public class AuthorizationTest extends TestBase {
    MySteps mySteps = new MySteps();
    Datafaker dataFaker = new Datafaker();
    String newLogin = dataFaker.generateName();
    String login = "Katerina";


    @Test
    @Tag("Auth")
    @DisplayName(value = "Авторизация существующего пользователя")
    public void authHappyPathTest (){
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
    @Tag("Auth")
    @DisplayName(value = "Авторизация нового пользователя")
    public void authNewClientHappyPathTest (){
        step("1. Вход в приложение", () -> {
            mySteps.goToAuthPage();
        });
        step("2. Регистраци нового клиента", () -> {
            mySteps.pushButtonRegistry();
            mySteps.setInfoToRegistry(newLogin, "1234", "my_good_tests@mail.com");
            mySteps.pushButtonRegistryCreate();
        });
        step("2. Авторизация нового клиента", () -> {
            mySteps.setInfoToAuthAndClick(newLogin, "1234");
        });
        step("2. Проверка входа в Заметки и удаление нового пользователя в БД", () -> {
            Assertions.assertTrue(mainPage.addNoteButtonDisplayed());
            dbConnection.executeParameterizedUpdateWithWait("/sql/deleteQueryUsersRoles.sql", newLogin,250);
            dbConnection.executeParameterizedUpdateWithWait("/sql/deleteQueryUsers.sql", newLogin,250);
        });
    }

    @AfterEach
    public void afterTest() {
        this.makeScreenshot(driver);
    }

}
