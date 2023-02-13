package steps;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import core.PageManager;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.Color;
import utils.PropertiesUtil;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.actions;

public class WebSteps {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSteps.class);
    private final int TIMEOUT = 20;

    @Step("открыть url '{string}'")
    public void openUrl(String url) {
        Selenide.open(url);
        Selenide.sleep(500);
        LOGGER.info("открыта ссылка'{}'", url);
    }

    @Step("Нажать на элемент, содержащий текст '{text}' ")
    public void clickElementContainsText(String text) {
        $x(String.format(".//*[contains(text(),'%1$s')]" +
                "| .//*[contains(@aria-label,'%1$s')]" +
                "| .//a[contains(@title,'%1$s')]", text))
                .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT))
                .click();
        Selenide.sleep(500);
        LOGGER.info("нажатие на элемент, содержащий текст '{}'", text);
    }

    @Step("Нажать на элемент c текстом '{text}' ")
    public void clickElementWithText(String text) {
        $x(String.format(".//span[text()='%1$s']", text))
                .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT))
                .click();
        Selenide.sleep(500);
        LOGGER.info("нажатие на элемент c текстом '{}'", text);
    }

    public SelenideElement clickHeader(String name) {
        SelenideElement header =
                $x(String.format(".//h1[contains(text(),'%s')]", name))
                        .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT));
        header.click();
        Selenide.sleep(500);
        LOGGER.info("клик по заголовку '{}'", name);
        return header;

    }

    //---------------------------------------------[  BUTTONS  ]--------------------------------------------------//
    @Step("Нажать на кнопку с текстом '{text}' ")
    public void clickButtonWithText(String text) {
        $x(String.format(".//button[text()='%1$s']", text))
                .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT))
                .click();
        Selenide.sleep(500);
        LOGGER.info("нажатие на кнопку c текстом '{}'", text);
    }

    @Step("Нажать на кнопку, содержащую текст '{text}' ")
    public void clickButtonContainsText(String text) {
        $x(String.format(".//button[@aria-label='%1$s']" +
                "| .//button[contains(text(),'%1$s')]" +
                "| .//input[contains(@value,'%1$s')]" +
                "| .//div[contains(@class,'Buttonsstyles')]/a[text()='%1$s']", text))
                .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT))
                .click();
        Selenide.sleep(500);
        LOGGER.info("нажатие на кнопку, содержащую текст '{}'", text);
    }

    public void activatedCheckBox(String name) {
        $x(String.format(".//*[contains(text(),'%1$s')]/following-sibling::label" +
                "| .//*[contains(@aria-label,'%1$s')]", name))
                .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT))
                .click();
        Selenide.sleep(500);
        LOGGER.info("Чекбокс с текстом '{}' активирован", name);
    }

    //---------------------------------------------[  INPUT FIELDS ]--------------------------------------------------//
    @Step("Ввести в текстовый редактор значение '{text}' ")
    public void inputTextInTextBox(String text) {
        $x(".//div[@role='textbox']")
                .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT))
                .setValue(text);
        Selenide.sleep(500);
        LOGGER.info("в текстовый редактр введено значение'{}'", text);
    }

    @Step("Ввести в поле '{field}' значение '{text}'")
    public void inputTextInField(String field, String text) {
        $x(String.format(".//input[contains(@placeholder,'%1$s')]" +
                "| .//div[contains(text(),'%1$s')]/following-sibling::input" +
                "| .//*[contains(text(),'%1$s')]/following::div/input", field))
                .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT))
                .setValue(text);
        Selenide.sleep(500);
        LOGGER.info("в поле '{}' введено значение '{}'", field, text);
    }
    //---------------------------------------------------------------------------------------------------------------//

    public void setColorForCurrentCard(String block, Color color) {
        String clr = color.getCode();

        SelenideElement colorBlock = $x(String.format(".//h4[contains(text(),'%s')]/following-sibling::div[1]", block))
                .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT));
        colorBlock.$x(String.format(".//button[contains(@class,'%s')]", clr))
                .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT))
                .click();
        closeBlock();
        Selenide.sleep(500);
    }

    //-------------------------------------------------[ BLOCKS ]-------------------------- -------------------------//
    public SelenideElement setCurrBlock(SelenideElement element) throws Exception {
        PageManager.setCurrentBlock(element);
        LOGGER.info("установить текущий блок");
        return PageManager.getCurrentBlock();
    }

    public void inputTextInCurrBlock(String text) throws Exception {
        SelenideElement block = PageManager.getCurrentBlock();
        block.$x("./following-sibling::input").shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT)).
                setValue(text);
        Selenide.sleep(TIMEOUT);
        LOGGER.info("в текущий блок введен текст'{}'", text);
    }

    /**
     * в текущем блоке нажать на элемент с текстом
     */
    public void inCurrentBlockClickElementWithText(String text) throws Exception {
        PageManager.getCurrentBlock().$x(String.format("//*[contains(text(),'%1$s')]" +
                        "| //input[contains(@value,'%1$s')]", text))
                .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT))
                .click();
        Selenide.sleep(TIMEOUT);
        LOGGER.info("в текущий блок нажат элемент с текстом'{}'", text);
    }

    @Step("Закрыть блок")
    public void closeBlock() {
        final String popOver = ".//div[contains(@class,'pop-over is-shown')]";
        $x(popOver).$x(".//*[contains(@class,'icon-close')]")
                .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT))
                .click();
        LOGGER.info("блок закрыт");
    }

    //--------------------------------------------------[  KEY  ]-----------------------------------------------------//
    @Step("нажать клавишу {string}}")
    public void pressKey(String key) {
        actions().keyDown(Keys.valueOf(key)).perform();
        LOGGER.info("нажата кнопка '{}'", key);
    }

    @Step("отправить сочетание клавиш ctrl + {string}")
    public void sendHotKeyWithCtrl(String key) {
        actions().keyDown(Keys.valueOf("CONTROL")).sendKeys(key)
                .keyUp(Keys.valueOf("CONTROL")).perform();
    }

    //----------------------------------------------------[  PHOTOS  ]--------------------------------------------//
    @Step("выбрать фон '{string}'")
    public void selectBackground(String name) {
        $x(String.format(".//span[contains(@class,'background-box') and contains(@style,'%s')]", name))
                .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT))
                .click();
        Selenide.sleep(500);
        LOGGER.info("клик по фото с текстом '{}'", name);
    }
}