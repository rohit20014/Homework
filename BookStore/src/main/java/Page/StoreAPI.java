package Page;

import Util.ReadPropFile;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.List;

import static io.restassured.RestAssured.given;

public class StoreAPI {
    ReadPropFile prop = new ReadPropFile();

    public Response response;

    public String postUser() {
        String username = prop.getPropertyValue("username");
        String validRequest = "{\n" +
                "  \"userName\": \"" + username + "\",\n" +
                "  \"password\": \"Test@123\" \n}";
        Response response =
                given()
                        .when()
                        .header("Accept", ContentType.JSON.getAcceptHeader())
                        .contentType(ContentType.JSON)
                        .body(validRequest)
                        .post("https://demoqa.com/Account/v1/User")
                        .then()
                        .statusCode(201)
                        .extract()
                        .response();

        //return UserID
        String ss = response.asString();
        JSONObject jObject = new JSONObject(ss);
        String userId = jObject.getString("userID");
        return userId;
    }

    public void deleteUsersAllBooks() {
        String userid = prop.getPropertyValue("userId");
        System.out.println(userid);
        Response response =
                given()
                        .auth()
                        .preemptive()
                        .basic("rohit100","Test@123")
                        .queryParam("UserId",userid)
                        .delete("https://demoqa.com/BookStore/v1/Books")
                        .then()
                        .extract()
                        .response();
        System.out.println(response.asString());
    }

    public void deleteUser() {
        String userid = prop.getPropertyValue("userId");
        Response response =
                given()
                .auth()
                .preemptive()
                .basic("rohit100","Test@123")
                .pathParam("UUID",userid)
                .delete("https://demoqa.com/Account/v1/User/{UUID}")
                .then()
                .extract().response();
    }

    public StoreAPI() {
        response =
                 given()
                .when()
                .get("https://demoqa.com/BookStore/v1/Books")
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    public int getBooksList() {
        List<String> jsonResponse = response.jsonPath().getList("books");
        int size =jsonResponse.size();
        return size;
    }

    public void getThreeBookName() {
        prop.setPropertyValue("bookTitle",response.jsonPath().getString("books[0].title"));
        prop.setPropertyValue("bookTitle2",response.jsonPath().getString("books[2].title"));
        prop.setPropertyValue("bookTitle3",response.jsonPath().getString("books[3].title"));
    }

    public String getBookDetails(String title, String attribute) {
        String value = null;
        int size = getBooksList();
        for (int i = 0; i < size; i++) {
            String bookTitle = response.jsonPath().getString("books[" + i + "].title");
            if (bookTitle.equalsIgnoreCase(title)) {
                value = response.jsonPath().getString("books[" + i + "]." + attribute);
            }
        }
        return value;
    }
}
