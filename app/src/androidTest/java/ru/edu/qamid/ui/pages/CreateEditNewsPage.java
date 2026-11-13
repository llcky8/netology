package ru.edu.qamid.ui.pages;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.StringRes;
import androidx.test.espresso.contrib.PickerActions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ru.edu.qamid.R;
import ru.edu.qamid.ui.utils.CustomMatchers;
import ru.edu.qamid.ui.utils.ToastChecker;
import ru.edu.qamid.ui.utils.WaitUtils;

public class CreateEditNewsPage {

    public void checkScreenDisplayed() {
        WaitUtils.waitFor(withId(R.id.news_save_button), matches(isDisplayed()));
        onView(withId(R.id.news_title_edit_text)).check(matches(isDisplayed()));
    }

    public void checkFormTitle(@StringRes int titleRes) {
        onView(allOf(withId(R.id.custom_app_bar_title_text_view), isDisplayed()))
                .check(matches(withText(titleRes)));
    }

    public void enterCategory(String category) {
        onView(withId(R.id.news_category_auto_complete))
                .perform(replaceText(category), closeSoftKeyboard());
    }

    public void selectCategoryFromDropdown(String category) {
        onView(withId(R.id.news_category_auto_complete)).perform(click());
        onData(equalTo(category))
                .inRoot(isPlatformPopup())
                .perform(click());
    }

    public void enterTitle(String title) {
        onView(withId(R.id.news_title_edit_text))
                .perform(replaceText(title), closeSoftKeyboard());
    }

    public void checkTitle(String title) {
        onView(withId(R.id.news_title_edit_text)).check(matches(withText(title)));
    }

    public void enterDescription(String description) {
        onView(withId(R.id.news_description_edit_text))
                .perform(scrollTo(), replaceText(description), closeSoftKeyboard());
    }

    public void openPublishDatePicker() {
        onView(withId(R.id.news_publish_date_edit_text)).perform(click());
        onView(isAssignableFrom(DatePicker.class)).check(matches(isDisplayed()));
    }

    public void setPickerDate(Calendar date) {
        onView(isAssignableFrom(DatePicker.class)).perform(
                PickerActions.setDate(
                        date.get(Calendar.YEAR),
                        date.get(Calendar.MONTH) + 1,
                        date.get(Calendar.DAY_OF_MONTH)
                )
        );
    }

    public void setTodayPublishDate() {
        openPublishDatePicker();
        setPickerDate(Calendar.getInstance());
        confirmPicker();
    }

    public void setPublishTime(int hour, int minute) {
        onView(withId(R.id.news_publish_time_edit_text)).perform(click());
        onView(isAssignableFrom(TimePicker.class)).perform(PickerActions.setTime(hour, minute));
        confirmPicker();
    }

    public void confirmPicker() {
        onView(withId(android.R.id.button1)).perform(click());
    }

    public void checkDatePickerMinDateIsNow(long toleranceMillis) {
        onView(isAssignableFrom(DatePicker.class))
                .check(matches(CustomMatchers.minDateCloseTo(
                        System.currentTimeMillis(), toleranceMillis)));
    }

    public void checkPublishDate(Calendar expected) {
        String formatted = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(expected.getTime());
        onView(withId(R.id.news_publish_date_edit_text)).check(matches(withText(formatted)));
    }

    public void clickActiveSwitch() {
        onView(withId(R.id.news_active_switch)).perform(scrollTo(), click());
    }

    public void checkActiveSwitchLockedOn() {
        onView(withId(R.id.news_active_switch))
                .perform(scrollTo())
                .check(matches(allOf(isChecked(), not(isEnabled()), withText(R.string.news_item_active))));
    }

    public void checkActiveSwitchOff() {
        onView(withId(R.id.news_active_switch))
                .check(matches(allOf(isNotChecked(), withText(R.string.news_item_not_active))));
    }

    public void clickSave() {
        onView(withId(R.id.news_save_button)).perform(scrollTo(), click());
    }

    public void clickSaveAndExpectToast(@StringRes int toastTextRes) {
        ToastChecker.performAndExpectToast(this::clickSave, toastTextRes);
    }

    public void clickCancel() {
        onView(withId(R.id.news_cancel_button)).perform(scrollTo(), click());
    }

    public void checkCancelDialogDisplayed() {
        onView(withText(R.string.cancellation)).check(matches(isDisplayed()));
    }

    public void confirmDialog() {
        onView(withId(android.R.id.button1)).perform(click());
    }

    public void dismissDialog() {
        onView(withId(android.R.id.button2)).perform(click());
    }
}
