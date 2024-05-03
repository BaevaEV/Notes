package api.dto;

import lombok.Data;

@Data
public class Role {
    private int id;
    private String name;

    public Role(int id, String name) {
        this.name = name;
        this.id = id;

    }

}
