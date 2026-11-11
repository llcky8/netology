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
@Epic("Авторизация")
public class AuthTest extends BaseUiTest {

    private final AuthSteps authSteps = new AuthSteps();

    @Before
    public void openAuthScreen() {
        authSteps.ensureLoggedOut();
    }

    @Test
    @Feature("Вход в систему")
    @DisplayName("Успешная авторизация")
    @Description("Вход с логином login2 и паролем password2, должен открыться главный экран")
    public void signInWithValidCredentials() {
        authSteps.fillLogin(TestData.VALID_LOGIN);
        authSteps.fillPassword(TestData.VALID_PASSWORD);
        authSteps.clickEnterAndCheckMainScreen();
    }

    @Test
    @Feature("Вход в систему")
    @DisplayName("Авторизация с пробелами вокруг логина и пароля")
    @Description("Пробелы до и после логина и пароля не мешают входу")
    public void signInWithSpacesAroundCredentials() {
        authSteps.fillLogin(" " + TestData.VALID_LOGIN + " ");
        authSteps.fillPassword(" " + TestData.VALID_PASSWORD + " ");
        authSteps.clickEnterAndCheckMainScreen();
    }

    @Test
    @Feature("Вход в систему")
    @DisplayName("Авторизация с незаполненными полями")
    @Description("Нажатие Войти с пустыми полями показывает сообщение о пустых полях")
    public void signInWithEmptyFields() {
        authSteps.clickEnterAndCheckEmptyFieldsToast();
        authSteps.checkAuthScreenDisplayed();
    }

    @Test
    @Feature("Вход в систему")
    @DisplayName("Авторизация с заполненным только полем Логин")
    @Description("Заполнен только логин, вход не выполняется, показывается сообщение о пустых полях")
    public void signInWithOnlyLogin() {
        authSteps.fillLogin(TestData.VALID_LOGIN);
        authSteps.clickEnterAndCheckEmptyFieldsToast();
        authSteps.checkAuthScreenDisplayed();
    }

    @Test
    @Feature("Вход в систему")
    @DisplayName("Авторизация с заполненным только полем Пароль")
    @Description("Заполнен только пароль, вход не выполняется, показывается сообщение о пустых полях")
    public void signInWithOnlyPassword() {
        authSteps.fillPassword(TestData.VALID_PASSWORD);
        authSteps.clickEnterAndCheckEmptyFieldsToast();
        authSteps.checkAuthScreenDisplayed();
    }

    @Test
    @Feature("Вход в систему")
    @DisplayName("Авторизация с логином и паролем из одних пробелов")
    @Description("Поля из одних пробелов считаются пустыми")
    public void signInWithBlankCredentials() {
        authSteps.fillLogin("   ");
        authSteps.fillPassword("   ");
        authSteps.clickEnterAndCheckEmptyFieldsToast();
        authSteps.checkAuthScreenDisplayed();
    }

    @Test
    @Feature("Вход в систему")
    @DisplayName("Авторизация с верным логином и неверным паролем")
    @Description("Верный логин и неверный пароль, показывается сообщение о неверном логине или пароле")
    public void signInWithWrongPassword() {
        authSteps.fillLogin(TestData.VALID_LOGIN);
        authSteps.fillPassword(TestData.randomPassword());
        authSteps.clickEnterAndCheckWrongCredentialsToast();
        authSteps.checkEnterButtonReady();
    }

    @Test
    @Feature("Вход в систему")
    @DisplayName("Авторизация несуществующего пользователя")
    @Description("Логин и пароль, которых нет в системе, показывается сообщение о неверном логине или пароле")
    public void signInWithUnknownUser() {
        authSteps.fillLogin(TestData.randomLogin());
        authSteps.fillPassword(TestData.randomPassword());
        authSteps.clickEnterAndCheckWrongCredentialsToast();
        authSteps.checkEnterButtonReady();
    }

    @Test
    @Feature("Выход из системы")
    @DisplayName("Выход из личного кабинета")
    @Description("После выхода через меню личного кабинета открывается экран авторизации")
    public void logOutFromMainScreen() {
        authSteps.signIn(TestData.VALID_LOGIN, TestData.VALID_PASSWORD);
        authSteps.logOut();
    }
}
