package com.framework.utils;

import com.github.javafaker.Faker;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestDataGenerator {

    private static final Faker faker = new Faker();

    public static String generateEmail() {
        return faker.internet().emailAddress();
    }

    public static String generateFirstName() {
        return faker.name().firstName();
    }

    public static String generateLastName() {
        return faker.name().lastName();
    }

    public static int generatePrice(int min, int max) {
        return faker.number().numberBetween(min, max);
    }

    public static String generateFutureDate(int daysFromNow) {
        LocalDate date = LocalDate.now().plusDays(daysFromNow);
        return date.format(DateTimeFormatter.ISO_DATE);
    }

    public static boolean generateBoolean() {
        return faker.bool().bool();
    }
}
