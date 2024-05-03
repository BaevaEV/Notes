package api;

import api.restSpecification.AuthSpec;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@DisplayName(value = "Проверка авторизации")
public class AuthorizationTest {
    AuthSpec authSpec = new AuthSpec();



    @Test
    @Tag("Api")
    @DisplayName("Авторизация существующего пользователя")
    public void authorizationRealClientTest() {
        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("username", "Katerina");
        loginParams.put("password", "1234");
        authSpec.getAccessToken(loginParams);
        authSpec.createRequestSpecAuth();
        authSpec.createResponseSpecAuth(201);
    }

    @Test
    @Tag("Api")
    @DisplayName("Авторизация несуществующего пользователя")
    public void authorizationNewClientTest() {
        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("username", "Katerina");
        loginParams.put("password", "1234");
        authSpec.getAccessTokenBadRequest(loginParams);

    }


    @Test
    @Tag("Api")
    @DisplayName("Авторизация только с логином")
    public void authorizationNewClientLoginTest() {
        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("username", "Katerina");
        authSpec.getAccessTokenBadRequest(loginParams);
    }

}