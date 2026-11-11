package ru.edu.qamid.ui;

import static ru.edu.qamid.ui.utils.AllureSteps.step;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.edu.qamid.ui.data.TestData;
import ru.edu.qamid.ui.pages.AppBarPage;
import ru.edu.qamid.ui.pages.OurMissionPage;
import ru.edu.qamid.ui.steps.AuthSteps;

@RunWith(AndroidJUnit4.class)
@Epic("Наша миссия")
public class OurMissionTest extends BaseUiTest {

    private static final String CARD_TITLE = "Хоспис в своем истинном понимании - это творчество";
    private static final String CARD_DESCRIPTION =
            "Нет шаблона и стандарта, есть только дух, который живет в разных домах по-разному. "
                    + "Но всегда он добрый, любящий и помогающий.";

    private final AppBarPage appBar = new AppBarPage();
    private final OurMissionPage ourMissionPage = new OurMissionPage();
    private final AuthSteps authSteps = new AuthSteps();

    @Before
    public void openOurMission() {
        authSteps.ensureLoggedIn(TestData.VALID_LOGIN, TestData.VALID_PASSWORD);
        step("Открыть экран Наша миссия", () -> {
            appBar.openOurMission();
            ourMissionPage.checkScreenDisplayed();
        });
    }

    @Test
    @Feature("Карточки миссии")
    @DisplayName("Раскрытие и сворачивание карточки миссии")
    @Description("Нажатие на карточку раскрывает описание, повторное нажатие скрывает")
    public void missionCardClickTogglesDescription() {
        step("Описание карточки изначально скрыто", () ->
                ourMissionPage.checkDescriptionCollapsed(CARD_TITLE));
        step("Нажать на карточку, описание раскрыто", () -> {
            ourMissionPage.clickCardWithTitle(CARD_TITLE);
            ourMissionPage.checkDescriptionExpanded(CARD_TITLE, CARD_DESCRIPTION);
        });
        step("Нажать повторно, описание скрыто", () -> {
            ourMissionPage.clickCardWithTitle(CARD_TITLE);
            ourMissionPage.checkDescriptionCollapsed(CARD_TITLE);
        });
    }
}
