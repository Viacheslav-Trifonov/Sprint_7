package org.example.client;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.model.Order;
import io.qameta.allure.Step;
import static io.restassured.RestAssured.given;

public class ScooterOrder {
    private final RequestSpecification spec = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .setBaseUri("https://qa-scooter.praktikum-services.ru")
            .build();
    @Step("Создать заказ")
    public Response createOrder(Order order){
        return given().spec(spec)
                .body(order)
                .post("/api/v1/orders");
    }
    @Step("Закрыть заказ")
    public Response cancelOrder(String track){
        return given().spec(spec)
                .body("{\"track\": " + track + "}")
                .put("/api/v1/orders/cancel");
    }
    @Step("Получить список заказов")
    public Response getOrderList(){
        return given().spec(spec)
                .get("/api/v1/orders");
    }
}
