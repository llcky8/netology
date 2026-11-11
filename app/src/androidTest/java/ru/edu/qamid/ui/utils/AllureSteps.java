package ru.edu.qamid.ui.utils;

import io.qameta.allure.kotlin.Allure;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public final class AllureSteps {

    private AllureSteps() {
    }

    public static void step(String name, Runnable action) {
        Allure.step(name, (Function1<Allure.StepContext, Unit>) stepContext -> {
            action.run();
            return Unit.INSTANCE;
        });
    }
}
