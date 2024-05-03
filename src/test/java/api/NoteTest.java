package api;

import api.dto.Note;
import api.restSpecification.AuthSpec;
import api.restSpecification.NoteSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

@DisplayName("Создание и удаление заметок")
public class NoteTest {
    AuthSpec authSpec = new AuthSpec();
    NoteSpec noteSpec = new NoteSpec();
    String accessToken;


    @BeforeEach
    public void before() {
        authSpec.createRequestSpecAuth();
        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("username", "Katerina");
        loginParams.put("password", "1234");
        accessToken = authSpec.getAccessToken(loginParams);

    }

    @Test
    @Tag("Api")
    @DisplayName("Получение заметок пользователя")
    public void getUsersNotesByLoginTest() {
        String login = "BAEVA";
        given()
                .spec(noteSpec.createRequestSpecGetNote(accessToken, login))
                .when()
                .get()
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    @Tag("Api")
    @DisplayName("Создание заметки")
    public void createNewNoteTest() {
        String login = "Katerina";
        Note note = new Note(null,"Заметочка", "За ме то чка", "red", 1);
        List<Note> noteList = Collections.singletonList(note);
        given()
                .spec(noteSpec.createRequestSpecGetNote(accessToken, login))
                .body(noteList)
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(201);
    }

    @Test
    @Tag("Api")
    @DisplayName("Редактирование заметки")
    public void editNoteTest() {
        String login = "Katerina";
        Note note = new Note(null,"Заметка", "За ме то чка", "red", 1);
        List<Note> noteList = Collections.singletonList(note);
        given()
                .spec(noteSpec.createRequestSpecGetNote(accessToken, login))
                .body(noteList)
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(201);
    }

}