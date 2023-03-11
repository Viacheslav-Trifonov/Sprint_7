package org.example.client;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.model.Courier;
import io.qameta.allure.Step;
import static io.restassured.RestAssured.given;

public class ScooterCourier {
    private final RequestSpecification spec = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .setBaseUri("https://qa-scooter.praktikum-services.ru")
            .build();
    @Step("Создать курьера")
    public Response createCourier(Courier courier) {
        return given().spec(spec)
                .body(courier)
                .post("/api/v1/courier");
    }
    @Step("Залогиниться курьеру")
    public Response loginCourier(Courier courier) {
        return given().spec(spec)
                .body(courier)
                .post("/api/v1/courier/login");
    }
    @Step("Удалить курьера")
    public Response deleteCourier(String id) {
        return given().spec(spec)
                .delete("/api/v1/courier/" + id);
    }
    @Step("Получить ID курьера")
    public String extractId(Response loginCourier) {
        return Integer.toString(loginCourier.then().extract().path("id"));
    }
}
