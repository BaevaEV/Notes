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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class AuthSpec {
    private String access_token;
    private String refresh_token;
    private RequestSpecification requestSpecification;
    private ResponseSpecification responseSpecification;

    public void createRequestSpecAuth() {
        requestSpecification = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + access_token)
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

        access_token = responseBody.get("access_token");
        refresh_token = responseBody.get("refresh_token");
        Assertions.assertNotNull(access_token, "Не был получен access_token");
        return access_token;
    }

    public String getRefreshAccessToken() {
        return refresh_token;
    }

    public String getAccessToken() {
        return access_token;
    }

    public void getAccessTokenBadRequest(Map<String , String> loginParams) {
         given()
                .log().all()
                .params(loginParams)
                .get(BASE_URI + LOGIN_PATH)
                 .then().statusCode(not(equalTo(201)));
    }
}