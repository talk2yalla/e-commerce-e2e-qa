package com.ecom.utils;

import com.github.javafaker.Faker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class FakerUtil {

    private static final Faker faker = new Faker();

    public static int randomQuantity() {
        return faker.number().numberBetween(1, 5);
    }
    public static int randomStock() {
        return faker.number().numberBetween(10, 200);
    }

    public static BigDecimal randomPrice() {
        double val = faker.number().randomDouble(2, 5, 500); // 5.00 to 500.00
        return BigDecimal.valueOf(val).setScale(2, RoundingMode.HALF_UP);
    }

    public static String randomSku() {
        return "SKU-" + faker.number().digits(6);
    }

    public static String productName() {
        return faker.commerce().productName();
    }

    public static String productDesc() {
        return faker.lorem().sentence(6);
    }
    public static String randomEmail() {
        return "user_" + UUID.randomUUID().toString().substring(0, 8) + "@test.com";
    }

    public static String fullName() {
        return "Test User " + UUID.randomUUID().toString().substring(0, 5);
    }

    public static String randomPhone() {
        // 10 digit number string
        long num = 1000000000L + (long)(Math.random() * 9000000000L);
        return String.valueOf(num);
    }
}
