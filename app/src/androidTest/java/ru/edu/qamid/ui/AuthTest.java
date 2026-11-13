package ru.edu.qamid.ui;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.edu.qamid.ui.data.TestData;
import ru.edu.qamid.ui.steps.AuthSteps;

@RunWith(AndroidJUnit4.class)
@Epic("Вход в приложение")
public class AuthTest extends BaseUiTest {

    private final AuthSteps authSteps = new AuthSteps();

    @Before
    public void openAuthScreen() {
        authSteps.makeSureLoggedOut();
    }

    @Test
    @Feature("Логин")
    @DisplayName("Вход с правильными логином и паролем")
    @Description("Вход с логином login2 и паролем password2, должен открыться главный экран")
    public void signInWithValidCredentials() {
        authSteps.fillLogin(TestData.VALID_LOGIN);
        authSteps.fillPassword(TestData.VALID_PASSWORD);
        authSteps.clickEnterAndCheckMainScreen();
    }

    @Test
    @Feature("Логин")
    @DisplayName("Пробелы по краям логина и пароля")
    @Description("Пробелы до и после логина и пароля не мешают входу")
    public void signInWithSpacesAroundCredentials() {
        authSteps.fillLogin(" " + TestData.VALID_LOGIN + " ");
        authSteps.fillPassword(" " + TestData.VALID_PASSWORD + " ");
        authSteps.clickEnterAndCheckMainScreen();
    }

    @Test
    @Feature("Логин")
    @DisplayName("Оба поля пустые")
    @Description("Нажатие Войти с пустыми полями показывает сообщение о пустых полях")
    public void signInWithEmptyFields() {
        authSteps.clickEnterAndCheckEmptyFieldsToast();
        authSteps.checkAuthScreenDisplayed();
    }

    @Test
    @Feature("Логин")
    @DisplayName("Пароль не заполнен")
    @Description("Заполнен только логин, вход не выполняется, показывается сообщение о пустых полях")
    public void signInWithOnlyLogin() {
        authSteps.fillLogin(TestData.fakeLogin());
        authSteps.clickEnterAndCheckEmptyFieldsToast();
        authSteps.checkAuthScreenDisplayed();
    }

    @Test
    @Feature("Логин")
    @DisplayName("Логин не заполнен")
    @Description("Заполнен только пароль, вход не выполняется, показывается сообщение о пустых полях")
    public void signInWithOnlyPassword() {
        authSteps.fillPassword(TestData.fakePassword());
        authSteps.clickEnterAndCheckEmptyFieldsToast();
        authSteps.checkAuthScreenDisplayed();
    }

    @Test
    @Feature("Логин")
    @DisplayName("В полях только пробелы")
    @Description("Поля из одних пробелов считаются пустыми")
    public void signInWithBlankCredentials() {
        authSteps.fillLogin("   ");
        authSteps.fillPassword("   ");
        authSteps.clickEnterAndCheckEmptyFieldsToast();
        authSteps.checkAuthScreenDisplayed();
    }

    @Test
    @Feature("Логин")
    @DisplayName("Неправильный пароль")
    @Description("Верный логин и неверный пароль, показывается сообщение о неверном логине или пароле")
    public void signInWithWrongPassword() {
        authSteps.fillLogin(TestData.VALID_LOGIN);
        authSteps.fillPassword(TestData.fakePassword());
        authSteps.clickEnterAndCheckWrongCredentialsToast();
        authSteps.checkSignInButtonActive();
    }

    @Test
    @Feature("Логин")
    @DisplayName("Пользователя нет в системе")
    @Description("Логин и пароль, которых нет в системе, показывается сообщение о неверном логине или пароле")
    public void signInWithUnknownUser() {
        authSteps.fillLogin(TestData.fakeLogin());
        authSteps.fillPassword(TestData.fakePassword());
        authSteps.clickEnterAndCheckWrongCredentialsToast();
        authSteps.checkSignInButtonActive();
    }

    @Test
    @Feature("Логаут")
    @DisplayName("Выход с главного экрана")
    @Description("После выхода через меню личного кабинета открывается экран авторизации")
    public void logOutFromMainScreen() {
        authSteps.logIn(TestData.VALID_LOGIN, TestData.VALID_PASSWORD);
        authSteps.logOut();
    }
}
