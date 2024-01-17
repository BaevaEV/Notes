package ui;

import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.db.DBConnection;
import ui.my_project_steps.MySteps;
import ui.pages.TestBase;
import utils.Datafaker;

import static io.qameta.allure.Allure.step;

@DisplayName(value = "Регистрация нового пользователя")
public class RegistrationTest extends TestBase {
    MySteps mySteps = new MySteps();
    Datafaker dataFaker = new Datafaker();
    String login = dataFaker.generateName();
    String sqlSelect = "select * from nfaut.users where login = ?";
    String deleteQueryUsers = "delete from nfaut.users where login = ?";
    String deleteQueryUsersRoles = "delete from nfaut.users_roles where user_id in(select id from nfaut.users where login = ? )";
    String columnName = "id";
    DBConnection dbConnection = new DBConnection();
    String userId;

    @Test
    @DisplayName(value = "Успешная регистрация нового пользователя")
    @Step ("registrationHappyPathTest")
    public void registrationHappyPathTest(){
        step("1. Вход в приложение", () -> {
            authPage.goToAuthPage();
        });
        step("2. Ввод данных", () -> {
            registryPage.clickRegestryButton();
            mySteps.setInfoToRegistry(login, "123456", "my_good_tests@mail.com");
        });
        step("3. Проверка созданного пользователя в БД ", () -> {
            registryPage.clickCreateButton();
            userId = dbConnection.executeParameterizedQueryWithWait(sqlSelect, columnName, login,250);
            Assertions.assertNotNull(userId, "Пользователь не найден");
        });

    }


    @AfterEach
    public void afterTest() {
        this.makeScreenshot(driver);
        try {
            dbConnection.executeParameterizedUpdateWithWait(deleteQueryUsersRoles, login, 250);
            dbConnection.executeParameterizedUpdateWithWait(deleteQueryUsers, login, 250);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConnection.closeConnection();
        }
    }

}
