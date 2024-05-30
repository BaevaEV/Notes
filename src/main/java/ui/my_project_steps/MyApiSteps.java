package ui.my_project_steps;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Assertions;


import static api.Properties.BASE_URI;
import static api.Properties.LOGIN_PATH;
import static io.restassured.RestAssured.given;

public class MyApiSteps {
    public static String access_token;


    @Step(value = "POST")
    public static void postMethod(RequestSpecification requestSpec, ResponseSpecification responseSpec){
        Response response = RestAssured.given(requestSpec).log().all()
                .post();
                response.then().log().all().spec(responseSpec);
    }

    @Step(value = "PUTINGGG")
    public static void putMethod(RequestSpecification requestSpec, ResponseSpecification responseSpec){
        RestAssured.given(requestSpec).log().all()
                .put()
                .then().log().all().spec(responseSpec);
    }

    @Step(value = "DELETE")
    public static void deleteMethod(RequestSpecification requestSpec, ResponseSpecification responseSpec){
        RestAssured.given(requestSpec).log().all()
                .delete()
                .then().log().all().spec(responseSpec);
    }

    @Step(value = "GET")
    public static void getMethod(RequestSpecification requestSpec, ResponseSpecification responseSpec){
        RestAssured.given(requestSpec).log().all()
                .get()
                .then().log().all().spec(responseSpec);
    }

    public static String getAccessToken(String login, String password) {
        JsonPath responseBody = given()
                .log().all()
                .params("username", login, "password", password)
                .get(BASE_URI + LOGIN_PATH)
                .jsonPath();

        access_token = responseBody.get("access_token");
//        refresh_token = responseBody.get("refresh_token");
        Assertions.assertNotNull(access_token, "Не был получен access_token");
        return access_token;
    }

}
