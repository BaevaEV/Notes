package utils;

import net.datafaker.Faker;

import java.util.Locale;

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

    public String generateRole(){
        Faker faker = new Faker(new Locale("ru"));
        return faker.beer().brand();
    }

}
