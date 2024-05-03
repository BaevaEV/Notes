package api.restSpecification;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static api.Properties.*;

public class NoteSpec {
    private RequestSpecification requestSpecification;
    private ResponseSpecification responseSpecification;

    public RequestSpecification createRequestSpecGetNote(String accessToken, String login) {
        return new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + accessToken)
                .setBaseUri(BASE_URI)
                .setBasePath(USER_PATH+login+"/notes")
                .setContentType(ContentType.JSON)
                .build();
    }

    public void createResponseSpecNote(int status) {
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(status)
                .build();
    }


}
