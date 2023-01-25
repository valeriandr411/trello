import io.qameta.allure.Description;
import org.json.JSONException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import steps.ApiSteps;

import java.util.List;
import java.util.Map;


public class RestTest {
    private static ApiSteps apiSteps = new ApiSteps();

//    @Test
//    @Description("1. Тестирование API")
    public void apiTest() throws JSONException {
//Шаг 1. Создать доску "KanbanTool"
         String idBoard = apiSteps.createBoard("Новая доска для теста");

//Шаг 2. Создать колонку "Backlog"
         String idListBacklog = apiSteps.createColumn("Backlog", idBoard);

//Шаг 3. Добавить карточку в колонку Backlog с названием "Карточка для изучения API"
        String idCard = apiSteps.createCard("Карточка для изучения API", idListBacklog);

//Шаг 4. Добавить вложение в виде любой фотографии
        String path = "src/test/resources/teddy_bears.png";
        apiSteps.createAttachment(idCard, path);

//Шаг 5. Поставить срок выполнения на следующий день
        apiSteps.changeDueDate(idCard, 1);

//Шаг 6. Добавить описание "Тут будет отмечаться прогресс обучения"
        apiSteps.addDescription(idCard,"Тут будет отмечаться прогресс обучения");

//Шаг 7. Создать чек-лист с пунктами:
        String idCheckList = apiSteps.createChecklist(idCard);
        String idCheckItem_1 = apiSteps.creteCheckItemOnChecklist(idCheckList, "1.Понять протокол HTTP");
        String idCheckItem_2 = apiSteps.creteCheckItemOnChecklist(idCheckList, "2.Выучить методы запросов");

//Шаг 8. Отметить пункт в карточке "Понять протокол HTTP"
        apiSteps.changeCheckItemState(idCard,  idCheckItem_1,"complete" );

//Шаг 9. Создать колонку "Done"
        String idListDane = apiSteps.createColumn("Done", idBoard);

//Шаг 10. Переместить карточку в колонку Done
         apiSteps.moveCardToList(idCard,idListDane);

//Шаг 11. Архивировать колонку "Backlog"
        apiSteps.updateArchiveList(idListBacklog,"true");

//Шаг 12. Отметить пункт в карточке "Выучить методы запросов"
        apiSteps.changeCheckItemState(idCard,  idCheckItem_2,"complete" );
        //

//Шаг 13. Поставить в карточке эмоджи Палец вверх
    }

  //  @Test
    public void test() throws JSONException {
       // apiSteps.deleteBoard("63c009e1012787032ad200ac");
       // apiSteps.getBoards();
        String idListDane = "63c180dfa5dd3f0220c9769a";
       // apiSteps.createCard("Новая карточка", idListDane);
       // apiSteps.deleteCard("63c6c7917d3652292bbbdc13");
   //     apiSteps.getCardsOnBoard("63c0f9a9bcc8320114e54021");
    }

  //  @AfterClass
    public void after() throws JSONException {
        List<Map<String, Object>> boards = apiSteps.getBoards();
        String idBoard;
        for (int i = 0; i < boards.size(); i++) {
            idBoard = boards.get(i).get("id").toString();
            apiSteps.deleteBoard(idBoard);
        }
    }

}
