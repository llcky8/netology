package ru.edu.qamid.ui;

import static ru.edu.qamid.ui.utils.AllureSteps.step;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.edu.qamid.ui.steps.AuthSteps;

@RunWith(AndroidJUnit4.class)
@Epic("Запуск")
public class SmokeTest extends BaseUiTest {

    private final AuthSteps authSteps = new AuthSteps();

    @Test
    @Feature("Запуск приложения")
    @DisplayName("После splash открывается экран входа или главный экран")
    public void appLeavesSplashScreen() {
        step("Splash-экран сменяется рабочим экраном приложения", authSteps::isLoggedInAfterSplash);
    }
}
