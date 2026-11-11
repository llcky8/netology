package ru.edu.qamid.ui.pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.matcher.ViewMatchers.Visibility;

import ru.edu.qamid.R;
import ru.edu.qamid.ui.utils.ToastChecker;
import ru.edu.qamid.ui.utils.WaitUtils;

public class AuthPage {

    public void checkScreenDisplayed() {
        WaitUtils.waitFor(withId(R.id.enter_button), matches(isDisplayed()));
        onView(withId(R.id.login_edit_text)).check(matches(isDisplayed()));
        onView(withId(R.id.password_edit_text)).check(matches(isDisplayed()));
    }

    public void setLogin(String login) {
        onView(withId(R.id.login_edit_text)).perform(replaceText(login), closeSoftKeyboard());
    }

    public void checkLoginFieldContains(String login) {
        onView(withId(R.id.login_edit_text)).check(matches(withText(login)));
    }

    public void setPassword(String password) {
        onView(withId(R.id.password_edit_text)).perform(replaceText(password), closeSoftKeyboard());
    }

    public void checkPasswordFieldContains(String password) {
        onView(withId(R.id.password_edit_text)).check(matches(withText(password)));
    }

    public void clickEnter() {
        onView(withId(R.id.enter_button)).perform(click());
    }

    public void clickEnterAndExpectToast(int toastTextRes) {
        ToastChecker.performAndExpectToast(this::clickEnter, toastTextRes);
    }

    public void checkEnterButtonReady() {
        WaitUtils.waitFor(
                withId(R.id.enter_button),
                matches(allOf(isDisplayed(), isEnabled(), withText(R.string.sign_in)))
        );
        onView(withId(R.id.enter_button_progress))
                .check(matches(withEffectiveVisibility(Visibility.GONE)));
    }
}
