package api.dto;

import java.util.List;

public class UserDTO {
    private String login;
    private String password;
    private String email;
    private List<NoteDTO> notes;
    private List<RoleDTO> roles;



    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<NoteDTO> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteDTO> notes) {
        this.notes = notes;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }

    // Пример использования для удобства создания объекта
    public static UserDTO createUser(String login, String password, String email,
                                     List<NoteDTO> notes, List<RoleDTO> roles) {
        UserDTO user = new UserDTO();
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        user.setNotes(notes);
        user.setRoles(roles);
        return user;
    }
}


