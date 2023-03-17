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
        Configuration.baseUrl = PropertiesUtil.get("base.url");
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

 //   @Test(dependsOnMethods = "RestTest.apiTest")
    @Test
    @Description(value = "Тестирование UI")
    public void uiTest() throws Exception {
        webSteps.openUrl(PropertiesUtil.get("base.url"));
        login();
        webSteps.clickElementContainsText("KanbanTool");

//Шаг 1. Удостовериться, что карточка находится в колонке Done
        webCheck.checkCardInList("Done", "Карточка для изучения API");

//Шаг 2. Удостовериться, все пункты чек-боксов выполнены
        webSteps.clickElementContainsText("Карточка для изучения API");
        webCheck.checkIfCheckboxIsComplete("1.Понять протокол HTTP");
        webCheck.checkIfCheckboxIsComplete("2.Выучить методы запросов");

//Шаг 3. Поставить обложку с зеленым цветом
        webSteps.clickButtonContainsText("Обложка");
        webSteps.setColorForCurrentCard("Цвета", Color.GREEN);

//Шаг 4. Отметить, что задача выполнена в срок
        webSteps.clickElementWithText("Даты");
        webSteps.activatedCheckBox("Срок");
        webSteps.clickButtonWithText("Сохранить");
        webSteps.activatedCheckBox("Отметить как выполненное в срок");

//Шаг 5. Сменить фон доски
        webSteps.clickElementContainsText("Закрыть диалоговое окно");
        webSteps.clickButtonContainsText("Меню");
        webSteps.clickElementContainsText("Сменить фон");
        webSteps.selectBackground("mills");
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
        webSteps.clickElementWithText("О доске");
        webSteps.clickElementContainsText("для чего используется эта доска");
        webSteps.inputTextInTextBox("Доска создана автотестом");
        webSteps.clickButtonContainsText("Сохранить");
        webSteps.clickElementContainsText("Вернуться.");
        webSteps.clickElementContainsText("Закрыть меню доски");

//Шаг 8. Сменить имя доски "Доска для обучения"
        webSteps.setCurrBlock(webSteps.clickHeader("KanbanTool"));
        webSteps.pressKey("DELETE");
        webSteps.inputTextInCurrBlock("Доска для обучения");
        webSteps.pressKey("ENTER");
    }
    @AfterClass
    public void after() throws Exception {
        webSteps.clickButtonContainsText("Меню");
        webSteps.clickElementContainsText("Ещё");
        webSteps.clickElementContainsText("Закрыть доску");
        webSteps.setCurrBlock("Закрытие доски");
        webSteps.inCurrentBlockClickElementWithText("Закрыть");
        webSteps.clickButtonWithText("Удалить доску навсегда");
        webSteps.clickButtonContainsText("Удалить");
    }
}
