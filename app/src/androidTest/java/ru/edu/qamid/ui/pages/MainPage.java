package ru.edu.qamid.ui.pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import ru.edu.qamid.R;
import ru.edu.qamid.ui.utils.WaitUtils;

public class MainPage {

    public void checkScreenDisplayed() {
        WaitUtils.waitFor(withId(R.id.expand_material_button), matches(isDisplayed()));
        onView(withId(R.id.all_news_text_view)).check(matches(isDisplayed()));
    }

    public void clickAllNews() {
        onView(withId(R.id.all_news_text_view)).perform(click());
    }
}
