package ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.db.DBConnection;
import ui.my_project_steps.MySteps;
import ui.pages.TestBase;
import ui.utils.Datafaker;

import static io.qameta.allure.Allure.step;


@DisplayName(value = "Авторизация пользователя")
public class AuthorizationTest extends TestBase {
    MySteps mySteps = new MySteps();
    Datafaker dataFaker = new Datafaker();
    String newLogin = dataFaker.generateName();
    DBConnection dbConnection = new DBConnection();
    String deleteQueryUsers = "delete from nfaut.users where login = ?";
    String deleteQueryUsersRoles = "delete from nfaut.users_roles where user_id in(select id from nfaut.users where login = ? )";
    String login = "BAEVA";


    @Test
    @DisplayName(value = "Успешная авторизация существующего пользователя")
    public void authHappyPathTest (){
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
    @DisplayName(value = "Успешная авторизация нового пользователя")
    public void authNewClientHappyPathTest (){
        step("1. Вход в приложение", () -> {
            authPage.goToAuthPage();
        });
        step("2. Регистраци нового клиента", () -> {
            registryPage.clickRegestryButton();
            mySteps.setInfoToRegistry(newLogin, "123456", "my_good_tests@mail.com");
            registryPage.clickCreateButton();
        });
        step("2. Авторизация нового клиента", () -> {
            authPage.goToAuthPage();
            mySteps.setInfoToAuthAndClick(newLogin, "123456");
        });
        step("2. Проверка входа в Заметки и удаление нового пользователя в БД", () -> {
            Assertions.assertTrue(mainPage.addNoteButtonDisplayed());
            dbConnection.executeParameterizedUpdateWithWait(deleteQueryUsersRoles, newLogin,250);
            dbConnection.executeParameterizedUpdateWithWait(deleteQueryUsers, newLogin,250);
        });
    }

    @AfterEach
    public void afterTest() {
        dbConnection.closeConnection();
        this.makeScreenshot(driver);
    }

}
