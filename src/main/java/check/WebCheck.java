package check;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import support.Color;
import org.testng.Assert;

import static com.codeborne.selenide.Selenide.$x;

public class WebCheck {

    public void test(){}

    public void checkColorInCurrentCard(Color color){
        String clr =  color.getCode();
        String currentColor = "_3GFiyhGr6WTMLB";
        SelenideElement colorBlock= $x(".//h4[contains(text(),'Цвета')]/following-sibling::div[1]");
        String actual = colorBlock.$x(String.format(".//button[contains(@class,'%s')]",clr))
                .shouldBe(Condition.exist)
                .should(Condition.attributeMatching("class",currentColor))
                .getAttribute("class");

        Selenide.sleep(500);
    }

    public void checkTextInField(String field, String text){
        SelenideElement colorBlock= $x(".//h4[contains(text(),'Цвета')]/following-sibling::div[1]");

        String actual = colorBlock.$x(String.format(".//button[contains(@class,'%s')]",field))
                .shouldBe(Condition.exist)
                .should(Condition.text(""))
                .getAttribute("class");

        Selenide.sleep(500);
    }
}
