package ru.edu.qamid.ui;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.edu.qamid.ui.steps.AuthSteps;

@RunWith(AndroidJUnit4.class)
@Epic("Запуск")
public class SmokeTest extends BaseUiTest {

    private final AuthSteps authSteps = new AuthSteps();

    @Test
    @Feature("Старт приложения")
    @DisplayName("После splash открывается экран входа или главный экран")
    @Description("После запуска приложение уходит с экрана загрузки на экран входа или на главный экран")
    public void appLeavesSplashScreen() {
        authSteps.checkSplashFinished();
    }
}
