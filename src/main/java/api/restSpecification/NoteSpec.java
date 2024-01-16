package api.restSpecification;

import api.dto.UserCreationDTO;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static api.Properties.*;


public class NoteSpec {
    private RequestSpecification requestSpecification;
    private ResponseSpecification responseSpecification;

    public void createRequestSpecNote(UserCreationDTO userCreationDTO) {
        this.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setBasePath(PATH_REGISTRATION)
                .setContentType(ContentType.JSON)
                .setBody(userCreationDTO)
                .build();
    }

        public void createResponseSpecNote(int status) {
            this.responseSpecification = new ResponseSpecBuilder()
                    .expectStatusCode(status)
                    .build();

        }

    public void postRegistration(){
        RestAssured.given(requestSpecification).log().all()
                .post()
                .then().log().all().spec(responseSpecification)
                .extract().body();
    }
}
