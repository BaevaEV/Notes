package api.dto;

import api.pojo.Note;
import api.pojo.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


import java.util.List;

@Builder
@Getter
@Setter
@JsonInclude
public class UserDTO {

    private String login;

    private String password;

    private String email;

    private List<Note> notes;

    private List<Role> roles;



}
