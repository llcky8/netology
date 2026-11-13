package ru.edu.qamid.ui.utils;

import io.qameta.allure.kotlin.Allure;

public class AllureUtils {

    public static void step(String name, Runnable action) {
        Allure.step(name, context -> {
            action.run();
            return null;
        });
    }
}
