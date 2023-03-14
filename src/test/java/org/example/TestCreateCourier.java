package org.example;

import io.restassured.response.Response;
import org.example.client.ScooterCourier;
import org.example.model.Courier;
import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;


public class TestCreateCourier {

    public Courier courier;
    public Response createCourier;
    public ScooterCourier scooterCourier = new ScooterCourier();

    @Test
    @Description("Курьера можно создать со всеми валидными полями")
    public void checkCreateCourierWithValidField() {
        courier = new Courier("Hulk", "1234", "Bruce");
        createCourier = scooterCourier.createCourier(courier);
        createCourier.then()
                .assertThat().statusCode(201)
                .and()
                .body("ok", equalTo(true));
    }

    @Test
    @Description("Попытка создать курьра, который уже существует")
    public void checkCreateCourierAlredyExists() {
        courier = new Courier("Hulk", "1234", "Bruce");
        createCourier = scooterCourier.createCourier(courier);
        createCourier = scooterCourier.createCourier(courier);
        createCourier.then().statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется"));
    }


    @Test
    @Description("Попытка создания курьера без пароля")
    public void checkCreateCourierWithoutPassword() {
        courier = new Courier("Hulk", "", "Bruce");
        createCourier = scooterCourier.createCourier(courier);
        createCourier.then().statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Description("Попытка создания курьера без логина")
    public void checkCreateCourierWithoutLogin() {
        courier = new Courier("", "1234", "Bruce");
        createCourier = scooterCourier.createCourier(courier);
        createCourier.then().statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void tearDown() {
        Response loginCourierResponse = scooterCourier.loginCourier(courier);
        int statusCode = loginCourierResponse.then().extract().statusCode();
        if (statusCode == 200) {
            String id = scooterCourier.extractId(loginCourierResponse);
            scooterCourier.deleteCourier(id);
        }
    }
}
