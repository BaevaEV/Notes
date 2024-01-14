package api.dto;

public class NoteDTO {
    private String name;
    private String content;
    private String color;
    private int priority;

    // конструкторы, геттеры и сеттеры

    // Пример использования для удобства создания объекта
    public NoteDTO(String name, String content, String color, int priority) {
        this.name = name;
        this.content = content;
        this.color = color;
        this.priority = priority;
    }
}
