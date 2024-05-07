package api;

import api.dto.UserDTO;
import api.restSpecification.AuthSpec;
import api.restSpecification.RegSpec;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static ui.my_project_steps.MyApiSteps.getAccessToken;
import static ui.my_project_steps.MyApiSteps.postMethod;

@DisplayName(value = "Проверка авторизации")
public class AuthorizationTest {
    private UserDTO userDTO;


    @Test
    @Tag("Api")
    @DisplayName("Авторизация существующего пользователя")
    public void authorizationRealClientTest() {
        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("username", "Katerina");
        loginParams.put("password", "1234");
        AuthSpec.createRequestSpecAuth(loginParams);
        AuthSpec.createResponseSpecAuth(201);
        postMethod(AuthSpec.requestSpecification, AuthSpec.responseSpecification);
    }

    @Test
    @Tag("Api")
    @DisplayName("Авторизация несуществующего пользователя")
    public void authorizationNewClientTest() {
        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("username", "Katerok");
        loginParams.put("password", "1234");
        AuthSpec.createRequestSpecAuth(loginParams);
        AuthSpec.createResponseSpecAuth(403);
        postMethod(AuthSpec.requestSpecification, AuthSpec.responseSpecification);
    }


    @Test
    @Tag("Api")
    @DisplayName("Авторизация только с логином")
    public void authorizationNewClientLoginTest() {
        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("username", "Katerina");
        AuthSpec.createRequestSpecAuth(loginParams);
        AuthSpec.createResponseSpecAuth(403);
        postMethod(AuthSpec.requestSpecification, AuthSpec.responseSpecification);

    }

}