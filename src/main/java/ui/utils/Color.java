package ui.utils;

import java.util.HashMap;
import java.util.Map;

public class Color {

    private final Map<String, int[]> colorMapping = new HashMap<>();

    public Color() {
        colorMapping.put("Красный", new int[]{242, 139, 130});
        colorMapping.put("Оранжевый", new int[]{251, 188, 4});
        colorMapping.put("Желтый", new int[]{255, 244, 117});
        colorMapping.put("Зеленый", new int[]{204, 255, 144});
        colorMapping.put("Коричневый", new int[]{230, 201, 168});
        colorMapping.put("Синий", new int[]{174, 203, 250});
        colorMapping.put("Бирюзовый", new int[]{135, 245, 208});
        colorMapping.put("Фиолетовый", new int[]{215, 174, 251});
        colorMapping.put("Розовый", new int[]{253, 207, 232});
    }

    public String getColorStyle(String colorName) {
        if (colorMapping.containsKey(colorName)) {
            int[] rgbValues = colorMapping.get(colorName);
            return String.format("background: rgb(%d, %d, %d);", rgbValues[0], rgbValues[1], rgbValues[2]);
        } else {
            return "Неверное имя цвета";
        }
    }

    public Map<String, int[]> getColorMapping() {
        return colorMapping;
    }
}