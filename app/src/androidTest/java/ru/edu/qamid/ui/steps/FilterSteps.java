package ru.edu.qamid.ui.steps;

import static ru.edu.qamid.ui.utils.AllureSteps.step;

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
            filterPage.clickApplyFilter();
            filterPage.checkWrongPeriodDialogDisplayed();
        });
    }

    public void closeWrongPeriodDialog() {
        step("Закрыть диалог, экран фильтра остаётся открытым", () -> {
            filterPage.confirmWrongPeriodDialog();
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
            controlPanelPage.clickFilterButton();
            filterPage.checkScreenDisplayed();
            filterPage.clickActiveCheckBox();
            filterPage.clickApplyFilter();
            controlPanelPage.checkScreenDisplayed();
        });
    }

    public void filterOnlyActiveInControlPanel() {
        step("В панели управления оставить в фильтре только Активна", () -> {
            controlPanelPage.clickFilterButton();
            filterPage.checkScreenDisplayed();
            filterPage.clickInactiveCheckBox();
            filterPage.clickApplyFilter();
            controlPanelPage.checkScreenDisplayed();
        });
    }
}
