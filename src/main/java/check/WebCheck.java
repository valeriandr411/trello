package check;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import support.Color;

import static com.codeborne.selenide.Selenide.$x;

public class WebCheck {

    public void test() {
    }

    public void checkColorInCurrentCard(Color color) {
        String clr = color.getCode();
        String currentColor = "_3GFiyhGr6WTMLB";
        SelenideElement colorBlock = $x(".//h4[contains(text(),'Цвета')]/following-sibling::div[1]");
        String actual = colorBlock.$x(String.format(".//button[contains(@class,'%s')]", clr))
                .shouldBe(Condition.exist)
                .should(Condition.attributeMatching("class", currentColor))
                .getAttribute("class");

        Selenide.sleep(500);
    }

    public void checkTextInField(String field, String text) {
        SelenideElement colorBlock = $x(".//h4[contains(text(),'Цвета')]/following-sibling::div[1]");

        String actual = colorBlock.$x(String.format(".//button[contains(@class,'%s')]", field))
                .shouldBe(Condition.exist)
                .should(Condition.text(""))
                .getAttribute("class");

        Selenide.sleep(500);
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
        list.shouldBe(Condition.exist.because(String.format("Колонка '%s' не найдена", nameList)));

        Selenide.sleep(500);

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
                        .because(String.format("В сколнке '%s' не найдена карточка '%s'", list, card)));
        Selenide.sleep(500);
    }
}
