import check.WebCheck;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import database.JDBCPostgreSQL;
import io.qameta.allure.Description;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import steps.WebSteps;
import utils.PropertiesUtil;

public class WebTest {
    private final WebSteps webSteps = new WebSteps();

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

        webSteps.clickButtonWithText("Log in");
        final String login = PropertiesUtil.get("test.user");
        webSteps.inputTextInField("Укажите адрес электронной почты", login);
        webSteps.clickButtonWithText("Продолжить");
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
        //отображается колонка Done
        //колонка Done содержит карточку

//Шаг 2. Удостовериться, все пункты чек-боксов выполнены
        //открыть карточку
        //проверить, что карточка содержит чеклист
        //каждый элемент в классе содержит : state-complete

//Шаг 3. Поставить обложку с зеленым цветом
        webSteps.clickElementContainsText("Карточка для изучения API");
        // webSteps.clickElementWithText("Обложка");
        // webSteps.setColorForCurrentCard(Color.BLUE);

//Шаг 4. Отметить, что задача выполнена в срок
//        webSteps.clickElementWithText("Даты");
//        webSteps.activatedCheckBox("Срок");
//        webSteps.clickElementWithText("Сохранить");
//        webSteps.activatedCheckBox("Отметить как выполненное в срок");

//Шаг 5. Сменить фон доски на зеленый цвет
        webSteps.clickElementContainsText("Закрыть диалоговое окно");
        webSteps.clickButtonWithText("Меню");
        webSteps.clickElementContainsText("Сменить фон");
        webSteps.clickElementContainsText("Цвета");
        webSteps.clickElementContainsText("Закрыть меню");

//Шаг 6. Создать для карточки метку зеленого цвета: Высокий приоритет
        webSteps.clickElementContainsText("Карточка для изучения API");
        webSteps.clickElementContainsText("Метки");
        webSteps.clickButtonWithText("Создать новую метку");
        webSteps.inputTextInField("Название", "Высокий приоритет");
        webSteps.clickElementContainsText("Создание");
        Selenide.sleep(1000);
//Шаг 7. Выбрать тип команды "Образование"

//Шаг 8. Сменить имя доски "Только для образования"

    }
}
