package check;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import support.Color;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class ApiCheck {
    public static void main(String[] args) {
        try {
            err();
        }catch (OutOfMemoryError error){
            error.printStackTrace();
        }
    }

    public static void err() throws OutOfMemoryError{
       throw  new OutOfMemoryError("—генерированна€ ошибка");
    }
}
