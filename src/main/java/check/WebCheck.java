package check;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.apache.log4j.Logger;
import support.Color;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class WebCheck {
    private final int TIMEOUT = 20;
    private static final Logger LOGGER = Logger.getLogger(WebCheck.class);

    public void checkColorInCurrentCard(Color color) {
        String clr = color.getCode();
        String currentColor = "_3GFiyhGr6WTMLB";
        SelenideElement colorBlock = $x(".//h4[contains(text(),'Цвета')]/following-sibling::div[1]");
        String actual = colorBlock.$x(String.format(".//button[contains(@class,'%s')]", clr))
                .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT))
                .should(Condition.attributeMatching("class", currentColor))
                .getAttribute("class");
    }

    public void checkTextInField(String field, String text) {
        SelenideElement colorBlock = $x(".//h4[contains(text(),'Цвета')]/following-sibling::div[1]");
        String actual = colorBlock.$x(String.format(".//button[contains(@class,'%s')]", field))
                .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT))
                .should(Condition.text(""))
                .getAttribute("class");
    }

    /**
     * Метод, проверяющий наличие колонки по ее названию
     *
     * @param nameList название колонки
     * @return селектор колокнки
     */
    public SelenideElement checkListDisplayed(String nameList) {
        SelenideElement list = $x(".//div[@id='board']/div[contains(@class,'js-list')]" +
                String.format("/descendant::h2[text()='%s']", nameList));
        list.shouldBe(Condition.exist.because(String.format("Колонка '%s' не найдена", nameList)),
                Duration.ofSeconds(TIMEOUT));
        LOGGER.info(String.format("Колонка '%1$s' отображается", nameList));
        return list.$x("./parent::div");
    }

    /**
     * Метод, проверяющий наличие в колонке карточки
     *
     * @param list название колонки
     * @param card название карточки
     */
    public void checkCardInList(String list, String card) {
        checkListDisplayed(list)
                .$x(String.format("./following-sibling::div[contains(@class,'list-cards')]/descendant::span[text()='%s']", card))
                .shouldBe(Condition.exist
                                .because(String.format("В сколнке '%s' не найдена карточка '%s'", list, card)),
                        Duration.ofSeconds(TIMEOUT));
        LOGGER.info(String.format("В колонке '%1$s' содержится карточка '%2$s'", list, card));
    }

    /**
     * Метод, проверяющий состояние чекбокса
     *
     * @param item название чекбокса
     */
    public void checkIfCheckboxIsComplete(String item) {
        $x(String.format(".//*[contains(@class,'checklist-item') and contains(text(),'%s')]", item))
                .shouldBe(Condition.exist, Duration.ofSeconds(TIMEOUT))
                .$x("./ancestor::div[contains(@class,'checklist-item-state-complete')]")
                .shouldBe(Condition.exist
                                .because(String.format("Чекбокс '%s' деактивирован", item)),
                        Duration.ofSeconds(TIMEOUT));
        LOGGER.info(String.format("Чекбокс '%1$s' активирован", item));
    }
}
