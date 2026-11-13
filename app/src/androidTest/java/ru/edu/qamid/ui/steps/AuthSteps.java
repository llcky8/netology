package ru.edu.qamid.ui.steps;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static ru.edu.qamid.ui.utils.AllureUtils.step;

import android.os.SystemClock;

import ru.edu.qamid.R;
import ru.edu.qamid.ui.pages.AppBarPage;
import ru.edu.qamid.ui.pages.AuthPage;
import ru.edu.qamid.ui.pages.MainPage;
import ru.edu.qamid.ui.utils.WaitUtils;

public class AuthSteps {

    private final AuthPage authPage = new AuthPage();
    private final MainPage mainPage = new MainPage();
    private final AppBarPage appBar = new AppBarPage();

    public boolean isLoggedInAfterSplash() {
        long deadline = SystemClock.uptimeMillis() + 20_000;
        while (SystemClock.uptimeMillis() < deadline) {
            if (WaitUtils.isMatching(withId(R.id.enter_button), matches(isDisplayed()))) {
                return false;
            }
            if (WaitUtils.isMatching(withId(R.id.expand_material_button), matches(isDisplayed()))) {
                return true;
            }
            SystemClock.sleep(250);
        }
        throw new AssertionError("После splash не открылся ни вход, ни главный экран");
    }

    public void checkSplashFinished() {
        step("Splash-экран сменяется рабочим экраном приложения", this::isLoggedInAfterSplash);
    }

    public void logIn(String login, String password) {
        step("Войти под " + login, () -> {
            authPage.typeLogin(login);
            authPage.typePassword(password);
            authPage.clickSignInButton();
            mainPage.checkScreenDisplayed();
        });
    }

    public void makeSureLoggedIn(String login, String password) {
        if (!isLoggedInAfterSplash()) {
            logIn(login, password);
        }
    }

    public void makeSureLoggedOut() {
        if (isLoggedInAfterSplash()) {
            step("Выйти из сохранённой сессии", () -> {
                appBar.openProfileMenu();
                appBar.clickExit();
            });
        }
        authPage.checkScreenDisplayed();
    }

    public void fillLogin(String login) {
        step("Заполнить поле Логин", () -> {
            authPage.typeLogin(login);
            authPage.checkLoginText(login);
        });
    }

    public void fillPassword(String password) {
        step("Заполнить поле Пароль", () -> {
            authPage.typePassword(password);
            authPage.checkPasswordText(password);
        });
    }

    public void clickEnterAndCheckMainScreen() {
        step("Нажать Войти, открывается главный экран", () -> {
            authPage.clickSignInButton();
            mainPage.checkScreenDisplayed();
        });
    }

    public void clickEnterAndCheckEmptyFieldsToast() {
        step("Нажать Войти, тост о пустых полях", () ->
                authPage.clickSignInAndWaitToast(R.string.empty_login_or_password));
    }

    public void clickEnterAndCheckWrongCredentialsToast() {
        step("Нажать Войти, тост о неверном логине или пароле", () ->
                authPage.clickSignInAndWaitToast(R.string.wrong_login_or_password));
    }

    public void checkAuthScreenDisplayed() {
        step("Экран авторизации остаётся открытым", authPage::checkScreenDisplayed);
    }

    public void checkSignInButtonActive() {
        step("Кнопка Войти снова доступна, индикатор загрузки скрыт", authPage::checkSignInButtonActive);
    }

    public void logOut() {
        step("Открыть меню личного кабинета", appBar::openProfileMenu);
        step("Нажать Выйти, открыт экран авторизации", () -> {
            appBar.clickExit();
            authPage.checkScreenDisplayed();
        });
    }
}
