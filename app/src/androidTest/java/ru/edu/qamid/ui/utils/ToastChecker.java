package ru.edu.qamid.ui.utils;

import android.app.UiAutomation;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;

import java.util.concurrent.TimeoutException;

public class ToastChecker {

    public static void performAndExpectToast(Runnable action, int textRes) {
        String expected = ApplicationProvider.getApplicationContext().getString(textRes);
        UiAutomation automation = InstrumentationRegistry.getInstrumentation().getUiAutomation();
        try {
            automation.executeAndWaitForEvent(action::run, event -> isToast(event, expected), 15_000);
        } catch (TimeoutException e) {
            throw new AssertionError("Не появился тост: " + expected);
        }
    }

    private static boolean isToast(AccessibilityEvent event, String expected) {
        return event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED
                && event.getClassName() != null
                && event.getClassName().toString().startsWith(Toast.class.getName())
                && event.getText().toString().contains(expected);
    }
}
