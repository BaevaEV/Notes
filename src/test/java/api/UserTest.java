//package api;
//
//import api.pojo.User;
//import api.dto.UserCreationDTO;
//import api.restSpecification.AuthSpec;
//import api.restSpecification.RegSpec;
//import api.restSpecification.UserSpec;
//import io.restassured.specification.RequestSpecification;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Tag;
//import org.junit.jupiter.api.Test;
//import ui.my_project_steps.MyApiSteps;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static io.restassured.RestAssured.given;
//import static ui.my_project_steps.MyApiSteps.postMethod;
//
//@DisplayName("Проверка данных пользователя")
//public class UserTest {
//    private User newUser;
//    private UserCreationDTO userCreationDTO;
//    private RequestSpecification requestSpec;
//    private UserSpec userSpec;  // не инициализируем здесь
//    RegSpec regSpec = new RegSpec();
//    AuthSpec authSpec = new AuthSpec();
//
//    @BeforeEach
//    public void before() {
//        authSpec.createRequestSpecAuth();
//        userSpec = new UserSpec(newUser, authSpec);
//        newUser = new User();
//        newUser = newUser.generateUser();
//        userCreationDTO = UserCreationDTO.builder().login(newUser.getLogin())
//                .password(newUser.getPassword())
//                .email((newUser.getEmail()))
//                .roles(newUser.getRoles())
//                .notes(newUser.getNotes())
//                .build();
//
//        regSpec.createRequestSpecReg(userCreationDTO);
//        regSpec.createResponseSpecReg(201);
//        postMethod(RegSpec.requestSpecification, RegSpec.responseSpecification);
//
//        Map<String, String> loginParams = new HashMap<>();
//        loginParams.put("username", newUser.getLogin());
//        loginParams.put("password", newUser.getPassword());
//        authSpec.getAccessToken(loginParams);
//        authSpec.createRequestSpecAuth();
//
//    }
//
//
//    @Test
//    @Tag("Api")
//    @DisplayName("Получение инфо созданного пользователя")
//    public void getUserByLoginTest() {
//        String login = "BAEVA";
//        String password = "Start123";
//
//        given()
//                .spec(userSpec.createRequestSpecGetUser(login, password))
//                .when()
//                .get()
//                .then()
//                .log().all()
//                .statusCode(403);
//    }
//
//    @Test
//    @Tag("Api")
//    @DisplayName("Удаление пользователя. Нет прав на удаление")
//    public void deleteUserByLogin() {
//        String login = newUser.getLogin();
//        String password = newUser.getPassword();
//        given()
//                .spec(userSpec.createRequestSpecDeleteUser(login))
//                .when()
//                .delete()
//                .then()
//                .statusCode(403);
//    }
//
//    @Test
//    @Tag("Api")
//    @DisplayName("Неуспешная смена роли созданного пользователя")
//    public void putNewRoleForUserByLogin() {
//        String login = newUser.getLogin();
//        given()
//                .spec(userSpec.createRequestSpecRole(login))
//                .log().all()
//                .when()
//                .put()
//                .then().log().all()
//                .spec(userSpec.createResponseSpecForUser("schemes/put_role_response_schema.json", 404));
//    }
//}
