package api.pojo;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class Note {
    @EqualsAndHashCode.Include @JsonInclude(JsonInclude.Include.NON_NULL) private Integer id;
    @EqualsAndHashCode.Include private String name;
    @EqualsAndHashCode.Exclude private String content;
    @EqualsAndHashCode.Exclude private String color;
    @EqualsAndHashCode.Exclude private Integer priority;

    public Note generateNote() {
        Note newNote = new Note();
        newNote.setName("Заметка");
        newNote.setContent("За ме то чка");
        newNote.setColor("#9e3dc2");
        newNote.setPriority(0);
        return newNote;
    }


}
