package api;

import api.dto.NoteDTO;
import api.pojo.Note;
import api.restSpecification.NoteSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ui.my_project_steps.MyApiSteps;

import static ui.my_project_steps.MyApiSteps.getMethod;
import static ui.my_project_steps.MyApiSteps.postMethod;

@DisplayName("Создание и удаление заметок")
public class NoteTest {
    private Note newNote;
    private NoteDTO noteDTO;


    @BeforeEach
    public void before() {
        newNote = new Note();
        newNote = newNote.generateNote();

    }


    @Test
    @Tag("Api")
    @DisplayName("Создание заметки")
    public void createNoteTest() {
        String login = "Katerina";
        String password = "1234";
        noteDTO = NoteDTO.builder()
                .name(newNote.getName())
                .priority(0)
                .build();
        String accessToken = MyApiSteps.getAccessToken(login, password);
        NoteSpec.createRequestSpecCreateNote(login, accessToken, noteDTO);
        NoteSpec.createResponseSpecNote(201);
        postMethod(NoteSpec.requestSpecification, NoteSpec.responseSpecification);
        NoteSpec.createJsonSchemaValidationSpec("schemes/new_note_schema.json");

    }

    @Test
    @Tag("Api")
    @DisplayName("Все заметки пользователя")
    public void getUsersNotesByLoginTest() {
        String name = "Заметка";
        int count = 1000;
        String login = "Katerina";
        String password = "1234";
        String accessToken = MyApiSteps.getAccessToken(login, password);
        NoteSpec.createRequestSpecGetNote(login, accessToken, name, count);
        NoteSpec.createResponseSpecNote(200);
        getMethod(NoteSpec.requestSpecification, NoteSpec.responseSpecification);

    }



}