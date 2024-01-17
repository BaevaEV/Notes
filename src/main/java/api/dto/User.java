package api.dto;

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
        Datafaker dataFaker = new Datafaker();
        String roleName = dataFaker.generateRole();

        Role defaultRole = new Role(2, roleName); // Передаем id и name
        List<Role> defaultRolesList = new ArrayList<>();
        defaultRolesList.add(defaultRole);
        this.roles = defaultRolesList;


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

    public User takeMyLogin() {
        String login = "BAEVA";

        User myUser = new User();
        myUser.setLogin(login);
        myUser.setPassword("Start123");
        myUser.setEmail("testtest@com");
        myUser.setDefaultRoles();
        return myUser;
    }

    public List<Note> setNewNote(String name, String content, String color, int priority) {
        Note newNote = new Note();
        newNote.setName(name);
        newNote.setContent(content);
        newNote.setColor(color);
        newNote.setPriority(priority);

        List<Note> newListNote = new ArrayList<>();
        newListNote.add(newNote);

        this.notes = newListNote;

        return newListNote;
    }

    public void setDefaultNotes() {
        Datafaker dataFaker = new Datafaker();
        String textTitle = dataFaker.generateTextTitle();
        String textContent = dataFaker.generateTextTitle();

        Note defaultNote = new Note(textTitle, textContent, "Красный", 1);
        List<Note> defaultNotesList = new ArrayList<>();
        defaultNotesList.add(defaultNote);
        this.notes = defaultNotesList;
    }

}