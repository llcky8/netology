package ru.edu.qamid.ui.pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers.Visibility;

import org.hamcrest.Matcher;

import ru.edu.qamid.R;
import ru.edu.qamid.ui.utils.WaitUtils;

public class OurMissionPage {

    public void checkScreenDisplayed() {
        WaitUtils.waitFor(withId(R.id.our_mission_title_text_view), matches(isDisplayed()));
        onView(withId(R.id.our_mission_title_text_view))
                .check(matches(withText(R.string.our_mission_title_text)));
        onView(withId(R.id.our_mission_item_list_recycler_view)).check(matches(isDisplayed()));
    }

    public void clickCardWithTitle(String title) {
        onView(withId(R.id.our_mission_item_list_recycler_view))
                .perform(RecyclerViewActions.<RecyclerView.ViewHolder>actionOnItem(
                        hasDescendant(allOf(withId(R.id.our_mission_item_title_text_view), withText(title))),
                        click()));
    }

    public void scrollToCard(String title) {
        onView(withId(R.id.our_mission_item_list_recycler_view))
                .perform(RecyclerViewActions.<RecyclerView.ViewHolder>scrollTo(
                        hasDescendant(allOf(withId(R.id.our_mission_item_title_text_view), withText(title)))));
    }

    public void checkDescriptionExpanded(String title, String description) {
        scrollToCard(title);
        WaitUtils.waitFor(descriptionOf(title), matches(allOf(isDisplayed(), withText(description))));
    }

    public void checkDescriptionCollapsed(String title) {
        scrollToCard(title);
        WaitUtils.waitFor(descriptionOf(title), matches(withEffectiveVisibility(Visibility.GONE)));
    }

    private Matcher<View> descriptionOf(String title) {
        return allOf(
                withId(R.id.our_mission_item_description_text_view),
                isDescendantOfA(allOf(
                        withId(R.id.our_mission_item_material_card_view),
                        hasDescendant(allOf(withId(R.id.our_mission_item_title_text_view), withText(title)))
                ))
        );
    }
}
