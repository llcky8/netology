package ru.edu.qamid.ui;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.edu.qamid.ui.data.NewsCleanupClient;
import ru.edu.qamid.ui.data.TestData;
import ru.edu.qamid.ui.steps.AuthSteps;
import ru.edu.qamid.ui.steps.FilterSteps;
import ru.edu.qamid.ui.steps.NewsSteps;

@RunWith(AndroidJUnit4.class)
@Epic("Фильтр новостей")
public class FilterTest extends BaseUiTest {

    private final AuthSteps authSteps = new AuthSteps();
    private final NewsSteps newsSteps = new NewsSteps();
    private final FilterSteps filterSteps = new FilterSteps();
    private final NewsCleanupClient cleanupClient = new NewsCleanupClient();

    private final List<String> createdTitles = new ArrayList<>();

    @Before
    public void logIn() {
        authSteps.makeSureLoggedIn(TestData.VALID_LOGIN, TestData.VALID_PASSWORD);
    }

    @After
    public void deleteCreatedNews() {
        cleanupClient.deleteByTitles(createdTitles);
    }

    @Test
    @Feature("Период")
    @DisplayName("Фильтр с указанной только начальной датой")
    @Description("Если указана только начальная дата, фильтр не применяется и показывается диалог Неверно указан период")
    public void filterWithOnlyStartDate() {
        newsSteps.goToFilterFromNewsList();
        filterSteps.pickOnlyStartDate();
        filterSteps.applyAndCheckWrongPeriodDialog();
        filterSteps.closeWrongPeriodDialog();
    }

    @Test
    @Feature("Период")
    @DisplayName("Фильтр с указанной только конечной датой")
    @Description("Если указана только конечная дата, фильтр не применяется и показывается диалог Неверно указан период")
    public void filterWithOnlyEndDate() {
        newsSteps.goToFilterFromNewsList();
        filterSteps.pickOnlyEndDate();
        filterSteps.applyAndCheckWrongPeriodDialog();
        filterSteps.closeWrongPeriodDialog();
    }

    @Test
    @Feature("Фильтр статуса")
    @DisplayName("Фильтр из списка новостей не содержит чекбоксов статуса")
    @Description("В фильтре, открытом из списка новостей, нет чекбоксов Активна и Не активна")
    public void filterFromNewsListHidesStatusCheckBoxes() {
        newsSteps.goToFilterFromNewsList();
        filterSteps.checkStatusCheckBoxesHidden();
    }

    @Test
    @Feature("Фильтр статуса")
    @DisplayName("Фильтр из панели управления содержит отмеченные чекбоксы статуса")
    @Description("В фильтре, открытом из панели управления, чекбоксы Активна и Не активна видны и отмечены")
    public void filterFromControlPanelShowsStatusCheckBoxes() {
        newsSteps.goToFilterFromControlPanel();
        filterSteps.checkStatusCheckBoxesShownAndChecked();
    }

    @Test
    @Feature("Фильтр статуса")
    @DisplayName("Фильтр по статусу в панели управления")
    @Description("Созданная активная новость пропадает из списка при фильтре Не активна и показывается при фильтре Активна")
    public void filterByStatusInControlPanel() {
        String title = TestData.uniqueNewsTitle();
        createdTitles.add(title);
        newsSteps.goToControlPanel();
        newsSteps.addNews(title);
        filterSteps.filterOnlyNotActiveInControlPanel();
        newsSteps.checkNewsMissing(title);
        filterSteps.filterOnlyActiveInControlPanel();
        newsSteps.checkNewsPresent(title);
    }

    @Test
    @Feature("Период")
    @DisplayName("Кнопка Отмена закрывает фильтр без применения")
    @Description("Кнопка Отмена возвращает к списку новостей")
    public void cancelFilterReturnsToNewsList() {
        newsSteps.goToFilterFromNewsList();
        filterSteps.cancelFilter();
    }
}
