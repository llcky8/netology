package ru.edu.qamid.ui;

import android.os.RemoteException;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.RuleChain;

import io.qameta.allure.android.rules.ScreenshotRule;
import ru.edu.qamid.ui.utils.DeviceUtils;

public abstract class BaseUiTest {

    @Rule
    public RuleChain rules = RuleChain
            .outerRule(new ActivityScenarioRule<>(AppActivity.class))
            .around(new ScreenshotRule(ScreenshotRule.Mode.FAILURE, "screenshot-failure"));

    @BeforeClass
    public static void setUpDevice() throws RemoteException {
        DeviceUtils.lockPortrait();
    }
}
