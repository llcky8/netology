package ru.edu.qamid.ui.utils;

import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;

import android.view.View;
import android.widget.DatePicker;

import androidx.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class CustomMatchers {

    public static Matcher<View> popupMenuItem(int titleRes) {
        return allOf(withClassName(endsWith("ListMenuItemView")), hasDescendant(withText(titleRes)));
    }

    public static Matcher<View> minDateCloseTo(long expected, long tolerance) {
        return new BoundedMatcher<View, DatePicker>(DatePicker.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("minDate ≈ " + expected);
            }

            @Override
            protected boolean matchesSafely(DatePicker picker) {
                return Math.abs(picker.getMinDate() - expected) <= tolerance;
            }
        };
    }
}
