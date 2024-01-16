package api.dto;

import lombok.Data;

import java.util.List;

@Data
public class GetMeDTO {

    private String login;

    private String password;

    private String email;

    private List<Note> notes;
}
