package ru.edu.qamid.ui.steps;

import static ru.edu.qamid.ui.utils.AllureSteps.step;

import java.util.Calendar;

import ru.edu.qamid.R;
import ru.edu.qamid.ui.data.TestData;
import ru.edu.qamid.ui.pages.AppBarPage;
import ru.edu.qamid.ui.pages.CreateEditNewsPage;
import ru.edu.qamid.ui.pages.FilterPage;
import ru.edu.qamid.ui.pages.NewsControlPanelPage;
import ru.edu.qamid.ui.pages.NewsListPage;

public class NewsSteps {

    private final AppBarPage appBar = new AppBarPage();
    private final NewsListPage newsListPage = new NewsListPage();
    private final NewsControlPanelPage controlPanelPage = new NewsControlPanelPage();
    private final CreateEditNewsPage createEditNewsPage = new CreateEditNewsPage();
    private final FilterPage filterPage = new FilterPage();

    public void openNewsList() {
        step("Открыть раздел Новости через главное меню", () -> {
            appBar.openMainMenu();
            appBar.clickMenuItem(R.string.news);
            newsListPage.checkScreenDisplayed();
        });
    }

    public void openNewsControlPanel() {
        openNewsList();
        step("Нажать кнопку редактирования новостей", () -> {
            newsListPage.clickEditNewsButton();
            controlPanelPage.checkScreenDisplayed();
        });
    }

    public void openCreateNewsForm() {
        step("Нажать кнопку добавления новости", () -> {
            controlPanelPage.clickAddNews();
            createEditNewsPage.checkScreenDisplayed();
        });
    }

    public void createNews(String title) {
        openCreateNewsForm();
        step("Заполнить все поля формы", () -> {
            createEditNewsPage.fillCategory(TestData.CATEGORY);
            createEditNewsPage.fillTitle(title);
            createEditNewsPage.pickTodayAsPublishDate();
            createEditNewsPage.pickPublishTime(23, 59);
            createEditNewsPage.fillDescription(TestData.DESCRIPTION);
        });
        saveNews();
        checkNewsInList(title);
    }

    public void saveNews() {
        step("Нажать Сохранить, открыта панель управления", () -> {
            createEditNewsPage.clickSave();
            controlPanelPage.checkScreenDisplayed();
        });
    }

    public void checkNewsInList(String title) {
        step("Новость есть в списке панели управления", () ->
                controlPanelPage.checkNewsWithTitleDisplayed(title));
    }

    public void checkNewsNotInList(String title) {
        step("Новости нет в списке панели управления", () ->
                controlPanelPage.checkNewsWithTitleNotExist(title));
    }

    public void checkNewsIsActive(String title) {
        step("У новости статус Активна", () ->
                controlPanelPage.checkNewsStatus(title, R.string.news_control_panel_active));
    }

    public void checkNewsIsNotActive(String title) {
        step("У новости статус Не активна", () ->
                controlPanelPage.checkNewsStatus(title, R.string.news_control_panel_not_active));
    }

    public void checkCreateFormTitle() {
        step("В верхней панели заголовок Создание", () ->
                createEditNewsPage.checkFormTitle(R.string.creating));
    }

    public void checkActiveSwitchLockedOn() {
        step("Переключатель Активна включён и недоступен", createEditNewsPage::checkActiveSwitchLockedOn);
    }

    public void selectCategory(String category) {
        step("Выбрать категорию из выпадающего списка", () ->
                createEditNewsPage.selectCategoryFromDropdown(category));
    }

    public void fillTitle(String title) {
        step("Заполнить заголовок", () -> createEditNewsPage.fillTitle(title));
    }

    public void checkTitle(String title) {
        step("В поле Заголовок значение '" + title + "'", () -> createEditNewsPage.checkTitle(title));
    }

    public void fillAllFieldsExceptDescription(String title) {
        step("Заполнить все поля, кроме Описания", () -> {
            createEditNewsPage.fillCategory(TestData.CATEGORY);
            createEditNewsPage.fillTitle(title);
            createEditNewsPage.pickTodayAsPublishDate();
            createEditNewsPage.pickPublishTime(23, 59);
        });
    }

    public void clickSaveAndCheckEmptyFieldsToast() {
        step("Нажать Сохранить, тост о пустых полях", () ->
                createEditNewsPage.clickSaveAndExpectToast(R.string.empty_fields));
    }

    public void checkFormDisplayed() {
        step("Форма осталась открытой", createEditNewsPage::checkScreenDisplayed);
    }

    public void clickCancel() {
        step("Нажать Отмена, показан диалог подтверждения", () -> {
            createEditNewsPage.clickCancel();
            createEditNewsPage.checkCancelDialogDisplayed();
        });
    }

    public void confirmCancel() {
        step("Подтвердить, открыта панель управления", () -> {
            createEditNewsPage.confirmDialog();
            controlPanelPage.checkScreenDisplayed();
        });
    }

    public void dismissCancel() {
        step("В диалоге нажать Отмена, форма осталась открытой", () -> {
            createEditNewsPage.dismissDialog();
            createEditNewsPage.checkScreenDisplayed();
        });
    }

    public void deleteNewsWithTitle(String title, boolean confirm) {
        step("Нажать иконку удаления у новости '" + title + "'", () -> {
            controlPanelPage.clickDeleteForTitle(title);
            controlPanelPage.checkDeleteDialogDisplayed();
        });
        if (confirm) {
            step("Подтвердить удаление", controlPanelPage::confirmDialog);
        } else {
            step("Отменить удаление", controlPanelPage::cancelDialog);
        }
    }

    public void openEditForm(String title) {
        step("Открыть редактирование новости", () -> {
            controlPanelPage.clickEditForTitle(title);
            createEditNewsPage.checkScreenDisplayed();
            createEditNewsPage.checkFormTitle(R.string.editing);
            createEditNewsPage.checkTitle(title);
        });
    }

    public void switchOffActive() {
        step("Выключить переключатель Активна", () -> {
            createEditNewsPage.clickActiveSwitch();
            createEditNewsPage.checkActiveSwitchOff();
        });
    }

    public void checkPublishDateMinIsToday() {
        step("Открыть выбор даты, минимальная дата календаря равна сегодняшней", () -> {
            createEditNewsPage.openPublishDatePicker();
            createEditNewsPage.checkDatePickerMinDateIsNow(60_000);
        });
    }

    public void pickTodayAndCheckPublishDate() {
        step("Выбрать сегодняшнюю дату, она подставляется в поле", () -> {
            Calendar today = Calendar.getInstance();
            createEditNewsPage.setPickerDate(today);
            createEditNewsPage.confirmPicker();
            createEditNewsPage.checkPublishDate(today);
        });
    }

    public void openFilterFromNewsList() {
        openNewsList();
        step("Нажать кнопку фильтра", () -> {
            newsListPage.clickFilterButton();
            filterPage.checkScreenDisplayed();
        });
    }

    public void openFilterFromControlPanel() {
        openNewsControlPanel();
        step("Нажать кнопку фильтра в панели управления", () -> {
            controlPanelPage.clickFilterButton();
            filterPage.checkScreenDisplayed();
        });
    }
}
