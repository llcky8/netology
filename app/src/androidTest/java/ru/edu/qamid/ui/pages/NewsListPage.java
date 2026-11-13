package ru.edu.qamid.ui.pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.matcher.ViewMatchers.Visibility;

import ru.edu.qamid.R;
import ru.edu.qamid.ui.utils.WaitUtils;

public class NewsListPage {

    public void checkScreenDisplayed() {
        WaitUtils.waitFor(withId(R.id.news_filter_button), matches(isDisplayed()));
        onView(withId(R.id.news_edit_button)).check(matches(isDisplayed()));
        onView(withId(R.id.news_sort_button)).check(matches(isDisplayed()));
        onView(withId(R.id.expand_material_button))
                .check(matches(withEffectiveVisibility(Visibility.GONE)));
    }

    public void clickFilterIcon() {
        onView(withId(R.id.news_filter_button)).perform(click());
    }

    public void clickControlPanelButton() {
        onView(withId(R.id.news_edit_button)).perform(click());
    }
}
