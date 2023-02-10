import check.WebCheck;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
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
        webCheck.checkCardInList("Done", "Карточка для изучения API");

//Шаг 2. Удостовериться, все пункты чек-боксов выполнены
        //������� ��������
        //���������, ��� �������� �������� �������
        //������ ������� � ������ �������� : state-complete

//Шаг 3. Поставить обложку с зеленым цветом
        webSteps.clickElementContainsText("Карточка для изучения API");
        webSteps.clickElementWithText("Обложка");
        webSteps.setColorForCurrentCard("Цвета", Color.BLUE);

//Шаг 4. Отметить, что задача выполнена в срок
//        webSteps.clickElementWithText("Даты");
//        webSteps.activatedCheckBox("Срок");
//        webSteps.clickElementWithText("Сохранить);
//        webSteps.activatedCheckBox("Срок карточки истекает не скоро");

//Шаг 5. •	Сменить фон доски на зеленый цвет
//        webSteps.clickElementContainsText("������� ���������� ����");
//        webSteps.clickButtonWithText("меню");
//        webSteps.clickElementContainsText("������� ���");
//        webSteps.clickElementContainsText("�����");
//        webSteps.clickElementContainsText("������� ����");

//Шаг 6. Добавить метку зеленого цвета с текстом: Средний приоритет
        webSteps.clickElementContainsText("Карточка для изучения API");
        webSteps.clickElementContainsText("Метки");
        webSteps.clickButtonWithText("Создать новую метку");
        webSteps.inputTextInField("Название", "Средний приоритет");
        webSteps.clickElementContainsText("Создание");
        Selenide.sleep(1000);

//Шаг 7.

//Шаг 8. ������� ��� ����� "������ ��� �����������"

    }

    public void after() {
        //удалить доску
    }
}
