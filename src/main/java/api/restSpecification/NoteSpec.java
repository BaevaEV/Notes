package api.restSpecification;

import api.dto.User;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static api.Properties.*;

public class NoteSpec {
    private RequestSpecification requestSpecification;
    private ResponseSpecification responseSpecification;
    private User newUser;
    private AuthSpec authSpec ;

    public NoteSpec(User newUser, AuthSpec authSpec) {
        this.newUser = newUser;
        this.authSpec = authSpec;
    }

    public NoteSpec() {
    }

    public RequestSpecification createRequestSpecGetNote(String login) {
        return new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + authSpec.getAccessToken())
                .setBaseUri(BASE_URI)
                .setBasePath(USER_PATH+login+"/notes")
                .build();
    }

//    public RequestSpecification createRequestSpecCreateNote(String login) {
//        return new RequestSpecBuilder()
//                .addHeader("Authorization", "Bearer " + authSpec.getAccessToken())
//                .setBaseUri(BASE_URI)
//                .setBasePath(USER_PATH+login+"/notes")
//                .setBody()
//                .build();
//    }
}
