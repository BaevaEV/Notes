package api.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Note {

    private int id;
    private String name;
    private String content;
    private String color;
    private int priority;


    public Note(Integer id, String name, String content, String color, int priority) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.color = color;
        this.priority = priority;
    }

}
