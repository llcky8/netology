package ru.edu.qamid.ui.steps;

import static ru.edu.qamid.ui.utils.AllureUtils.step;

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

    public void goToNewsList() {
        step("Открыть раздел Новости через главное меню", () -> {
            appBar.openMainMenu();
            appBar.selectMenuItem(R.string.news);
            newsListPage.checkScreenDisplayed();
        });
    }

    public void goToControlPanel() {
        goToNewsList();
        step("Нажать кнопку редактирования новостей", () -> {
            newsListPage.clickControlPanelButton();
            controlPanelPage.checkScreenDisplayed();
        });
    }

    public void openNewsForm() {
        step("Нажать плюс в панели управления", () -> {
            controlPanelPage.clickPlusButton();
            createEditNewsPage.checkScreenDisplayed();
        });
    }

    public void addNews(String title) {
        openNewsForm();
        step("Заполнить все поля формы", () -> {
            createEditNewsPage.enterCategory(TestData.CATEGORY);
            createEditNewsPage.enterTitle(title);
            createEditNewsPage.setTodayPublishDate();
            createEditNewsPage.setPublishTime(23, 59);
            createEditNewsPage.enterDescription(TestData.DESCRIPTION);
        });
        saveNews();
        checkNewsPresent(title);
    }

    public void saveNews() {
        step("Нажать Сохранить, открыта панель управления", () -> {
            createEditNewsPage.clickSave();
            controlPanelPage.checkScreenDisplayed();
        });
    }

    public void checkNewsPresent(String title) {
        step("Новость есть в списке панели управления", () ->
                controlPanelPage.checkNewsShown(title));
    }

    public void checkNewsMissing(String title) {
        step("Новости нет в списке панели управления", () ->
                controlPanelPage.checkNewsAbsent(title));
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

    public void enterTitle(String title) {
        step("Заполнить заголовок", () -> createEditNewsPage.enterTitle(title));
    }

    public void checkTitle(String title) {
        step("В поле Заголовок значение '" + title + "'", () -> createEditNewsPage.checkTitle(title));
    }

    public void fillAllFieldsExceptDescription(String title) {
        step("Заполнить все поля, кроме Описания", () -> {
            createEditNewsPage.enterCategory(TestData.CATEGORY);
            createEditNewsPage.enterTitle(title);
            createEditNewsPage.setTodayPublishDate();
            createEditNewsPage.setPublishTime(23, 59);
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

    public void deleteNews(String title) {
        openDeleteDialog(title);
        step("Нажать ОК в диалоге удаления", controlPanelPage::confirmDialog);
    }

    public void cancelNewsDeletion(String title) {
        openDeleteDialog(title);
        step("Нажать Отмена в диалоге удаления", controlPanelPage::cancelDialog);
    }

    private void openDeleteDialog(String title) {
        step("Нажать корзину у новости '" + title + "'", () -> {
            controlPanelPage.clickDeleteIcon(title);
            controlPanelPage.checkDeleteDialogDisplayed();
        });
    }

    public void openEditForm(String title) {
        step("Открыть редактирование новости", () -> {
            controlPanelPage.clickEditIcon(title);
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

    public void goToFilterFromNewsList() {
        goToNewsList();
        step("Открыть фильтр", () -> {
            newsListPage.clickFilterIcon();
            filterPage.checkScreenDisplayed();
        });
    }

    public void goToFilterFromControlPanel() {
        goToControlPanel();
        step("Нажать кнопку фильтра в панели управления", () -> {
            controlPanelPage.clickFilterIcon();
            filterPage.checkScreenDisplayed();
        });
    }
}
