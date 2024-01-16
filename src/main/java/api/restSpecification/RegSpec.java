package api.restSpecification;

import api.dto.UserCreationDTO;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static api.Properties.BASE_URI;
import static api.Properties.PATH_REGISTRATION;


public class RegSpec {
    private RequestSpecification requestSpecification;
    private ResponseSpecification responseSpecification;

    public void createRequestSpecReg(UserCreationDTO userCreationDTO){
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setBasePath(PATH_REGISTRATION)
                .setContentType(ContentType.JSON)
                .setBody(userCreationDTO)
                .build();
    }

    public void createResponseSpecReg(int status){
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(status)
                .build();
    }

    public void postRegistration(){
        RestAssured.given(requestSpecification).log().all()
                .post()
                .then().log().all().spec(responseSpecification);
    }

}
