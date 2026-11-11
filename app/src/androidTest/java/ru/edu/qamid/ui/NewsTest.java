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

import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.edu.qamid.R;
import ru.edu.qamid.ui.data.NewsCleanupClient;
import ru.edu.qamid.ui.data.TestData;
import ru.edu.qamid.ui.pages.CreateEditNewsPage;
import ru.edu.qamid.ui.pages.NewsControlPanelPage;
import ru.edu.qamid.ui.steps.AuthSteps;
import ru.edu.qamid.ui.steps.NewsSteps;

@RunWith(AndroidJUnit4.class)
@Epic("Новости")
public class NewsTest extends BaseUiTest {

    private final NewsControlPanelPage controlPanelPage = new NewsControlPanelPage();
    private final CreateEditNewsPage createEditNewsPage = new CreateEditNewsPage();
    private final NewsSteps newsSteps = new NewsSteps();
    private final AuthSteps authSteps = new AuthSteps();
    private final NewsCleanupClient cleanupClient = new NewsCleanupClient();

    private final List<String> createdTitles = new ArrayList<>();

    @Before
    public void signInAndOpenControlPanel() {
        authSteps.ensureLoggedIn(TestData.VALID_LOGIN, TestData.VALID_PASSWORD);
        newsSteps.openNewsControlPanel();
    }

    @After
    public void deleteCreatedNews() {
        cleanupClient.deleteByTitles(createdTitles);
    }

    @Test
    @Feature("Создание новости")
    @DisplayName("Создание новости со всеми заполненными полями")
    public void createNews_withAllFieldsFilled_appearsAsActiveInControlPanel() {
        String title = TestData.randomNewsTitle();
        newsSteps.createNews(title, createdTitles);
        step("Новость создана со статусом Активна", () ->
                controlPanelPage.checkNewsStatus(title, R.string.news_control_panel_active));
    }

    @Test
    @Feature("Создание новости")
    @DisplayName("Форма создания: заголовок Создание и заблокированный переключатель активности")
    public void createForm_hasCreatingTitle_andLockedActiveSwitch() {
        newsSteps.openCreateNewsForm();
        step("В верхней панели заголовок Создание", () ->
                createEditNewsPage.checkFormTitle(R.string.creating));
        step("Переключатель Активна включён и недоступен", createEditNewsPage::checkActiveSwitchLockedOn);
    }

    @Test
    @Feature("Создание новости")
    @DisplayName("Выбор категории подставляет её в пустой заголовок")
    public void selectCategory_withEmptyTitle_fillsTitleWithCategory() {
        newsSteps.openCreateNewsForm();
        step("Выбрать категорию из выпадающего списка", () ->
                createEditNewsPage.selectCategoryFromDropdown(TestData.CATEGORY));
        step("Заголовок заполнен названием категории", () ->
                createEditNewsPage.checkTitle(TestData.CATEGORY));
    }

    @Test
    @Feature("Создание новости")
    @DisplayName("Сохранение новости с незаполненным описанием")
    public void saveNews_withEmptyDescription_showsToastAndKeepsForm() {
        String title = TestData.randomNewsTitle();
        createdTitles.add(title);
        newsSteps.openCreateNewsForm();
        step("Заполнить все поля, кроме Описания", () -> {
            createEditNewsPage.fillCategory(TestData.CATEGORY);
            createEditNewsPage.fillTitle(title);
            createEditNewsPage.pickTodayAsPublishDate();
            createEditNewsPage.pickPublishTime(23, 59);
        });
        step("Нажать Сохранить, тост о пустых полях", () ->
                createEditNewsPage.clickSaveAndExpectToast(R.string.empty_fields));
        step("Форма создания осталась открытой", createEditNewsPage::checkScreenDisplayed);
    }

    @Test
    @Feature("Создание новости")
    @DisplayName("Отмена создания новости с подтверждением")
    public void cancelCreation_afterConfirmation_doesNotSaveNews() {
        String title = TestData.randomNewsTitle();
        createdTitles.add(title);
        newsSteps.openCreateNewsForm();
        step("Заполнить заголовок", () -> createEditNewsPage.fillTitle(title));
        step("Нажать Отмена, показан диалог подтверждения", () -> {
            createEditNewsPage.clickCancel();
            createEditNewsPage.checkCancelDialogDisplayed();
        });
        step("Подтвердить, открыта панель управления", () -> {
            createEditNewsPage.confirmDialog();
            controlPanelPage.checkScreenDisplayed();
        });
        step("Новость с введённым заголовком не появилась в списке", () ->
                controlPanelPage.checkNewsWithTitleNotExist(title));
    }

    @Test
    @Feature("Создание новости")
    @DisplayName("Отказ от отмены создания сохраняет введённые данные")
    public void dismissCancelDialog_keepsFormWithEnteredData() {
        String title = TestData.randomNewsTitle();
        newsSteps.openCreateNewsForm();
        step("Заполнить заголовок", () -> createEditNewsPage.fillTitle(title));
        step("Нажать Отмена, в диалоге тоже Отмена", () -> {
            createEditNewsPage.clickCancel();
            createEditNewsPage.checkCancelDialogDisplayed();
            createEditNewsPage.dismissDialog();
        });
        step("Форма открыта, заголовок сохранён", () -> {
            createEditNewsPage.checkScreenDisplayed();
            createEditNewsPage.checkTitle(title);
        });
    }

    @Test
    @Feature("Удаление новости")
    @DisplayName("Удаление новости с подтверждением")
    public void deleteNews_afterConfirmation_removesFromList() {
        String title = TestData.randomNewsTitle();
        newsSteps.createNews(title, createdTitles);
        newsSteps.deleteNewsWithTitle(title, true);
        step("Новости нет в списке", () ->
                controlPanelPage.checkNewsWithTitleNotExist(title));
    }

    @Test
    @Feature("Удаление новости")
    @DisplayName("Отмена удаления новости")
    public void cancelDeletion_keepsNewsInList() {
        String title = TestData.randomNewsTitle();
        newsSteps.createNews(title, createdTitles);
        newsSteps.deleteNewsWithTitle(title, false);
        step("Новость осталась в списке", () -> controlPanelPage.checkNewsWithTitleDisplayed(title));
    }

    @Test
    @Feature("Редактирование новости")
    @DisplayName("Редактирование заголовка новости")
    public void editNews_withChangedTitle_updatesTitleInList() {
        String title = TestData.randomNewsTitle();
        String editedTitle = TestData.randomNewsTitle();
        newsSteps.createNews(title, createdTitles);
        createdTitles.add(editedTitle);

        step("Открыть редактирование созданной новости", () -> {
            controlPanelPage.clickEditForTitle(title);
            createEditNewsPage.checkScreenDisplayed();
            createEditNewsPage.checkFormTitle(R.string.editing);
            createEditNewsPage.checkTitle(title);
        });
        step("Изменить заголовок и сохранить", () -> {
            createEditNewsPage.fillTitle(editedTitle);
            createEditNewsPage.clickSave();
            controlPanelPage.checkScreenDisplayed();
        });
        step("В списке новый заголовок, старого заголовка нет", () -> {
            controlPanelPage.checkNewsWithTitleDisplayed(editedTitle);
            controlPanelPage.checkNewsWithTitleNotExist(title);
        });
    }

    @Test
    @Feature("Редактирование новости")
    @DisplayName("Снятие публикации новости через редактирование")
    public void editNews_switchOffActive_showsNotActiveStatus() {
        String title = TestData.randomNewsTitle();
        newsSteps.createNews(title, createdTitles);

        step("Открыть редактирование и выключить переключатель Активна", () -> {
            controlPanelPage.clickEditForTitle(title);
            createEditNewsPage.checkScreenDisplayed();
            createEditNewsPage.clickActiveSwitch();
            createEditNewsPage.checkActiveSwitchOff();
        });
        step("Сохранить", () -> {
            createEditNewsPage.clickSave();
            controlPanelPage.checkScreenDisplayed();
        });
        step("В карточке статус Не активна", () ->
                controlPanelPage.checkNewsStatus(title, R.string.news_control_panel_not_active));
    }

    @Test
    @Feature("Даты публикации")
    @DisplayName("Дату публикации нельзя выбрать в прошлом")
    public void publishDate_cannotBeSetBeforeToday() {
        newsSteps.openCreateNewsForm();
        step("Открыть выбор даты, minDate календаря равен текущему моменту", () -> {
            createEditNewsPage.openPublishDatePicker();
            createEditNewsPage.checkDatePickerMinDateIsNow(60_000);
        });
        step("Выбрать сегодняшнюю дату, она подставляется в поле", () -> {
            Calendar today = Calendar.getInstance();
            createEditNewsPage.setPickerDate(today);
            createEditNewsPage.confirmPicker();
            createEditNewsPage.checkPublishDate(today);
        });
    }
}
