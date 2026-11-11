package ru.edu.qamid.ui.data;

import java.util.UUID;

public class TestData {

    public static final String VALID_LOGIN = "login2";
    public static final String VALID_PASSWORD = "password2";

    public static final String CATEGORY = "Объявление";
    public static final String DESCRIPTION = "Создано автотестом";

    public static String randomLogin() {
        return "user_" + random(8);
    }

    public static String randomPassword() {
        return random(12);
    }

    public static String randomNewsTitle() {
        return "Автотест " + random(10);
    }

    private static String random(int length) {
        return UUID.randomUUID().toString().replace("-", "").substring(0, length);
    }
}
