package ui;

import io.qameta.allure.Step;
import org.junit.jupiter.api.*;
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
    String userId;

    @Test
    @Tag("Ui")
    @DisplayName(value = "Успешная регистрация нового пользователя")
    @Step ("Регистрация пользователя")
    public void registrationHappyPathTest(){
        step("1. Вход в приложение", () -> {
            mySteps.goToAuthPage();
        });
        step("2. Ввод данных", () -> {
            mySteps.pushButtonRegistry();
            mySteps.setInfoToRegistry(login, "123456", "my_good_tests@mail.com");
        });
        step("3. Проверка созданного пользователя в БД ", () -> {
            mySteps.pushButtonRegistryCreate();
            userId = dbConnection.executeParameterizedQueryWithWait("/sql/selectUserID.sql", "id", login,250);
            Assertions.assertNotNull(userId, "Пользователь не найден");
        });

    }


    @AfterEach
    public void afterTest() {
        this.makeScreenshot(driver);
        try {
            dbConnection.executeParameterizedUpdateWithWait("/sql/deleteQueryUsersRoles.sql", login,250);
            dbConnection.executeParameterizedUpdateWithWait("/sql/deleteQueryUsers.sql", login,250);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConnection.closeConnection();
        }
    }

}
