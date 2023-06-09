import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import order.GeneratorOfOrder;
import order.OrderCreateResponse;
import order.OrderScooter;
import order.OrderUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTest {
    private final List<String> color;
    private OrderScooter orderScooter;
    private OrderUser orderUser;
    private int actualTrackNumber;

    public OrderTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Цвет самоката. Тестовые данные: {0}")
    public static Object[][] getColor() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {List.of()}
        };
    }

    @Before
    @Step("Подготовка данных для создания заказа")
    public void setUp() {
        orderUser = new OrderUser();
        orderScooter = GeneratorOfOrder.getNewOrder(color);
    }

    @Test
    @Step("Создаем новый заказ")
    public void orderCanBeCreated() {
        ValidatableResponse responseCreate = orderUser.createOrder(orderScooter);
        int actualStatusCodeCreate = responseCreate.extract().statusCode();
        actualTrackNumber = responseCreate.extract().path("track");
        assertEquals(201, actualStatusCodeCreate);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        OrderCreateResponse orderCreateResponse = responseCreate.extract().body().as(OrderCreateResponse.class);
        String responseBody = gson.toJson(orderCreateResponse);
        assertTrue(responseBody.contains("track"));
    }

    @After
    @Step("Удаляем созданный заказ")
    public void cleanUp() {
        orderUser.cancelOrder(actualTrackNumber);
    }
}
