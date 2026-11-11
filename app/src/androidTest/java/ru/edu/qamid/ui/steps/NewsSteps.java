package ru.edu.qamid.ui.steps;

import static ru.edu.qamid.ui.utils.AllureSteps.step;

import java.util.List;

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

    public void createNews(String title, List<String> createdTitles) {
        openCreateNewsForm();
        step("Заполнить все поля формы", () -> {
            createEditNewsPage.fillCategory(TestData.CATEGORY);
            createEditNewsPage.fillTitle(title);
            createEditNewsPage.pickTodayAsPublishDate();
            createEditNewsPage.pickPublishTime(23, 59);
            createEditNewsPage.fillDescription(TestData.DESCRIPTION);
        });
        createdTitles.add(title);
        step("Нажать кнопку Сохранить", () -> {
            createEditNewsPage.clickSave();
            controlPanelPage.checkScreenDisplayed();
        });
        step("В панели управления есть созданная новость", () ->
                controlPanelPage.checkNewsWithTitleDisplayed(title));
    }

    public void deleteNewsWithTitle(String title, boolean confirm) {
        step("Нажать иконку удаления у новости «" + title + "»", () -> {
            controlPanelPage.clickDeleteForTitle(title);
            controlPanelPage.checkDeleteDialogDisplayed();
        });
        if (confirm) {
            step("Подтвердить удаление", controlPanelPage::confirmDialog);
        } else {
            step("Отменить удаление", controlPanelPage::cancelDialog);
        }
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
