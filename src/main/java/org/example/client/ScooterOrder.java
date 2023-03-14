package org.example.client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.model.Order;
import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

public class ScooterOrder {

    private final String ORDER_URI = "https://qa-scooter.praktikum-services.ru/api/v1/orders";
    private final RequestSpecification spec = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .setBaseUri(ORDER_URI)
            .build();

    @Step("Создать заказ")
    public Response createOrder(Order order) {
        return given().spec(spec)
                .body(order)
                .post();
    }

    @Step("Закрыть заказ")
    public Response cancelOrder(String track) {
        return given().spec(spec)
                .body("{\"track\": " + track + "}")
                .put("/cancel");
    }

    @Step("Получить список заказов")
    public Response getOrderList() {
        return given().spec(spec)
                .get();
    }
}
