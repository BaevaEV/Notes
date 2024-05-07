package ui;

import org.junit.jupiter.api.*;
import ui.pages.TestBase;
import utils.Datafaker;

import static io.qameta.allure.Allure.step;

@DisplayName(value = "Авторизация пользователя")
public class AuthorizationTest extends TestBase {

    Datafaker dataFaker = new Datafaker();
    String newLogin = dataFaker.generateName();
    String login = "Katerina";



    @Test
    @Tag("Auth")
    @DisplayName(value = "Авторизация существующего пользователя")
    public void authHappyPathTest (){
        step("1. Вход в приложение", () -> {
        TestBase.getMySteps().goToAuthPage();
        });
        step("2. Ввод данных существующего пользователя", () -> {
        TestBase.getMySteps().setInfoToAuthAndClick(login, "1234");
        });
        step("3. Проверка входа в приложение ", () -> {
        TestBase.getMySteps().clickLoginAndCheck();
        });

    }

    @Test
    @Tag("Auth")
    @DisplayName(value = "Авторизация нового пользователя")
    public void authNewClientHappyPathTest (){
        step("1. Вход в приложение", () -> {
            TestBase.getMySteps().goToAuthPage();
        });
        step("2. Регистраци нового клиента", () -> {
            TestBase.getMySteps().pushButtonRegistry();
            TestBase.getMySteps().setInfoToRegistry(newLogin, "1234", "my_good_tests@mail.com");
            TestBase.getMySteps().pushButtonRegistryCreate();
        });
        step("2. Авторизация нового клиента", () -> {
            TestBase.getMySteps().setInfoToAuthAndClick(newLogin, "1234");
        });
        step("2. Проверка входа в Заметки и удаление нового пользователя в БД", () -> {
            Assertions.assertTrue(TestBase.getMainPage().addNoteButtonDisplayed());
            dbConnection.executeParameterizedUpdateWithWait("/sql/deleteQueryUsersRoles.sql", newLogin,250);
            dbConnection.executeParameterizedUpdateWithWait("/sql/deleteQueryUsers.sql", newLogin,250);
        });
    }

    @AfterEach
    public void afterTest() {
        this.makeScreenshot(driver.get());
    }

}
