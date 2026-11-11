package ru.edu.qamid.ui;

import static ru.edu.qamid.ui.utils.AllureSteps.step;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.edu.qamid.R;
import ru.edu.qamid.ui.data.TestData;
import ru.edu.qamid.ui.pages.AppBarPage;
import ru.edu.qamid.ui.pages.AuthPage;
import ru.edu.qamid.ui.pages.MainPage;
import ru.edu.qamid.ui.steps.AuthSteps;

@RunWith(AndroidJUnit4.class)
@Epic("Авторизация")
public class AuthTest extends BaseUiTest {

    private final AuthPage authPage = new AuthPage();
    private final MainPage mainPage = new MainPage();
    private final AppBarPage appBar = new AppBarPage();
    private final AuthSteps authSteps = new AuthSteps();

    @Before
    public void openAuthScreen() {
        authSteps.ensureLoggedOut();
    }

    @Test
    @Feature("Вход в систему")
    @DisplayName("Успешная авторизация")
    public void signIn_withValidCredentials_opensMainScreen() {
        step("Заполнить поле Логин", () -> {
            authPage.setLogin(TestData.VALID_LOGIN);
            authPage.checkLoginFieldContains(TestData.VALID_LOGIN);
        });
        step("Заполнить поле Пароль", () -> {
            authPage.setPassword(TestData.VALID_PASSWORD);
            authPage.checkPasswordFieldContains(TestData.VALID_PASSWORD);
        });
        step("Нажать Войти — открывается главный экран", () -> {
            authPage.clickEnter();
            mainPage.checkScreenDisplayed();
        });
    }

    @Test
    @Feature("Вход в систему")
    @DisplayName("Авторизация с незаполненными полями")
    public void signIn_withEmptyFields_showsEmptyFieldsToast() {
        step("Нажать Войти — показан Toast «Логин и пароль не могут быть пустыми»", () ->
                authPage.clickEnterAndExpectToast(R.string.empty_login_or_password));
        step("Главный экран не открыт", mainPage::checkScreenNotDisplayed);
    }

    @Test
    @Feature("Вход в систему")
    @DisplayName("Авторизация с заполненным только полем Логин")
    public void signIn_withOnlyLogin_showsEmptyFieldsToast() {
        step("Заполнить только поле Логин", () -> authPage.setLogin(TestData.VALID_LOGIN));
        step("Нажать Войти — показан Toast о пустых полях", () ->
                authPage.clickEnterAndExpectToast(R.string.empty_login_or_password));
        step("Главный экран не открыт", mainPage::checkScreenNotDisplayed);
    }

    @Test
    @Feature("Вход в систему")
    @DisplayName("Авторизация с заполненным только полем Пароль")
    public void signIn_withOnlyPassword_showsEmptyFieldsToast() {
        step("Заполнить только поле Пароль", () -> authPage.setPassword(TestData.VALID_PASSWORD));
        step("Нажать Войти — показан Toast о пустых полях", () ->
                authPage.clickEnterAndExpectToast(R.string.empty_login_or_password));
        step("Главный экран не открыт", mainPage::checkScreenNotDisplayed);
    }

    @Test
    @Feature("Вход в систему")
    @DisplayName("Авторизация с логином и паролем из одних пробелов")
    public void signIn_withBlankCredentials_showsEmptyFieldsToast() {
        step("Заполнить поля Логин и Пароль пробелами", () -> {
            authPage.setLogin("   ");
            authPage.setPassword("   ");
        });
        step("Нажать Войти — показан Toast о пустых полях", () ->
                authPage.clickEnterAndExpectToast(R.string.empty_login_or_password));
        step("Главный экран не открыт", mainPage::checkScreenNotDisplayed);
    }



    @Test
    @Feature("Вход в систему")
    @DisplayName("Авторизация с верным логином и неверным паролем")
    public void signIn_withWrongPassword_showsWrongCredentialsToast() {
        step("Заполнить верный логин и неверный пароль", () -> {
            authPage.setLogin(TestData.VALID_LOGIN);
            authPage.setPassword(TestData.randomPassword());
        });
        step("Нажать Войти — показан Toast «Неверный логин или пароль»", () ->
                authPage.clickEnterAndExpectToast(R.string.wrong_login_or_password));
        step("Кнопка Войти снова доступна, индикатор загрузки скрыт", authPage::checkEnterButtonReady);
        step("Главный экран не открыт", mainPage::checkScreenNotDisplayed);
    }


    @Test
    @Feature("Вход в систему")
    @DisplayName("Авторизация со случайными несуществующими учётными данными")
    public void signIn_withRandomCredentials_showsWrongCredentialsToast() {
        String login = TestData.randomLogin();
        String password = TestData.randomPassword();
        step("Заполнить поля случайными значениями", () -> {
            authPage.setLogin(login);
            authPage.setPassword(password);
        });
        step("Нажать Войти — показан Toast «Неверный логин или пароль»", () ->
                authPage.clickEnterAndExpectToast(R.string.wrong_login_or_password));
        step("Кнопка Войти снова доступна, индикатор загрузки скрыт", authPage::checkEnterButtonReady);
        step("Главный экран не открыт", mainPage::checkScreenNotDisplayed);
    }

    @Test
    @Feature("Выход из системы")
    @DisplayName("Выход из личного кабинета")
    public void logOut_afterSignIn_returnsToAuthScreen() {
        authSteps.signIn(TestData.VALID_LOGIN, TestData.VALID_PASSWORD);
        step("Открыть меню личного кабинета", appBar::openPersonalCabinet);
        step("Нажать Выйти — открыт экран авторизации", () -> {
            appBar.clickLogOut();
            authPage.checkScreenDisplayed();
            mainPage.checkScreenNotDisplayed();
        });
    }
}
