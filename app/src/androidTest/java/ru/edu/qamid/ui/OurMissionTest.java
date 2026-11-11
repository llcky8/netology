package ru.edu.qamid.ui;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.edu.qamid.ui.data.TestData;
import ru.edu.qamid.ui.steps.AuthSteps;
import ru.edu.qamid.ui.steps.NavigationSteps;
import ru.edu.qamid.ui.steps.OurMissionSteps;

@RunWith(AndroidJUnit4.class)
@Epic("Наша миссия")
public class OurMissionTest extends BaseUiTest {

    private static final String CARD_TITLE = "Хоспис в своем истинном понимании - это творчество";
    private static final String CARD_DESCRIPTION =
            "Нет шаблона и стандарта, есть только дух, который живет в разных домах по-разному. "
                    + "Но всегда он добрый, любящий и помогающий.";

    private final AuthSteps authSteps = new AuthSteps();
    private final NavigationSteps navigationSteps = new NavigationSteps();
    private final OurMissionSteps ourMissionSteps = new OurMissionSteps();

    @Before
    public void openOurMission() {
        authSteps.ensureLoggedIn(TestData.VALID_LOGIN, TestData.VALID_PASSWORD);
        navigationSteps.openOurMission();
    }

    @Test
    @Feature("Карточки миссии")
    @DisplayName("Раскрытие и сворачивание карточки миссии")
    @Description("Нажатие на карточку раскрывает описание, повторное нажатие скрывает")
    public void missionCardClickTogglesDescription() {
        ourMissionSteps.checkDescriptionCollapsed(CARD_TITLE);
        ourMissionSteps.expandCard(CARD_TITLE, CARD_DESCRIPTION);
        ourMissionSteps.collapseCard(CARD_TITLE);
    }
}
