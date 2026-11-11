package ru.edu.qamid.ui.pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

import androidx.annotation.StringRes;

import ru.edu.qamid.R;
import ru.edu.qamid.ui.utils.CustomMatchers;

public class AppBarPage {

    public void openMainMenu() {
        onView(withId(R.id.main_menu_image_button)).perform(click());
        onView(withText(R.string.main)).check(matches(isDisplayed()));
    }

    public void clickMenuItem(@StringRes int itemTextRes) {
        onView(withText(itemTextRes)).perform(click());
    }

    public void checkMenuItemDisabled(@StringRes int itemTextRes) {
        onView(CustomMatchers.popupMenuItem(itemTextRes)).check(matches(not(isEnabled())));
    }

    public void checkMenuItemEnabled(@StringRes int itemTextRes) {
        onView(CustomMatchers.popupMenuItem(itemTextRes)).check(matches(isEnabled()));
    }

    public void openOurMission() {
        onView(withId(R.id.our_mission_image_button)).perform(click());
    }

    public void openPersonalCabinet() {
        onView(withId(R.id.authorization_image_button)).perform(click());
        onView(withText(R.string.log_out)).check(matches(isDisplayed()));
    }

    public void clickLogOut() {
        onView(withText(R.string.log_out)).perform(click());
    }
}
