package api.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class Role {

    @EqualsAndHashCode.Include private Integer id;
    @EqualsAndHashCode.Exclude private String name;

    public Role() {
    }

}
