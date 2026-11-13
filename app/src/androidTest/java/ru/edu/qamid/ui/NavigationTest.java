package ru.edu.qamid.ui;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.edu.qamid.ui.data.TestData;
import ru.edu.qamid.ui.steps.AuthSteps;
import ru.edu.qamid.ui.steps.NavigationSteps;
import ru.edu.qamid.ui.steps.NewsSteps;

@RunWith(AndroidJUnit4.class)
@Epic("Переходы между экранами")
public class NavigationTest extends BaseUiTest {

    private final AuthSteps authSteps = new AuthSteps();
    private final NavigationSteps navigationSteps = new NavigationSteps();
    private final NewsSteps newsSteps = new NewsSteps();

    @Before
    public void logIn() {
        authSteps.makeSureLoggedIn(TestData.VALID_LOGIN, TestData.VALID_PASSWORD);
    }

    @Test
    @Feature("Главное меню")
    @DisplayName("Пункт меню текущего экрана недоступен")
    @Description("В главном меню нельзя выбрать пункт экрана, на котором сейчас находимся")
    public void currentScreenMenuItemIsDisabled() {
        navigationSteps.checkMenuOnMainScreen();
        navigationSteps.clickNewsInMenu();
        navigationSteps.checkMenuOnNewsScreen();
        navigationSteps.clickMainInMenu();
    }

    @Test
    @Feature("Главный экран")
    @DisplayName("Переход ко всем новостям с главного экрана")
    @Description("Ссылка Все новости на главном экране открывает список новостей")
    public void allNewsOpensNewsList() {
        navigationSteps.openAllNews();
    }

    @Test
    @Feature("Наша миссия")
    @DisplayName("Переход на экран Наша миссия из верхней панели")
    @Description("Кнопка в верхней панели открывает экран Наша миссия, из него можно перейти в Новости")
    public void ourMissionButtonOpensOurMission() {
        navigationSteps.openOurMission();
        navigationSteps.openNewsFromOurMission();
    }

    @Test
    @Feature("Логаут")
    @DisplayName("Выход из системы с экрана Новостей")
    @Description("Выход из системы работает с экрана новостей")
    public void logOutFromNewsList() {
        newsSteps.goToNewsList();
        authSteps.logOut();
    }
}
