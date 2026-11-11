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
import ru.edu.qamid.ui.steps.NewsSteps;

@RunWith(AndroidJUnit4.class)
@Epic("Новости")
public class NewsTest extends BaseUiTest {

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
    @Description("Новость со всеми заполненными полями сохраняется и видна в панели управления со статусом Активна")
    public void createNewsWithAllFieldsFilled() {
        String title = TestData.randomNewsTitle();
        createdTitles.add(title);
        newsSteps.createNews(title);
        newsSteps.checkNewsIsActive(title);
    }

    @Test
    @Feature("Создание новости")
    @DisplayName("Форма создания: заголовок Создание и заблокированный переключатель активности")
    @Description("При создании новости в шапке написано Создание, переключатель Активна включён и его нельзя изменить")
    public void createFormHasLockedActiveSwitch() {
        newsSteps.openCreateNewsForm();
        newsSteps.checkCreateFormTitle();
        newsSteps.checkActiveSwitchLockedOn();
    }

    @Test
    @Feature("Создание новости")
    @DisplayName("Выбор категории подставляет её в пустой заголовок")
    @Description("Если заголовок пустой, после выбора категории в него подставляется название категории")
    public void selectCategoryFillsEmptyTitle() {
        newsSteps.openCreateNewsForm();
        newsSteps.selectCategory(TestData.CATEGORY);
        newsSteps.checkTitle(TestData.CATEGORY);
    }

    @Test
    @Feature("Создание новости")
    @DisplayName("Сохранение новости с незаполненным описанием")
    @Description("Без описания новость не сохраняется, показывается сообщение Заполните пустые поля")
    public void saveNewsWithEmptyDescription() {
        String title = TestData.randomNewsTitle();
        createdTitles.add(title);
        newsSteps.openCreateNewsForm();
        newsSteps.fillAllFieldsExceptDescription(title);
        newsSteps.clickSaveAndCheckEmptyFieldsToast();
        newsSteps.checkFormDisplayed();
    }

    @Test
    @Feature("Создание новости")
    @DisplayName("Отмена создания новости с подтверждением")
    @Description("После отмены создания и подтверждения в диалоге новость не появляется в списке")
    public void cancelCreationDoesNotSaveNews() {
        String title = TestData.randomNewsTitle();
        createdTitles.add(title);
        newsSteps.openCreateNewsForm();
        newsSteps.fillTitle(title);
        newsSteps.clickCancel();
        newsSteps.confirmCancel();
        newsSteps.checkNewsNotInList(title);
    }

    @Test
    @Feature("Создание новости")
    @DisplayName("Отказ от отмены создания сохраняет введённые данные")
    @Description("Если в диалоге отмены нажать Отмена, форма остаётся открытой и заголовок не стирается")
    public void dismissCancelDialogKeepsEnteredData() {
        String title = TestData.randomNewsTitle();
        createdTitles.add(title);
        newsSteps.openCreateNewsForm();
        newsSteps.fillTitle(title);
        newsSteps.clickCancel();
        newsSteps.dismissCancel();
        newsSteps.checkTitle(title);
    }

    @Test
    @Feature("Удаление новости")
    @DisplayName("Удаление новости с подтверждением")
    @Description("После подтверждения удаления новость пропадает из панели управления")
    public void deleteNewsRemovesItFromList() {
        String title = TestData.randomNewsTitle();
        createdTitles.add(title);
        newsSteps.createNews(title);
        newsSteps.deleteNewsWithTitle(title, true);
        newsSteps.checkNewsNotInList(title);
    }

    @Test
    @Feature("Удаление новости")
    @DisplayName("Отмена удаления новости")
    @Description("Если в диалоге удаления нажать Отмена, новость остаётся в списке")
    public void cancelDeletionKeepsNewsInList() {
        String title = TestData.randomNewsTitle();
        createdTitles.add(title);
        newsSteps.createNews(title);
        newsSteps.deleteNewsWithTitle(title, false);
        newsSteps.checkNewsInList(title);
    }

    @Test
    @Feature("Редактирование новости")
    @DisplayName("Редактирование заголовка новости")
    @Description("После изменения заголовка в списке виден новый заголовок, старого нет")
    public void editNewsTitle() {
        String title = TestData.randomNewsTitle();
        String editedTitle = TestData.randomNewsTitle();
        createdTitles.add(title);
        createdTitles.add(editedTitle);
        newsSteps.createNews(title);
        newsSteps.openEditForm(title);
        newsSteps.fillTitle(editedTitle);
        newsSteps.saveNews();
        newsSteps.checkNewsInList(editedTitle);
        newsSteps.checkNewsNotInList(title);
    }

    @Test
    @Feature("Редактирование новости")
    @DisplayName("Снятие публикации новости через редактирование")
    @Description("После выключения переключателя Активна у новости статус Не активна")
    public void editNewsSwitchOffActive() {
        String title = TestData.randomNewsTitle();
        createdTitles.add(title);
        newsSteps.createNews(title);
        newsSteps.openEditForm(title);
        newsSteps.switchOffActive();
        newsSteps.saveNews();
        newsSteps.checkNewsIsNotActive(title);
    }

    @Test
    @Feature("Даты публикации")
    @DisplayName("Дату публикации нельзя выбрать в прошлом")
    @Description("В календаре даты публикации минимальная дата равна сегодняшней")
    public void publishDateCannotBeBeforeToday() {
        newsSteps.openCreateNewsForm();
        newsSteps.checkPublishDateMinIsToday();
        newsSteps.pickTodayAndCheckPublishDate();
    }
}
