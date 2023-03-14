package org.example.client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.model.Courier;
import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

public class ScooterCourier {
    private final String COURIER_URI = "https://qa-scooter.praktikum-services.ru/api/v1/courier";


    private final RequestSpecification spec = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .setBaseUri(COURIER_URI)
            .build();

    @Step("Создать курьера")
    public Response createCourier(Courier courier) {
        return given().spec(spec)
                .body(courier)
                .post();
    }

    @Step("Залогиниться курьеру")
    public Response loginCourier(Courier courier) {
        return given().spec(spec)
                .body(courier)
                .post("/login");
    }

    @Step("Удалить курьера")
    public Response deleteCourier(String id) {
        return given().spec(spec)
                .delete(id);
    }

    @Step("Получить ID курьера")
    public String extractId(Response loginCourier) {
        return Integer.toString(loginCourier.then().extract().path("id"));
    }
}
