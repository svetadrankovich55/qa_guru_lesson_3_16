import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;


public class ReqresInTests {
    @BeforeEach
    void beforeEach() {
        RestAssured.filters(new AllureRestAssured());
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    @DisplayName("Получить список пользователей")
    void successListUserTest() {
        given()
                .when()
                .get("/users?page=2")
                .then()
                .statusCode(200)
                .log().body()
                .body("per_page", is(6));
    }

    @Test
    @DisplayName("Получить пользователя по id")
    void successSingleUserTest() {
        given()
                .when()
                .get("/users/8")
                .then()
                .statusCode(200)
                .log().body()
                .body("data.first_name", is("Lindsay"));
    }

    @Test
    @DisplayName("Проверить отсутствие пользователя")
    void singleUserNotFoundTest() {
        given()
                .when()
                .get("/users/26")
                .then()
                .statusCode(404)
                .log().body()
                .body("isEmpty()", is(true));
    }

    @Test
    @DisplayName("Создать пользователя")
    void successCreateUserTest() {
        String data = "{\n" +
                "    \"name\": \"Sveta\",\n" +
                "    \"job\": \"teacher\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .log().body()
                .body("job", is("teacher"));
    }

    @Test
    @DisplayName("Обновить пользователя")
    void successPutUserTest() {
        String data = "{\n" +
                "    \"name\": \"Sveta\",\n" +
                "    \"job\": \"coach\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .put("/api/users/6")
                .then()
                .statusCode(200)
                .log().body()
                .body("job", is("coach"));
    }

    @Test
    @DisplayName("Удалить пользователя")
    void successDeleteUserTest() {
        given()
                .when()
                .delete("/users/2")
                .then()
                .statusCode(204);
    }


}
