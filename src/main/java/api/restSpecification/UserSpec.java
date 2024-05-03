package api.restSpecification;

import api.dto.User;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static api.Properties.BASE_URI;
import static api.Properties.USER_PATH;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class UserSpec {
    private RequestSpecification requestSpecification;
    private ResponseSpecification responseSpecification;
    private User newUser;
    private AuthSpec authSpec ;

    public UserSpec(User newUser, AuthSpec authSpec) {
        this.newUser = newUser;
        this.authSpec = authSpec;
    }

    public RequestSpecification createRequestSpecGetUser(String login, String token) {
        return new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .setBaseUri(BASE_URI)
                .setBasePath(USER_PATH+login)
                .build();
    }


    public ResponseSpecification createResponseSpecForUser(String schemaPath, int status) {
        return new ResponseSpecBuilder()
                .expectBody(matchesJsonSchemaInClasspath(schemaPath))
                .expectStatusCode(status)
                .build();
    }

    public RequestSpecification createRequestSpecDeleteUser(String login) {
        return new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + authSpec.getAccessToken())
                .setBaseUri(BASE_URI)
                .setBasePath(USER_PATH+login)
                .build();
    }

    public RequestSpecification createRequestSpecRole(String login) {
        return new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + authSpec.getRefreshAccessToken() )
                .setBaseUri(BASE_URI)
                .setBasePath(USER_PATH+login+"/role/ROLE_ADMIN")
                .build();



    }



}
