package ru.edu.qamid.ui;

import static ru.edu.qamid.ui.utils.AllureSteps.step;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.edu.qamid.ui.data.TestData;
import ru.edu.qamid.ui.pages.FilterPage;
import ru.edu.qamid.ui.pages.NewsListPage;
import ru.edu.qamid.ui.steps.AuthSteps;
import ru.edu.qamid.ui.steps.NewsSteps;

@RunWith(AndroidJUnit4.class)
@Epic("Фильтр")
public class FilterTest extends BaseUiTest {

    private final FilterPage filterPage = new FilterPage();
    private final NewsListPage newsListPage = new NewsListPage();
    private final AuthSteps authSteps = new AuthSteps();
    private final NewsSteps newsSteps = new NewsSteps();

    @Before
    public void signIn() {
        authSteps.ensureLoggedIn(TestData.VALID_LOGIN, TestData.VALID_PASSWORD);
    }

    @Test
    @Feature("Фильтрация по периоду")
    @DisplayName("Фильтр с указанной только начальной датой")
    public void filterWithOnlyStartDate_showsWrongPeriodDialog() {
        newsSteps.openFilterFromNewsList();
        step("Выбрать только дату начала периода", () -> filterPage.pickStartDate(Calendar.getInstance()));
        step("Нажать Фильтровать, диалог о неверном периоде", () -> {
            filterPage.clickApplyFilter();
            filterPage.checkWrongPeriodDialogDisplayed();
        });
        step("Закрыть диалог, экран фильтра остаётся открытым", () -> {
            filterPage.confirmWrongPeriodDialog();
            filterPage.checkScreenDisplayed();
        });
    }

    @Test
    @Feature("Фильтрация по периоду")
    @DisplayName("Фильтр с указанной только конечной датой")
    public void filterWithOnlyEndDate_showsWrongPeriodDialog() {
        newsSteps.openFilterFromNewsList();
        step("Выбрать только дату окончания периода", () -> filterPage.pickEndDate(Calendar.getInstance()));
        step("Нажать Фильтровать, диалог о неверном периоде", () -> {
            filterPage.clickApplyFilter();
            filterPage.checkWrongPeriodDialogDisplayed();
        });
        step("Закрыть диалог, экран фильтра остаётся открытым", () -> {
            filterPage.confirmWrongPeriodDialog();
            filterPage.checkScreenDisplayed();
        });
    }

    @Test
    @Feature("Фильтр статуса")
    @DisplayName("Фильтр из списка новостей не содержит чекбоксов статуса")
    public void filterFromNewsList_hidesStatusCheckBoxes() {
        newsSteps.openFilterFromNewsList();
        step("Чекбоксы Активна / Не активна скрыты", filterPage::checkStatusCheckBoxesHidden);
    }

    @Test
    @Feature("Фильтр статуса")
    @DisplayName("Фильтр из панели управления содержит отмеченные чекбоксы статуса")
    public void filterFromControlPanel_showsCheckedStatusCheckBoxes() {
        newsSteps.openFilterFromControlPanel();
        step("Чекбоксы Активна / Не активна видимы и отмечены",
                filterPage::checkStatusCheckBoxesShownAndChecked);
    }

    @Test
    @Feature("Фильтрация по периоду")
    @DisplayName("Кнопка Отмена закрывает фильтр без применения")
    public void cancelFilter_returnsToNewsList() {
        newsSteps.openFilterFromNewsList();
        step("Нажать Отмена, открыт список новостей", () -> {
            filterPage.clickCancel();
            newsListPage.checkScreenDisplayed();
        });
    }
}
