package steps;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import core.PageManager;
import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import support.Color;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.actions;

public class WebSteps {
    private static final Logger LOGGER = Logger.getLogger(WebSteps.class);
    private final int TIMEOUT = 20;

    @Step("открыть url '{string}'")
    public void openUrl(String url) {
        Selenide.open(url);
        LOGGER.info(String.format("открыта ссылка'%s'", url));
    }

    @Step("Нажать на элемент, содержащий текст '{text}' ")
    public void clickElementContainsText(String text) {
        $x(String.format(".//*[contains(text(),'%1$s')]" +
                "| .//*[contains(@aria-label,'%1$s')]" +
                "| .//a[contains(@title,'%1$s')]", text))
                .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT))
                .click();
        LOGGER.info(String.format("нажатие на элемент, содержащий текст '%s'", text));
    }

    @Step("Нажать на элемент c текстом '{text}' ")
    public void clickElementWithText(String text) {
        $x(String.format(".//span[text()='%1$s']", text))
                .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT))
                .click();
        LOGGER.info(String.format("нажатие на элемент c текстом '%s'", text));
    }

    public SelenideElement clickHeader(String name) {
        SelenideElement header =
                $x(String.format(".//h1[contains(text(),'%1$s')]" +
                        " | .//h2[contains(text(),'%1$s')]", name))
                        .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT));
        header.click();
        LOGGER.info(String.format("клик по заголовку '%s'", name));
        return header;
    }

    //---------------------------------------------[  BUTTONS  ]--------------------------------------------------//
    @Step("Нажать на кнопку с текстом '{text}' ")
    public void clickButtonWithText(String text) {
        $x(String.format(".//button[text()='%1$s']", text))
                .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT))
                .click();
        LOGGER.info(String.format("нажатие на кнопку c текстом '%s'", text));
    }

    @Step("Нажать на кнопку, содержащую текст '{text}' ")
    public void clickButtonContainsText(String text) {
        $x(String.format(".//button[@aria-label='%1$s']" +
                "| .//button[contains(text(),'%1$s')]" +
                "| .//input[contains(@value,'%1$s')]" +
                "| .//a[contains(text(),'%1$s')]" +
                "| .//div[contains(@class,'Buttonsstyles')]/a[text()='%1$s']", text))
                .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT))
                .click();
        LOGGER.info(String.format("нажатие на кнопку, содержащую текст '%s'", text));
    }

    public void activatedCheckBox(String name) {
        $x(String.format(".//*[contains(text(),'%1$s')]/following-sibling::label" +
                "| .//*[contains(@aria-label,'%1$s')]", name))
                .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT))
                .click();
        LOGGER.info(String.format("Чекбокс с текстом '%s' активирован", name));
    }

    //---------------------------------------------[  INPUT FIELDS ]--------------------------------------------------//
    @Step("Ввести в текстовый редактор значение '{text}' ")
    public void inputTextInTextBox(String text) {
        $x(".//div[@role='textbox']")
                .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT))
                .setValue(text);
        LOGGER.info(String.format("в текстовый редактр введено значение'%s'", text));
    }

    @Step("Ввести в поле '{field}' значение '{text}'")
    public void inputTextInField(String field, String text) {
        $x(String.format(".//input[contains(@placeholder,'%1$s')]" +
                "| .//div[contains(text(),'%1$s')]/following-sibling::input" +
                "| .//*[contains(text(),'%1$s')]/following::div/input", field))
                .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT))
                .setValue(text);
        LOGGER.info(String.format("в поле '%1$s' введено значение '%2$s'", field, text));
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
        LOGGER.info(String.format("для блока '%1$s' установлен цвет '%2$s'", block, clr));
    }

    //-------------------------------------------------[ BLOCKS ]-------------------------- -------------------------//
    public void setCurrBlock(String block) {
        SelenideElement element = $x(String.format(".//span[contains(text(),'%1$s')]" +
                "/ancestor::div[@class='pop-over is-shown']", block));
        PageManager.setCurrentBlock(element);
        LOGGER.info(String.format("установить блок '%1$s' текущим", block));
    }

    public void setCurrBlock(SelenideElement element) {
        PageManager.setCurrentBlock(element);
        LOGGER.info("установить текущий блок");
    }

    public void inputTextInCurrBlock(String text) throws Exception {
        SelenideElement block = PageManager.getCurrentBlock();
        block.$x("./following-sibling::input").shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT)).
                setValue(text);
        LOGGER.info(String.format("в текущий блок введен текст '%1$s'", text));
    }

    /**
     * в текущем блоке нажать на элемент с текстом
     */
    public void inCurrentBlockClickElementWithText(String text) throws Exception {
        PageManager.getCurrentBlock().$x(String.format("./descendant::input[contains(@value,'%1$s')]", text))
                .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT))
                .click();
        LOGGER.info(String.format("в текущий блок нажат элемент с текстом'%1$s'", text));
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
        LOGGER.info(String.format("нажата кнопка '%1$s'", key));
    }

    @Step("отправить сочетание клавиш ctrl + {string}")
    public void sendHotKeyWithCtrl(String key) {
        actions().keyDown(Keys.valueOf("CONTROL")).sendKeys(key)
                .keyUp(Keys.valueOf("CONTROL")).perform();
        LOGGER.info(String.format("нажать сочетание клавиш  ctrl + %1$s", key));
    }

    //----------------------------------------------------[  PHOTOS  ]--------------------------------------------//
    @Step("выбрать фон '{string}'")
    public void selectBackground(String name) {
        $x(String.format(".//span[contains(@class,'background-box') and contains(@style,'%s')]", name))
                .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT))
                .click();
        Selenide.sleep(500);
        LOGGER.info(String.format("установить фон '%1$s'", name));
    }
}