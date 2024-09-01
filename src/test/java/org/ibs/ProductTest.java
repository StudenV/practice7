package org.ibs;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ProductTest {
    private String Cookie;

    @Test
    void launch() {
        getCookies();
        addExoticFruit();
        addUnExoticFruit();
        addUnExoticVegetable();
        addExoticVegetable();
        getProducts();
        deleteTestData();
        getDefaultProducts();
        clearMyCookie();
    }

    void addExoticFruit() {
        RestAssured.baseURI = "http://localhost:8080";

        Response response = given()
                .header("Accept", "*/*")
                .header("Content-Type", "application/json")
                .cookie("JSESSIONID", Cookie)
                .body("{\"name\":\"Манго\",\"type\":\"FRUIT\",\"exotic\":true}")
                .when()
                .post("/api/food");

        response.prettyPrint();
        response.then().statusCode(200);
    }

    void addUnExoticFruit() {
        RestAssured.baseURI = "http://localhost:8080";

        Response response = given()
                .header("Accept", "*/*")
                .header("Content-Type", "application/json")
                .cookie("JSESSIONID", Cookie)
                .body("{\"name\":\"Щавель\",\"type\":\"FRUIT\",\"exotic\":false}")
                .when()
                .post("/api/food");

        response.prettyPrint();
        response.then().statusCode(200);
    }

    void addUnExoticVegetable() {
        RestAssured.baseURI = "http://localhost:8080";

        Response response = given()
                .header("Accept", "*/*")
                .header("Content-Type", "application/json")
                .cookie("JSESSIONID", Cookie)
                .body("{\"name\":\"Баклажан\",\"type\":\"VEGETABLE\",\"exotic\":false}")
                .when()
                .post("/api/food");

        response.prettyPrint();
        response.then().statusCode(200);
    }

    void addExoticVegetable() {
        RestAssured.baseURI = "http://localhost:8080";

        Response response = given()
                .header("Accept", "*/*")
                .header("Content-Type", "application/json")
                .cookie("JSESSIONID", Cookie)
                .body("{\"name\":\"Редиска\",\"type\":\"VEGETABLE\",\"exotic\":true}")
                .when()
                .post("/api/food");

        response.prettyPrint();
        response.then().statusCode(200);
    }

    void getCookies() {
        Response response = given()
                .header("Accept", "*/*")
                .when()
                .get("http://localhost:8080/api/food");

        Cookie = response.getCookie("JSESSIONID");
        response.then().statusCode(200);
    }

    void getProducts() {
        System.out.println("\n*************************** Список с тестовыми данными **********************************************\n");
        given()
                .baseUri("http://localhost:8080")
                .cookie("JSESSIONID", Cookie)
                .when()
                .get("/api/food")
                .then()
                .log().body()
                .assertThat()
                .statusCode(200)
                .body(
                        "[0].name", equalTo("Апельсин"),
                        "[0].type", equalTo("FRUIT"),
                        "[0].exotic", equalTo(true),
                        "[1].name", equalTo("Капуста"),
                        "[1].type", equalTo("VEGETABLE"),
                        "[1].exotic", equalTo(false),
                        "[2].name", equalTo("Помидор"),
                        "[2].type", equalTo("VEGETABLE"),
                        "[2].exotic", equalTo(false),
                        "[3].name", equalTo("Яблоко"),
                        "[3].type", equalTo("FRUIT"),
                        "[3].exotic", equalTo(false),
                        "[4].name", equalTo("Манго"),
                        "[4].type", equalTo("FRUIT"),
                        "[4].exotic", equalTo(true),
                        "[5].name", equalTo("Щавель"),
                        "[5].type", equalTo("FRUIT"),
                        "[5].exotic", equalTo(false),
                        "[6].name", equalTo("Баклажан"),
                        "[6].type", equalTo("VEGETABLE"),
                        "[6].exotic", equalTo(false),
                        "[7].name", equalTo("Редиска"),
                        "[7].type", equalTo("VEGETABLE"),
                        "[7].exotic", equalTo(true));
    }

    void deleteTestData() {
        given()
                .baseUri("http://localhost:8080")
                .header("Accept", "*/*")
                .cookie("JSESSIONID", Cookie)
                .when()
                .post("/api/data/reset")
                .then()
                .assertThat()
                .statusCode(200);
    }

    void getDefaultProducts() {
        System.out.println("\n*************************** Список с дефолтными данными **********************************************\n");
        given()
                .baseUri("http://localhost:8080")
                .cookie("JSESSIONID", Cookie)
                .when()
                .get("/api/food")
                .then()
                .log().body()
                .assertThat()
                .statusCode(200)
                .body(
                        "[0].name", equalTo("Апельсин"),
                        "[0].type", equalTo("FRUIT"),
                        "[0].exotic", equalTo(true),
                        "[1].name", equalTo("Капуста"),
                        "[1].type", equalTo("VEGETABLE"),
                        "[1].exotic", equalTo(false),
                        "[2].name", equalTo("Помидор"),
                        "[2].type", equalTo("VEGETABLE"),
                        "[2].exotic", equalTo(false),
                        "[3].name", equalTo("Яблоко"),
                        "[3].type", equalTo("FRUIT"),
                        "[3].exotic", equalTo(false));
    }

    void clearMyCookie() {
        Cookie = null;
    }
}
