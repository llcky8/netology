package ru.edu.qamid.ui.pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.widget.DatePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers.Visibility;

import java.util.Calendar;

import ru.edu.qamid.R;
import ru.edu.qamid.ui.utils.WaitUtils;

public class FilterPage {

    public void checkScreenDisplayed() {
        WaitUtils.waitFor(withId(R.id.filter_news_title_text_view), matches(isDisplayed()));
        onView(withId(R.id.filter_news_apply_button)).check(matches(isDisplayed()));
        onView(withId(R.id.filter_news_cancel_button)).check(matches(isDisplayed()));
    }

    public void pickStartDate(Calendar date) {
        onView(withId(R.id.filter_news_date_start_edit_text)).perform(click());
        setDateAndConfirm(date);
    }

    public void pickEndDate(Calendar date) {
        onView(withId(R.id.filter_news_date_end_edit_text)).perform(click());
        setDateAndConfirm(date);
    }

    private void setDateAndConfirm(Calendar date) {
        onView(isAssignableFrom(DatePicker.class)).perform(
                PickerActions.setDate(
                        date.get(Calendar.YEAR),
                        date.get(Calendar.MONTH) + 1,
                        date.get(Calendar.DAY_OF_MONTH)
                )
        );
        onView(withId(android.R.id.button1)).perform(click());
    }

    public void clickApplyFilter() {
        onView(withId(R.id.filter_news_apply_button)).perform(click());
    }

    public void clickCancel() {
        onView(withId(R.id.filter_news_cancel_button)).perform(click());
    }

    public void checkWrongPeriodDialogDisplayed() {
        onView(withText(R.string.wrong_news_date_period)).check(matches(isDisplayed()));
    }

    public void confirmWrongPeriodDialog() {
        onView(withId(android.R.id.button1)).perform(click());
    }

    public void checkStatusCheckBoxesHidden() {
        onView(withId(R.id.filter_news_active_check_box))
                .check(matches(withEffectiveVisibility(Visibility.GONE)));
        onView(withId(R.id.filter_news_inactive_check_box))
                .check(matches(withEffectiveVisibility(Visibility.GONE)));
    }

    public void checkStatusCheckBoxesShownAndChecked() {
        onView(withId(R.id.filter_news_active_check_box))
                .check(matches(allOf(isDisplayed(), isChecked(), withText(R.string.news_item_active))));
        onView(withId(R.id.filter_news_inactive_check_box))
                .check(matches(allOf(isDisplayed(), isChecked(), withText(R.string.news_item_not_active))));
    }
}
