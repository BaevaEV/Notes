package api;

import api.dto.User;
import api.dto.UserCreationDTO;
import api.restSpecification.AuthSpec;
import api.restSpecification.NoteSpec;
import api.restSpecification.RegSpec;
import api.restSpecification.UserSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@DisplayName("Создание и удаление заметок")
public class NoteTest {
    private User newUser;
    private UserCreationDTO userCreationDTO;
    private UserSpec userSpec;
    RegSpec regSpec = new RegSpec();
    AuthSpec authSpec = new AuthSpec();
    NoteSpec noteSpec = new NoteSpec();


    @BeforeEach
    public void before() {
        authSpec.createRequestSpecAuth();
        noteSpec = new NoteSpec(newUser, authSpec);
        newUser = new User();
        newUser = newUser.generateUser();
        userCreationDTO = UserCreationDTO.builder().login(newUser.getLogin())
                .password(newUser.getPassword())
                .email((newUser.getEmail()))
                .roles(newUser.getRoles())
                .notes(newUser.getNotes())
                .build();

        regSpec.createRequestSpecReg(userCreationDTO);
        regSpec.createResponseSpecReg(201);
        regSpec.postRegistration();

        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("username", newUser.getLogin());
        loginParams.put("password", newUser.getPassword());
        authSpec.getAccessToken(loginParams);
        authSpec.createRequestSpecAuth();

    }

    @Test
    @DisplayName("Получение заметок пользователя")
    public void getUsersNotesByLoginTest() {
        String login = newUser.getLogin();
        given()
                .spec(noteSpec.createRequestSpecGetNote(login))
                .when()
                .get()
                .then()
                .log().all()
                .statusCode(403);
    }

//    @Test
//    @DisplayName("Получение заметок пользователя")
//    public void createUsersNoteByLoginTest() {
//        String login = newUser.getLogin();
//        given()
//                .spec(noteSpec.createRequestSpecCreateNote(login))
//                .when()
//                .post()
//                .then()
//                .log().all()
//                .statusCode(403);
//    }




}