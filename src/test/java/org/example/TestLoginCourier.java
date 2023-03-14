package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import org.example.client.ScooterCourier;
import org.example.model.Courier;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class TestLoginCourier {
    public Courier courier;
    public Response loginCourier;
    public ScooterCourier scooterCourier = new ScooterCourier();

    @Test
    @Description("Курьер может залогиниться с валидными полями")
    public void checkLoginCourierWithValidField() {
        courier = new Courier("Hulk", "1234", "Bruce");
        scooterCourier.createCourier(courier);
        loginCourier = scooterCourier.loginCourier(courier);
        loginCourier.then().assertThat().statusCode(200);
        loginCourier.then().assertThat().body("id", notNullValue());
    }

    @Test
    @Description("Попытка залогиниться без пароля")
    public void checkLoginCourierWithoutPassword() {
        courier = new Courier("Hulk", "", "Bruce");
        scooterCourier.createCourier(courier);
        loginCourier = scooterCourier.loginCourier(courier);
        loginCourier.then().assertThat().statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @Description("Попытка залогиниться без логина")
    public void checkLoginCourierWithoutLogin() {
        courier = new Courier("", "1234", "Bruce");
        scooterCourier.createCourier(courier);
        loginCourier = scooterCourier.loginCourier(courier);
        loginCourier.then().statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @Description("Попытка залогиниться с данными несуществующего курьера")
    public void checkLoginNonExistingCourier() {
        courier = new Courier("Red Hulk", "1234", "Bruce");
        loginCourier = scooterCourier.loginCourier(courier);
        loginCourier.then().statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {
        int statusCode = loginCourier.then().extract().statusCode();
        if (statusCode == 200) {
            String id = scooterCourier.extractId(loginCourier);
            scooterCourier.deleteCourier(id);
        }
    }
}
