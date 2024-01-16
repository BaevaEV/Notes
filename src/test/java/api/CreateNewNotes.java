package api;

import api.dto.User;
import api.dto.UserCreationDTO;
import api.restSpecification.NoteSpec;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Создание и удаление заметок")
public class CreateNewNotes {
    private User newUser;
    private UserCreationDTO userCreationDTO;
    NoteSpec noteSpec = new NoteSpec();


    @Before
    public void before() {
        newUser = new User();
        newUser = newUser.generateUser();
    }

    @Test
    @DisplayName("Создание и удаление заметки, проверка schema.json")
    public void createNewUserAndNote() {
        userCreationDTO = UserCreationDTO.builder().login(newUser.getLogin())
                .password(newUser.getPassword())
                .email((newUser.getEmail()))
                .roles(newUser.getRoles())
                .notes(newUser.getNotes())
                .build();

        noteSpec.createRequestSpecNote(userCreationDTO);
        noteSpec.createResponseSpecNote(201);
        noteSpec.postRegistration();
    }

}
