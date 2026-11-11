package ru.edu.qamid.ui.steps;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static ru.edu.qamid.ui.utils.AllureSteps.step;

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

    public void signIn(String login, String password) {
        step("Войти под " + login, () -> {
            authPage.setLogin(login);
            authPage.setPassword(password);
            authPage.clickEnter();
            mainPage.checkScreenDisplayed();
        });
    }

    public void ensureLoggedIn(String login, String password) {
        if (!isLoggedInAfterSplash()) {
            signIn(login, password);
        }
    }

    public void ensureLoggedOut() {
        if (isLoggedInAfterSplash()) {
            step("Выйти из сохранённой сессии", () -> {
                appBar.openPersonalCabinet();
                appBar.clickLogOut();
            });
        }
        authPage.checkScreenDisplayed();
    }
}
