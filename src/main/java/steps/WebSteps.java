package steps;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.remote.DesiredCapabilities;
import support.Color;
import utils.PropertiesUtil;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class WebSteps {
    private final int TIMEOUT = 60;
    private SelenideElement currentBlock;
    private final String popOver = ".//div[contains(@class,'pop-over is-shown')]";

    public void setUp() {
        Configuration.baseUrl = "https://trello.com/";
        Configuration.browser = "chrome";
        //Размер окна браузера
        Configuration.browserSize = "1920x1080";
        Configuration.browserVersion = "108.0.5359.22";
        //Создаём объект класса DesiredCapabilities, используется как настройка  вашей конфигурации с помощью пары ключ-значение
        DesiredCapabilities capabilities = new DesiredCapabilities();
        //Включить поддержку отображения экрана браузера во время выполнения теста
        capabilities.setCapability("enableVNC", true);
        //Переопределяем Browser capabilities
        Configuration.browserCapabilities = capabilities;
    }
    /**
     * Открытие сайта
     */
    public void openUrl(String url) {
        Selenide.open(url);
        Selenide.sleep(500);
    }
    @Step("Нажать на элемент, содержащий текст '{text}' ")
    public void clickElementContainsText(String text) {
        $x(String.format(".//*[contains(text(),'%1$s')]"+
                "| .//*[contains(@aria-label,'%1$s')]"+
                "| .//a[contains(@title,'%1$s')]", text)).click();
        Selenide.sleep(500);
    }
    @Step("Нажать на элемент c текстом '{text}' ")
    public void clickElementWithText(String text) {
        $x(String.format(".//span[text()='%1$s']", text))
                .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT))
                .click();
        Selenide.sleep(500);
    }
    @Step("Нажать на кнопку с текстом '{text}' ")
    public void clickButtonWithText(String text) {
        $x(String.format(".//button[@aria-label='%1$s']"+
                "| .//button[contains(text(),'%1$s')]"+
                "| .//input[contains(@value,'%1$s')]"+
                "| .//div[contains(@class,'Buttonsstyles')]/a[text()='%1$s']", text))
                .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT))
                .click();
        Selenide.sleep(500);
    }

    @Step("Ввести в поле '{field}' значение '{text}' ")
    public void inputTextInField(String field, String text) {
        $x(String.format(".//input[contains(@placeholder,'%1$s')]" +
                "| .//div[contains(text(),'%1$s')]/following-sibling::input" +
                "| .//*[contains(text(),'%1$s')]/following::div/input", field))
                .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT))
                .setValue(text);
        Selenide.sleep(500);
    }

    //*[contains(text(),'%s')]/ancestor::div[contains(@class,'list-wrapper')]
    public void setCurrentBlock(String currentBlock) {
        this.currentBlock = $x(String.format("//*[contains(text(),'%1$s')]/ancestor::div[contains(@class,'list-wrapper')]" +
                "| .//*[contains(text(),'%1$s')]/ancestor::section", currentBlock));
    }

    /**
     * в текущем блоке нажать на элемент с текстом
     */
    public void inCurrentBlockClickElementWithText(String text) {
        currentBlock.$x(String.format("//*[contains(text(),'%1$s')]" +
                "| //input[contains(@value,'%1$s')]",text)).click();
    }

    public void setColorForCurrentCard(Color color){
        String clr =  color.getCode();

        SelenideElement colorBlock= $x(".//h4[contains(text(),'Цвета')]/following-sibling::div[1]")
                .shouldBe(Condition.exist);
        colorBlock.$x(String.format(".//button[contains(@class,'%s')]",clr))
                .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT))
                .click();
        //закрываем pop over
        closeBlock();
        Selenide.sleep(500);
    }

    public void activatedCheckBox(String name) {
        $x(String.format(".//*[contains(text(),'%1$s')]/following-sibling::label" +
                "| .//*[contains(@aria-label,'%1$s')]", name))
                .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT))
                        .click();
        Selenide.sleep(500);
    }
    @Step("Закрыть блок")
    public void closeBlock(){
        $x(popOver).$x(".//*[contains(@class,'icon-close')]")
                .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT))
                .click();
    }
}
