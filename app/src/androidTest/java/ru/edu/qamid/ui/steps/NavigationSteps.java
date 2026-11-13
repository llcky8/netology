package ru.edu.qamid.ui.steps;

import static ru.edu.qamid.ui.utils.AllureUtils.step;

import ru.edu.qamid.R;
import ru.edu.qamid.ui.pages.AppBarPage;
import ru.edu.qamid.ui.pages.MainPage;
import ru.edu.qamid.ui.pages.NewsListPage;
import ru.edu.qamid.ui.pages.OurMissionPage;

public class NavigationSteps {

    private final AppBarPage appBar = new AppBarPage();
    private final MainPage mainPage = new MainPage();
    private final NewsListPage newsListPage = new NewsListPage();
    private final OurMissionPage ourMissionPage = new OurMissionPage();

    public void checkMenuOnMainScreen() {
        step("Главный экран: Главная недоступна, Новости доступны", () -> {
            appBar.openMainMenu();
            appBar.checkMenuItemDisabled(R.string.main);
            appBar.checkMenuItemEnabled(R.string.news);
        });
    }

    public void clickNewsInMenu() {
        step("Перейти в Новости", () -> {
            appBar.selectMenuItem(R.string.news);
            newsListPage.checkScreenDisplayed();
        });
    }

    public void checkMenuOnNewsScreen() {
        step("Экран Новостей: Новости недоступны, Главная доступна", () -> {
            appBar.openMainMenu();
            appBar.checkMenuItemDisabled(R.string.news);
            appBar.checkMenuItemEnabled(R.string.main);
        });
    }

    public void clickMainInMenu() {
        step("Вернуться на главный экран через меню", () -> {
            appBar.selectMenuItem(R.string.main);
            mainPage.checkScreenDisplayed();
        });
    }

    public void openAllNews() {
        step("Нажать Все новости", () -> {
            mainPage.clickAllNews();
            newsListPage.checkScreenDisplayed();
        });
    }

    public void openOurMission() {
        step("Нажать кнопку Наша миссия", () -> {
            appBar.openOurMission();
            ourMissionPage.checkScreenDisplayed();
        });
    }

    public void openNewsFromOurMission() {
        step("Из меню экрана Наша миссия перейти в Новости", () -> {
            appBar.openMainMenu();
            appBar.checkMenuItemEnabled(R.string.main);
            appBar.checkMenuItemEnabled(R.string.news);
            appBar.selectMenuItem(R.string.news);
            newsListPage.checkScreenDisplayed();
        });
    }
}
