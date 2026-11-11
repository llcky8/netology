package ru.edu.qamid.ui.utils;

import static androidx.test.espresso.Espresso.onView;

import android.os.SystemClock;
import android.view.View;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;

import org.hamcrest.Matcher;

public class WaitUtils {

    public static void waitFor(Matcher<View> view, ViewAssertion assertion) {
        long deadline = SystemClock.uptimeMillis() + 15_000;
        while (true) {
            try {
                onView(view).check(assertion);
                return;
            } catch (NoMatchingViewException | AssertionError e) {
                if (SystemClock.uptimeMillis() > deadline) throw e;
            }
            SystemClock.sleep(250);
        }
    }

    public static boolean isMatching(Matcher<View> view, ViewAssertion assertion) {
        try {
            onView(view).check(assertion);
            return true;
        } catch (NoMatchingViewException | AssertionError e) {
            return false;
        }
    }
}
