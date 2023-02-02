package steps;

import com.codeborne.selenide.Selenide;
import database.JDBCPostgreSQL;
import io.restassured.common.mapper.TypeRef;
import org.json.JSONException;
import org.json.JSONObject;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import utils.PropertiesUtil;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
public class ApiSteps {

    private static final String KEY = JDBCPostgreSQL.getKey(PropertiesUtil.get("test.user"));
    private static final String TOKEN = JDBCPostgreSQL.getToken(PropertiesUtil.get("test.user"));

    //--------------------------------�����----------------------------------------//
    public String createBoard(String name) throws JSONException {
        JSONObject requestBody = new JSONObject()
                .put("name", name);
        Response response = given()
                .baseUri("https://api.trello.com/1")
                .basePath("/boards")
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when().post()
                .then()
                .statusCode(200)
                .extract().response();
        return response.jsonPath().getString("id");
    }
    public void deleteBoard(String idBoard){
        Response response = given()
                .baseUri("https://api.trello.com")
                .basePath("/1/boards/" + idBoard)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .contentType(ContentType.JSON)
                .when().delete()
                .then()
                .statusCode(200)
                .extract().response();
        response.prettyPrint();
    }

    public  List<Map<String, Object>> getBoards() {
        return given()
                .baseUri("https://api.trello.com/1/members/me")
                .basePath("/boards")
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .contentType(ContentType.JSON)
                .when().get()
                .then()
                .statusCode(200)
                .extract()
                .response()
                .as(new TypeRef<List<Map<String, Object>>>() {
                });
    }
    public  List<Map<String, Object>> getCardsOnBoard(String idBoard){
             return    given()
                        .baseUri("https://api.trello.com")
                        .basePath("/1/boards/"+idBoard+"/cards")
                        .queryParam("key", KEY)
                        .queryParam("token", TOKEN)
                        .contentType(ContentType.JSON)
                        .when().get()
                        .then()
                        .statusCode(200)
                        .extract()
                        .response()
                        .as(new TypeRef<>() {
                        });
    }

    //----------------------------------���-����-------------------------------------------//
    public String createChecklist(String idCard) throws JSONException {
        JSONObject requestBody = new JSONObject()
                .put("idCard", idCard);
        Response response = given()
                .baseUri("https://api.trello.com")
                .basePath(("/1/checklists"))
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())

                .when().post()
                .then()
                .statusCode(200)
                .extract().response();
        response.prettyPrint();
        return response.jsonPath().getString("id");
    }

    public String creteCheckItemOnChecklist(String idCheckList, String name) throws JSONException {
        JSONObject requestBody = new JSONObject()
                .put("name", name);
        Response response = given()
                .baseUri("https://api.trello.com")
                .basePath(String.format("/1/checklists/%s/checkItems", idCheckList))
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())

                .when().post()
                .then()
                .statusCode(200)
                .extract().response();
        response.prettyPrint();
        return response.jsonPath().getString("id");
    }

    /**
     * �����, ����������� ������� ��������� ���-����� � ��������
     *
     * @param idCard      id ��������
     * @param idCheckItem id ���-�����
     * @param state       ���������, � ������� ����� ��������� ���-����: complete, incomplete.
     * @throws JSONException
     */
    public void changeCheckItemState(String idCard, String idCheckItem, String state) throws JSONException {
        JSONObject requestBody = new JSONObject()
                .put("state", state);
        Response response = given()
                .baseUri("https://api.trello.com")
                .basePath(String.format("/1/cards/%1s/checkItem/%2s", idCard, idCheckItem))
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when().put()
                .then()
                .statusCode(200)
                .extract().response();
        response.prettyPrint();
    }

    //----------------------------------��������-------------------------------------------//

    /**
     * �����, ����������� ��������� ��������
     * @param name �������� ��������
     * @param idList id �������
     * @return id ��������� ��������
     * @throws JSONException
     */
    public String createCard(String name, String idList) throws JSONException {
        JSONObject requestBody = new JSONObject()
                .put("name", name)
                .put("idList", idList);

        Response response = given()
                .baseUri("https://api.trello.com/1")
                .basePath("/cards")
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())

                .when().post()
                .then()
                .statusCode(200)
                .extract().response();
        return response.jsonPath().getString("id");
    }

    /**
     * �����, ����������� ������� ��������
     * @param idCard id ��������
     */
    public void deleteCard(String idCard){
            Response response = given()
                    .baseUri("https://api.trello.com")
                    .basePath("/1/cards/" + idCard)
                    .queryParam("key", KEY)
                    .queryParam("token", TOKEN)
                    .contentType(ContentType.JSON)
                    .when().delete()
                    .then()
                    .statusCode(200)
                    .extract().response();
            response.prettyPrint();
    }

    /**
     * �����, ����������� �������� �������� � ��������
     *
     * @param idCard
     * @param description ��������, ������� ������� ��������
     * @throws JSONException
     */
    public void addDescription(String idCard, String description) throws JSONException {
        JSONObject requestBody = new JSONObject()
                .put("desc", description);
        Response response = given()
                .baseUri("https://api.trello.com")
                .basePath(String.format("/1/cards/%s", idCard))
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when().put()
                .then()
                .statusCode(200)
                .extract().response();
        response.prettyPrint();
    }

    /**
     * �����, ����������� ��������� �������� � ��������
     * @param idCard id ��������
     * @param pathName ���� � �����
     */
    public void createAttachment(String idCard, String pathName){
        File file = new File(pathName);
        Response response = given()
                .baseUri("https://api.trello.com")
                .basePath(String.format("/1/cards/%s/attachments", idCard))
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .contentType(ContentType.MULTIPART)
                .multiPart("file", file, "multipart/form-data")
                .when().post()
                .then()
                .statusCode(200)
                .extract().response();
        response.prettyPrint();
    }

    /**
     * �����, ����������� ���������� �������� � �������
     * @param idCard id ��������
     * @param idList id �������
     * @throws JSONException
     */
    public void moveCardToList(String idCard, String idList) throws JSONException {
        JSONObject requestBody = new JSONObject()
                .put("idList", idList);
        Response response = given()
                .baseUri("https://api.trello.com")
                .basePath(String.format("/1/cards/%s", idCard))
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when().put()
                .then()
                .statusCode(200)
                .extract().response();
        response.prettyPrint();
    }

    /**
     * �����, ����������� �������� ���� ���������� � ��������.
     * ������ ������� �� �������� ���.
     * @param idCard id ��������
     * @param numberDays ���������� ����, �� ������� ������� ���������
     *                   ��� ��������� (����� ���������� �� ������ �����) ���� ���������� ������
     * @throws JSONException
     */
    public void changeDueDate(String idCard,int numberDays) throws JSONException {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DAY_OF_MONTH, numberDays);
        DateFormat df = new SimpleDateFormat("yyyy MM dd");
        String newDate = df.format(instance.getTime());
        JSONObject requestBody = new JSONObject()
                .put("start", newDate);
        Response response = given()
                .baseUri("https://api.trello.com")
                .basePath(String.format("/1/cards/%s", idCard))
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when().put()
                .then()
                .statusCode(200)
                .extract().response();
        response.prettyPrint();
    }
//----------------------------------�������-------------------------------------------//

    /**
     * �����, ��������� ����� ������� ��� �������� �����
     *
     * @param name    ��� �������
     * @param idBoard id �����
     * @return id ��������� �������
     * @throws JSONException
     */
    public String createColumn(String name, String idBoard) throws JSONException {
        JSONObject requestBody = new JSONObject()
                .put("name", name)
                .put("idBoard", idBoard);
        Response response = given()
                .baseUri("https://api.trello.com/1")
                .basePath("/lists")
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when().post()
                .then()
                .statusCode(200)
                .extract().response();
        response.prettyPrint();

        Selenide.sleep(500);

        return response.jsonPath().getString("id");
    }


    /**
     * @param idList id ������
     * @param value  ������������ - true, ������� �� ������ - false
     * @throws JSONException
     */
    public void updateArchiveList(String idList, String value) throws JSONException {
        JSONObject requestBody = new JSONObject()
                .put("value", value);
        Response response = given()
                .baseUri("https://api.trello.com")
                .basePath(String.format("/1/lists/%s/closed", idList))
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when().put()
                .then()
                .statusCode(200)
                .extract().response();
        response.prettyPrint();
    }
}