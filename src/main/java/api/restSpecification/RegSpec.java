package api.restSpecification;

import api.dto.UserDTO;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static api.Properties.BASE_URI;
import static api.Properties.PATH_REGISTRATION;


public class RegSpec {
    public static RequestSpecification requestSpecification;
    public static ResponseSpecification responseSpecification;

    public static void createRequestSpecReg(UserDTO userDTO){
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setBasePath(PATH_REGISTRATION)
                .setContentType(ContentType.JSON)
                .setBody(userDTO)
                .build();
    }

    public static void createResponseSpecReg(int status){
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(status)
                .build();
    }

    public static void createJsonSchemaValidationSpec(String schemaPath) {
        RestAssured.defaultParser = Parser.JSON;
        responseSpecification.expect().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
    }


}
