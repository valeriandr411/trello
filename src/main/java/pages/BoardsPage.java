package pages;

import jdk.jfr.Name;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Field;

@Deprecated
public class BoardsPage{
    @FindBy
    private final String addBoardButton = "//div[contains(@class,'board-tile')]";
    private final String boardTileField = "//input[contains(@data-testid,'create-board-title-input')]";
    private final String createButton = "//button[contains(text(),'Создать')]";

}
