package core;

import com.codeborne.selenide.SelenideElement;

/**
  * Класс, который хранит текущую страницу теста
*/
public class PageManager {
private static SelenideElement currentBlock;
    public PageManager() {
    }

    public static SelenideElement getCurrentBlock() throws Exception{
     if (currentBlock == null) {
         throw new Exception("Текущий блок не установлен");
        }
        return currentBlock;
    }
    public static void setCurrentBlock(SelenideElement element){
       currentBlock = element;
    }

}