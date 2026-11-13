package ru.edu.qamid.ui.steps;

import static ru.edu.qamid.ui.utils.AllureUtils.step;

import java.util.Calendar;

import ru.edu.qamid.ui.pages.FilterPage;
import ru.edu.qamid.ui.pages.NewsControlPanelPage;
import ru.edu.qamid.ui.pages.NewsListPage;

public class FilterSteps {

    private final FilterPage filterPage = new FilterPage();
    private final NewsListPage newsListPage = new NewsListPage();
    private final NewsControlPanelPage controlPanelPage = new NewsControlPanelPage();

    public void pickOnlyStartDate() {
        step("Выбрать только дату начала периода", () -> filterPage.pickStartDate(Calendar.getInstance()));
    }

    public void pickOnlyEndDate() {
        step("Выбрать только дату окончания периода", () -> filterPage.pickEndDate(Calendar.getInstance()));
    }

    public void applyAndCheckWrongPeriodDialog() {
        step("Нажать Фильтровать, диалог о неверном периоде", () -> {
            filterPage.clickFilterApply();
            filterPage.checkWrongPeriodMessage();
        });
    }

    public void closeWrongPeriodDialog() {
        step("Закрыть диалог, экран фильтра остаётся открытым", () -> {
            filterPage.closeWrongPeriodMessage();
            filterPage.checkScreenDisplayed();
        });
    }

    public void checkStatusCheckBoxesHidden() {
        step("Чекбоксы Активна / Не активна скрыты", filterPage::checkStatusCheckBoxesHidden);
    }

    public void checkStatusCheckBoxesShownAndChecked() {
        step("Чекбоксы Активна / Не активна видимы и отмечены",
                filterPage::checkStatusCheckBoxesShownAndChecked);
    }

    public void cancelFilter() {
        step("Нажать Отмена, открыт список новостей", () -> {
            filterPage.clickCancel();
            newsListPage.checkScreenDisplayed();
        });
    }

    public void filterOnlyNotActiveInControlPanel() {
        step("В панели управления оставить в фильтре только Не активна", () -> {
            controlPanelPage.clickFilterIcon();
            filterPage.checkScreenDisplayed();
            filterPage.clickActiveCheckBox();
            filterPage.clickFilterApply();
            controlPanelPage.checkScreenDisplayed();
        });
    }

    public void filterOnlyActiveInControlPanel() {
        step("В панели управления оставить в фильтре только Активна", () -> {
            controlPanelPage.clickFilterIcon();
            filterPage.checkScreenDisplayed();
            filterPage.clickInactiveCheckBox();
            filterPage.clickFilterApply();
            controlPanelPage.checkScreenDisplayed();
        });
    }
}
