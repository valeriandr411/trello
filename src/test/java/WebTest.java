import check.WebCheck;
import com.codeborne.selenide.Configuration;
import database.JDBCPostgreSQL;
import io.qameta.allure.Description;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import steps.WebSteps;
import support.Color;
import utils.PropertiesUtil;

public class WebTest {
    private final WebSteps webSteps = new WebSteps();
    private final WebCheck webCheck = new WebCheck();

    @BeforeClass
    public void setUp() {
        Configuration.baseUrl = "https://trello.com";
        Configuration.browser = PropertiesUtil.get("browser.name");
        Configuration.browserSize = PropertiesUtil.get("browser.size");
        Configuration.browserVersion = PropertiesUtil.get("browser.version");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", PropertiesUtil.get("enableVNC"));
        Configuration.browserCapabilities = capabilities;
    }

    public void login() {
        webSteps.clickButtonContainsText("Log in");
        final String login = PropertiesUtil.get("test.user");
        webSteps.inputTextInField("Укажите адрес электронной почты", login);
        webSteps.clickButtonContainsText("Продолжить");
        webSteps.inputTextInField("Введите пароль", JDBCPostgreSQL.getPassword(login));
        webSteps.clickElementWithText("Войти");
    }

    @Test
    @Description(value = "Тестирование UI")
    public void uiTest() {
        webSteps.openUrl("https://trello.com/");
        login();
        webSteps.clickElementContainsText("Ученики");

//Шаг 1. Удостовериться, что карточка находится в колонке Done
        webCheck.checkCardInList("Done", "Карточка для изучения API");

//Шаг 2. Удостовериться, все пункты чек-боксов выполнены
        webSteps.clickElementContainsText("Карточка для изучения API");
        webCheck.checkIfCheckboxIsComplete("1.Понять протокол HTTP");
        webCheck.checkIfCheckboxIsComplete("2.Выучить методы запросов");

//Шаг 3. Поставить обложку с зеленым цветом
        webSteps.clickElementWithText("Обложка");
        webSteps.setColorForCurrentCard("Цвета", Color.GREEN);

//Шаг 4. Отметить, что задача выполнена в срок
        webSteps.clickElementWithText("Даты");
        webSteps.activatedCheckBox("Срок");
        webSteps.clickButtonWithText("Сохранить");
        webSteps.activatedCheckBox("Отметить как выполненное в срок");

//Шаг 5. Сменить фон доски на зеленый цвет
        webSteps.clickElementContainsText("Закрыть диалоговое окно");
        webSteps.clickButtonContainsText("Меню");
        webSteps.clickElementContainsText("Сменить фон");
        webSteps.clickElementContainsText("Цвета");
        webSteps.clickElementContainsText("Вернуться.");
        webSteps.clickElementContainsText("Вернуться.");
        webSteps.clickElementContainsText("Закрыть меню доски");

//Шаг 6. Добавить метку зеленого цвета с текстом: Средний приоритет
        webSteps.clickElementContainsText("Карточка для изучения API");
        webSteps.clickElementContainsText("Метки");
        webSteps.clickButtonContainsText("Создать новую метку");
        webSteps.inputTextInField("Название", "Средний приоритет");
        webSteps.clickButtonWithText("Создание");
        webSteps.clickElementContainsText("Закрыть всплывающее окно");
        webSteps.clickElementContainsText("Закрыть диалоговое окно");

//Шаг 7. Добавить описание к доске: Доска создана автотестом
        webSteps.clickButtonContainsText("Меню");
        webSteps.clickElementContainsText("О доске");
        webSteps.clickElementContainsText("для чего используется эта доска");
        webSteps.inputTextInTextBox("Доска создана автотестом");

//Шаг 8. Сменить имя доски "Только для образования"

    }

    public void after() {
        //удалить доску
    }
}
