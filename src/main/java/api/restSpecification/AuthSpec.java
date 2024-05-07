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
    public static RequestSpecification requestSpecification;
    public static ResponseSpecification responseSpecification;

    public static void createRequestSpecAuth(Map<String , String> loginParams) {
        requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URI)
                .setBasePath(LOGIN_PATH)
                .addParams(loginParams)
                .build();
    }

    public static void createResponseSpecAuth(int status) {
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(status)
                .build();
    }

}