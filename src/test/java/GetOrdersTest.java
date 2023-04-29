import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import order.OrderUser;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GetOrdersTest {
    private OrderUser orderUser;

    @Before
    @Step("Подготовка данных для получения списка заказов")
    public void setUp() {
        orderUser = new OrderUser();
    }

    @Test
    @Step("Проверка списка заказов")
    public void getListOfOrders() {
        ValidatableResponse responseCreate = orderUser.getOrders();
        int actualStatusCode = responseCreate.extract().statusCode();
        List<HashMap> orderBody = responseCreate.extract().path("orders");
        assertEquals(200, actualStatusCode);
        assertNotNull(orderBody);
    }
}
