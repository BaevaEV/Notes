package api.dto;

public class Role {
    private int id;
    private String name;

    // конструктор
    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
