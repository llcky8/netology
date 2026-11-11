package ru.edu.qamid.ui;

import android.os.RemoteException;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.RuleChain;

import io.qameta.allure.android.rules.ScreenshotRule;

public abstract class BaseUiTest {

    @Rule
    public RuleChain rules = RuleChain
            .outerRule(new ActivityScenarioRule<>(AppActivity.class))
            .around(new ScreenshotRule(ScreenshotRule.Mode.FAILURE, "screenshot-failure"));

    @BeforeClass
    public static void lockPortrait() throws RemoteException {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.setOrientationNatural();
        device.freezeRotation();
    }
}
