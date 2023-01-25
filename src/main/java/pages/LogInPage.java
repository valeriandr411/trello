package pages;

import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Selenide.*;

/**
 * Страница https://trello.com/
 */
@Deprecated
public class LogInPage {
    @FindBy
    private final String loginButton = ".//a[@href='/login'][ancestor::div[contains(@class,'NavBar')]]" +
            "| .//span[text()='Войти']";
    private final String emailField = " //input[@placeholder='Укажите адрес электронной почты']" +
            "| .//input[@type='email']";
    private final String nextButton = " //input[@value='Продолжить'] | .//span[text()='Далее']";
    private final String passwordField = "//input[@name='password']";
    private final String googleButton = ".//a[@id='googleButton']";


    public void typeEmail(String email) {
        $x(emailField).sendKeys(email);
    }

    public void typePassword(String password) {
        $x(passwordField).sendKeys(password);
    }

    public void loginButtonClick() {
        $x(loginButton).click();
    }

    public void nextButtonClick() {
        $x(nextButton).click();
    }

    public void googleButtonClick() {
        $x(googleButton).click();
    }
    // Кнопка войти
    // .//a[@href='/login'][ancestor::div[contains(@class,'NavBar')]]

    //Кнопка зарегистрироваться
    //span[contains(text(),'Зарегистрироваться')]/parent::span


    // Вход в Trello
}
