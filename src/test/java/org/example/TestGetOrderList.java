package org.example;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.example.client.ScooterOrder;
import org.example.model.Order;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

public class TestGetOrderList {
    public ScooterOrder scooterOrder;
    public Order order;
    public Response createOrder;

    @Before
    public void setUp() {
        scooterOrder = new ScooterOrder();
        order = new Order("Владимир", "Чёрный", "Московское шоссе 123",
                1, "88005553535", 1, "11.03.2023",
                "1", new String[]{"BLACK"});
        createOrder = scooterOrder.createOrder(order);
    }

    @Test
    @Description("Проверяем, что массив не пустой.")
    public void checkOrderListNotNull() {
        scooterOrder.getOrderList()
                .then()
                .assertThat()
                .body("orders", hasSize(greaterThan(0)));
    }

    @After
    public void tearDown() {
        String track = Integer.toString(createOrder.then().extract().path("track"));
        scooterOrder.cancelOrder(track);
    }
}
