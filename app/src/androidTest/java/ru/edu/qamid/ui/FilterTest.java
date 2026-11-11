package ru.edu.qamid.ui;

import static ru.edu.qamid.ui.utils.AllureSteps.step;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.edu.qamid.ui.data.NewsCleanupClient;
import ru.edu.qamid.ui.data.TestData;
import ru.edu.qamid.ui.pages.FilterPage;
import ru.edu.qamid.ui.pages.NewsControlPanelPage;
import ru.edu.qamid.ui.pages.NewsListPage;
import ru.edu.qamid.ui.steps.AuthSteps;
import ru.edu.qamid.ui.steps.NewsSteps;

@RunWith(AndroidJUnit4.class)
@Epic("Фильтр")
public class FilterTest extends BaseUiTest {

    private final FilterPage filterPage = new FilterPage();
    private final NewsListPage newsListPage = new NewsListPage();
    private final NewsControlPanelPage controlPanelPage = new NewsControlPanelPage();
    private final AuthSteps authSteps = new AuthSteps();
    private final NewsSteps newsSteps = new NewsSteps();
    private final NewsCleanupClient cleanupClient = new NewsCleanupClient();

    private final List<String> createdTitles = new ArrayList<>();

    @Before
    public void signIn() {
        authSteps.ensureLoggedIn(TestData.VALID_LOGIN, TestData.VALID_PASSWORD);
    }

    @After
    public void deleteCreatedNews() {
        cleanupClient.deleteByTitles(createdTitles);
    }

    @Test
    @Feature("Фильтрация по периоду")
    @DisplayName("Фильтр с указанной только начальной датой")
    @Description("Если указана только начальная дата, фильтр не применяется и показывается диалог Неверно указан период")
    public void filterWithOnlyStartDate() {
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
    @Description("Если указана только конечная дата, фильтр не применяется и показывается диалог Неверно указан период")
    public void filterWithOnlyEndDate() {
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
    @Description("В фильтре, открытом из списка новостей, нет чекбоксов Активна и Не активна")
    public void filterFromNewsListHidesStatusCheckBoxes() {
        newsSteps.openFilterFromNewsList();
        step("Чекбоксы Активна / Не активна скрыты", filterPage::checkStatusCheckBoxesHidden);
    }

    @Test
    @Feature("Фильтр статуса")
    @DisplayName("Фильтр из панели управления содержит отмеченные чекбоксы статуса")
    @Description("В фильтре, открытом из панели управления, чекбоксы Активна и Не активна видны и отмечены")
    public void filterFromControlPanelShowsStatusCheckBoxes() {
        newsSteps.openFilterFromControlPanel();
        step("Чекбоксы Активна / Не активна видимы и отмечены",
                filterPage::checkStatusCheckBoxesShownAndChecked);
    }

    @Test
    @Feature("Фильтр статуса")
    @DisplayName("Фильтр по статусу в панели управления")
    @Description("Созданная активная новость пропадает из списка при фильтре Не активна и показывается при фильтре Активна")
    public void filterByStatusInControlPanel() {
        String title = TestData.randomNewsTitle();
        createdTitles.add(title);
        newsSteps.openNewsControlPanel();
        newsSteps.createNews(title);

        step("Оставить в фильтре только Не активна, активной новости нет в списке", () -> {
            controlPanelPage.clickFilterButton();
            filterPage.checkScreenDisplayed();
            filterPage.clickActiveCheckBox();
            filterPage.clickApplyFilter();
            controlPanelPage.checkScreenDisplayed();
            controlPanelPage.checkNewsWithTitleNotExist(title);
        });
        step("Оставить в фильтре только Активна, новость есть в списке", () -> {
            controlPanelPage.clickFilterButton();
            filterPage.checkScreenDisplayed();
            filterPage.clickInactiveCheckBox();
            filterPage.clickApplyFilter();
            controlPanelPage.checkScreenDisplayed();
            controlPanelPage.checkNewsWithTitleDisplayed(title);
        });
    }

    @Test
    @Feature("Фильтрация по периоду")
    @DisplayName("Кнопка Отмена закрывает фильтр без применения")
    @Description("Кнопка Отмена возвращает к списку новостей")
    public void cancelFilterReturnsToNewsList() {
        newsSteps.openFilterFromNewsList();
        step("Нажать Отмена, открыт список новостей", () -> {
            filterPage.clickCancel();
            newsListPage.checkScreenDisplayed();
        });
    }
}
