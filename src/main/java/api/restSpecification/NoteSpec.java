package api.restSpecification;

import api.dto.NoteDTO;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static api.Properties.BASE_URI;
import static api.Properties.USER_PATH;

public class NoteSpec {
    public static RequestSpecification requestSpecification;
    public static ResponseSpecification responseSpecification;


    public static void createRequestSpecCreateNote(String login, String accessToken, NoteDTO noteDTO) {
        requestSpecification = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + accessToken)
                .setBaseUri(BASE_URI)
                .setBasePath(USER_PATH+login+"/notes")
                .setContentType(ContentType.JSON)
                .setBody(new NoteDTO[]{noteDTO})
                .build();
    }

    public static void createRequestSpecGetNote(String login, String accessToken, String name, int count) {
        requestSpecification = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + accessToken)
                .setBaseUri(BASE_URI)
                .setBasePath(USER_PATH+login+"/notes")
                .addQueryParam("name", name)
                .addQueryParam("count", count)
                .setContentType(ContentType.JSON)
                .build();
    }

    public static void createRequestSpecDeleteNote(String accessToken, String login, String noteId) {
        requestSpecification = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + accessToken)
                .setBaseUri(BASE_URI)
                .setBasePath(USER_PATH+login+"/notes/"+noteId+"/")
                .setContentType(ContentType.JSON)
                .build();
    }

    public static void createResponseSpecNote(int status){
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(status)
                .build();
    }

    public static void createJsonSchemaValidationSpec(String schemaPath) {
        RestAssured.defaultParser = Parser.JSON;
        responseSpecification.expect().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
    }




}
