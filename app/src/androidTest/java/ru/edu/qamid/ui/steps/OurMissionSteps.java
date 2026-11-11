package ru.edu.qamid.ui.steps;

import static ru.edu.qamid.ui.utils.AllureSteps.step;

import ru.edu.qamid.ui.pages.OurMissionPage;

public class OurMissionSteps {

    private final OurMissionPage ourMissionPage = new OurMissionPage();

    public void checkDescriptionCollapsed(String title) {
        step("Описание карточки скрыто", () -> ourMissionPage.checkDescriptionCollapsed(title));
    }

    public void expandCard(String title, String description) {
        step("Нажать на карточку, описание раскрыто", () -> {
            ourMissionPage.clickCardWithTitle(title);
            ourMissionPage.checkDescriptionExpanded(title, description);
        });
    }

    public void collapseCard(String title) {
        step("Нажать повторно, описание скрыто", () -> {
            ourMissionPage.clickCardWithTitle(title);
            ourMissionPage.checkDescriptionCollapsed(title);
        });
    }
}
