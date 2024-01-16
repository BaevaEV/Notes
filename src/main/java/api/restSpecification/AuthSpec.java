package api.restSpecification;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

import static api.Properties.BASE_URI;
import static api.Properties.LOGIN_PATH;
import static io.restassured.RestAssured.given;

public class AuthSpec {
    private String token;
    private RequestSpecification requestSpecification;
    private ResponseSpecification responseSpecification;

    public void createRequestSpecAuth() {
        requestSpecification = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URI)
                .setBasePath(LOGIN_PATH)
                .build();
    }

    public void createResponseSpecAuth(int status) {
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(status)
                .build();
    }

    public String getAccessToken(Map<String , String> loginParams) {
        JsonPath responseBody = given()
                .log().all()
                .params(loginParams)
                .get(BASE_URI + LOGIN_PATH)
                .jsonPath();

        token = responseBody.get("access_token");
        Assertions.assertNotNull(token, "Не был получен access_token");
        return token;
    }
    public String getAccessTokenBadRequest(Map<String , String> loginParams) {
        JsonPath responseBody = given()
                .log().all()
                .params(loginParams)
                .get(BASE_URI + LOGIN_PATH)
                .jsonPath();
        return null;
    }
}