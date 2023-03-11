package org.example;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.example.client.ScooterOrder;
import org.example.model.Order;
import static org.hamcrest.Matchers.hasKey;

@RunWith(Parameterized.class)
public class TestCreateOrder {
    ScooterOrder scooterOrder;
    Response createOrder;
    String firstName;
    String lastName;
    String address;
    int metroStation;
    String phone;
    int rentTime;
    String deliveryDate;
    String comment;
    String[] color;

    public TestCreateOrder(String firstName, String lastName, String address,
                           int metroStation, String phone, int rentTime,
                           String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {"Иван", "Черный", "пр. Ленина 1", 1, "79177777777", 1, "10.03.2023", "1", new String[]{"BLACK"}},
                {"Сергей", "Серый", "Арбат 7", 2, "79179999999", 2, "09.03.2023", "12", new String[]{"Grey"}}
        };
    }

    @Before
    public void setUp() {
        scooterOrder = new ScooterOrder();
    }

    @Test
    @Description("Создать заказ и проверить, что тело ответа содержит 'track'")
    public void checkOrderHaveTrack() {
        Order order = new Order(firstName, lastName, address, metroStation,
                phone, rentTime, deliveryDate, comment, color);
        createOrder = scooterOrder.createOrder(order);
        createOrder.then().assertThat().body("$", hasKey("track"));
    }

    @After
    public void tearDown() {
        String track = Integer.toString(createOrder.then().extract().path("track"));
        scooterOrder.cancelOrder(track);
    }
}