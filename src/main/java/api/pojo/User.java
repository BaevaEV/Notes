package api.pojo;

import lombok.Data;
import utils.Datafaker;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {

    private String login;
    private String password;
    private String email;
    private List<Role> roles;
    private List<Note> notes;

    public void setDefaultRoles() {
        Role defaultRole = new Role();
        defaultRole.setId(2);
        defaultRole.setName("ROLE_USER");

        List<Role> defaultListRole = new ArrayList<>();
        defaultListRole.add(defaultRole);

        this.roles = defaultListRole;
    }

    public User generateUser() {
        Datafaker dataFaker = new Datafaker();
        String login = dataFaker.generateName();

        User newUser = new User();
        newUser.setLogin(login);
        newUser.setPassword("123456");
        newUser.setEmail("testtest@com");
        newUser.setDefaultRoles();
        newUser.setDefaultNotes();
        return newUser;
    }

    public void setDefaultNotes() {

        Note defaultNote = new Note();
        List<Note> defaultNotesList = new ArrayList<>();
        defaultNotesList.add(defaultNote);
        this.notes = defaultNotesList;
    }

}