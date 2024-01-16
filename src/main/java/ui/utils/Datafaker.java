package ui.utils;

import net.datafaker.Faker;

public class Datafaker {
    public String generateName(){
        Faker faker = new Faker();
        return faker.name().name();
    }

    public String generateTextTitle(){
        Faker faker = new Faker();
        return faker.artist().name();
    }

    public String generateTextContent(){
        Faker faker = new Faker();
        return faker.text().text(15);
    }
}
