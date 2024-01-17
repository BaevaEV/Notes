package api.dto;


import lombok.Data;

@Data
public class Note {

    private String name;
    private String content;
    private String color;
    private int priority;


    public Note(String name, String content, String color, int priority) {
        this.name = name;
        this.content = content;
        this.color = color;
        this.priority = priority;
    }

    public Note() {

    }
}
