package api;

import api.restSpecification.AuthSpec;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@DisplayName(value = "Проверка авторизации")
public class AuthorizationTest {
    AuthSpec authSpec = new AuthSpec();



    @Test
    @DisplayName("Авторизация существующего пользователя")
    public void authorizationRealClientTest() {
        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("username", "BAEVA");
        loginParams.put("password", "Start123");
        authSpec.getAccessToken(loginParams);
        authSpec.createRequestSpecAuth();
        authSpec.createResponseSpecAuth(201);
    }

    @Test
    @DisplayName("Авторизация несуществующего пользователя")
    public void authorizationNewClientTest() {
        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("username", "EBAEVA");
        loginParams.put("password", "Start123");
        authSpec.getAccessTokenBadRequest(loginParams);

    }


    @Test
    @DisplayName("Авторизация только с логином")
    public void uauthorizationNewClientTest() {
        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("username", "BAEVA");
        authSpec.getAccessTokenBadRequest(loginParams);
    }

}