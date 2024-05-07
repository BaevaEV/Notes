package api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NoteDTO {
    private Integer id;
    private String name;
    private String content;
    private String color;
    private Integer priority;


}


