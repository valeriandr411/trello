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
        webSteps.inputTextInField("������� ����� ����������� �����", login);
        webSteps.clickButtonWithText("����������");
        webSteps.inputTextInField("������� ������", JDBCPostgreSQL.getPassword(login));
        webSteps.clickElementWithText("�����");
    }

    @Test
    @Description(value = "������������ UI")
    public void uiTest() {
        webSteps.openUrl("https://trello.com/");
        login();
        webSteps.clickElementContainsText("�������");

//��� 1. ��������������, ��� �������� ��������� � ������� Done
        //������������ ������� Done
        //������� Done �������� ��������

//��� 2. ��������������, ��� ������ ���-������ ���������
        //������� ��������
        //���������, ��� �������� �������� �������
        //������ ������� � ������ �������� : state-complete

//��� 3. ��������� ������� � ������� ������
        webSteps.clickElementContainsText("�������� ��� �������� API");
        // webSteps.clickElementWithText("�������");
        // webSteps.setColorForCurrentCard(Color.BLUE);

//��� 4. ��������, ��� ������ ��������� � ����
//        webSteps.clickElementWithText("����");
//        webSteps.activatedCheckBox("����");
//        webSteps.clickElementWithText("���������");
//        webSteps.activatedCheckBox("�������� ��� ����������� � ����");

//��� 5. ������� ��� ����� �� ������� ����
        webSteps.clickElementContainsText("������� ���������� ����");
        webSteps.clickButtonWithText("����");
        webSteps.clickElementContainsText("������� ���");
        webSteps.clickElementContainsText("�����");
        webSteps.clickElementContainsText("������� ����");

//��� 6. ������� ��� �������� ����� �������� �����: ������� ���������
        webSteps.clickElementContainsText("�������� ��� �������� API");
        webSteps.clickElementContainsText("�����");
        webSteps.clickButtonWithText("������� ����� �����");
        webSteps.inputTextInField("��������", "������� ���������");
        webSteps.clickElementContainsText("��������");
        Selenide.sleep(1000);
//��� 7. ������� ��� ������� "�����������"

//��� 8. ������� ��� ����� "������ ��� �����������"

    }
}
