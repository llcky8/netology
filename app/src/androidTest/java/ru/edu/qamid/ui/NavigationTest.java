package ru.edu.qamid.ui;

import static ru.edu.qamid.ui.utils.AllureSteps.step;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.edu.qamid.R;
import ru.edu.qamid.ui.data.TestData;
import ru.edu.qamid.ui.pages.AppBarPage;
import ru.edu.qamid.ui.pages.AuthPage;
import ru.edu.qamid.ui.pages.MainPage;
import ru.edu.qamid.ui.pages.NewsListPage;
import ru.edu.qamid.ui.pages.OurMissionPage;
import ru.edu.qamid.ui.steps.AuthSteps;

@RunWith(AndroidJUnit4.class)
@Epic("Навигация")
public class NavigationTest extends BaseUiTest {

    private final AppBarPage appBar = new AppBarPage();
    private final MainPage mainPage = new MainPage();
    private final NewsListPage newsListPage = new NewsListPage();
    private final OurMissionPage ourMissionPage = new OurMissionPage();
    private final AuthPage authPage = new AuthPage();
    private final AuthSteps authSteps = new AuthSteps();

    @Before
    public void signIn() {
        authSteps.ensureLoggedIn(TestData.VALID_LOGIN, TestData.VALID_PASSWORD);
    }

    @Test
    @Feature("Меню приложения")
    @DisplayName("Пункт меню текущего экрана недоступен")
    @Description("В главном меню нельзя выбрать пункт экрана, на котором сейчас находимся")
    public void currentScreenMenuItemIsDisabled() {
        step("Главный экран: Главная недоступна, Новости доступны", () -> {
            appBar.openMainMenu();
            appBar.checkMenuItemDisabled(R.string.main);
            appBar.checkMenuItemEnabled(R.string.news);
        });
        step("Перейти в Новости", () -> {
            appBar.clickMenuItem(R.string.news);
            newsListPage.checkScreenDisplayed();
        });
        step("Экран Новостей: Новости недоступны, Главная доступна", () -> {
            appBar.openMainMenu();
            appBar.checkMenuItemDisabled(R.string.news);
            appBar.checkMenuItemEnabled(R.string.main);
        });
        step("Вернуться на главный экран через меню", () -> {
            appBar.clickMenuItem(R.string.main);
            mainPage.checkScreenDisplayed();
        });
    }

    @Test
    @Feature("Главный экран")
    @DisplayName("Переход ко всем новостям с главного экрана")
    @Description("Ссылка Все новости на главном экране открывает список новостей")
    public void allNewsOpensNewsList() {
        step("Нажать Все новости", () -> {
            mainPage.clickAllNews();
            newsListPage.checkScreenDisplayed();
        });
    }

    @Test
    @Feature("Наша миссия")
    @DisplayName("Переход на экран Наша миссия из верхней панели")
    @Description("Кнопка в верхней панели открывает экран Наша миссия, из него можно перейти в Новости")
    public void ourMissionButtonOpensOurMission() {
        step("Нажать кнопку Наша миссия", () -> {
            appBar.openOurMission();
            ourMissionPage.checkScreenDisplayed();
        });
        step("Из меню экрана Наша миссия перейти в Новости", () -> {
            appBar.openMainMenu();
            appBar.checkMenuItemEnabled(R.string.main);
            appBar.checkMenuItemEnabled(R.string.news);
            appBar.clickMenuItem(R.string.news);
            newsListPage.checkScreenDisplayed();
        });
    }

    @Test
    @Feature("Выход из системы")
    @DisplayName("Выход из системы с экрана Новостей")
    @Description("Выход из системы работает с экрана новостей")
    public void logOutFromNewsList() {
        step("Перейти в Новости", () -> {
            appBar.openMainMenu();
            appBar.clickMenuItem(R.string.news);
            newsListPage.checkScreenDisplayed();
        });
        step("Выйти через меню личного кабинета", () -> {
            appBar.openPersonalCabinet();
            appBar.clickLogOut();
            authPage.checkScreenDisplayed();
        });
    }
}
