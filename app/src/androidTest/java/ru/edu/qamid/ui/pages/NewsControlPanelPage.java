package ru.edu.qamid.ui.pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.os.SystemClock;
import android.view.View;

import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.contrib.RecyclerViewActions;

import org.hamcrest.Matcher;

import ru.edu.qamid.R;
import ru.edu.qamid.ui.utils.WaitUtils;

public class NewsControlPanelPage {

    private static final long LIST_UPDATE_TIMEOUT_MS = 15_000;
    private static final long POLL_INTERVAL_MS = 300;

    public void checkScreenDisplayed() {
        WaitUtils.waitFor(withId(R.id.add_news_image_view), matches(isDisplayed()));
        onView(withId(R.id.news_filter_button)).check(matches(isDisplayed()));
    }

    public void clickAddNews() {
        onView(withId(R.id.add_news_image_view)).perform(click());
    }

    public void clickFilterButton() {
        onView(withId(R.id.news_filter_button)).perform(click());
    }

    public void scrollToNewsWithTitle(String title) {
        long deadline = SystemClock.uptimeMillis() + LIST_UPDATE_TIMEOUT_MS;
        PerformException last;
        do {
            try {
                scrollTo(title);
                return;
            } catch (PerformException e) {
                last = e;
            }
            SystemClock.sleep(POLL_INTERVAL_MS);
        } while (SystemClock.uptimeMillis() < deadline);
        throw new AssertionError("Новость «" + title + "» не появилась в списке", last);
    }

    public void checkNewsWithTitleDisplayed(String title) {
        scrollToNewsWithTitle(title);
        onView(cardForTitle(title)).check(matches(isDisplayed()));
    }

    public void checkNewsWithTitleNotExist(String title) {
        long deadline = SystemClock.uptimeMillis() + LIST_UPDATE_TIMEOUT_MS;
        do {
            try {
                scrollTo(title);
            } catch (PerformException expected) {
                return;
            }
            SystemClock.sleep(POLL_INTERVAL_MS);
        } while (SystemClock.uptimeMillis() < deadline);
        throw new AssertionError("Новость «" + title + "» всё ещё есть в списке");
    }

    public void clickDeleteForTitle(String title) {
        scrollToNewsWithTitle(title);
        onView(childOfCard(title, R.id.news_item_delete_image_view)).perform(click());
    }

    public void clickEditForTitle(String title) {
        scrollToNewsWithTitle(title);
        onView(childOfCard(title, R.id.news_item_edit_image_view)).perform(click());
    }

    public void checkDeleteDialogDisplayed() {
        onView(withText(R.string.irrevocable_deletion)).check(matches(isDisplayed()));
    }

    public void confirmDialog() {
        onView(withId(android.R.id.button1)).perform(click());
    }

    public void cancelDialog() {
        onView(withId(android.R.id.button2)).perform(click());
    }

    public void checkNewsStatus(String title, @StringRes int statusTextRes) {
        scrollToNewsWithTitle(title);
        WaitUtils.waitFor(
                childOfCard(title, R.id.news_item_published_text_view),
                matches(withText(statusTextRes))
        );
    }

    private void scrollTo(String title) {
        onView(withId(R.id.news_list_recycler_view))
                .perform(RecyclerViewActions.<RecyclerView.ViewHolder>scrollTo(
                        hasDescendant(withText(title))));
    }

    private Matcher<View> cardForTitle(String title) {
        return allOf(
                withId(R.id.news_item_material_card_view),
                hasDescendant(allOf(withId(R.id.news_item_title_text_view), withText(title)))
        );
    }

    private Matcher<View> childOfCard(String title, int childId) {
        return allOf(withId(childId), isDescendantOfA(cardForTitle(title)));
    }
}
