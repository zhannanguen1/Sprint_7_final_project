import courier.Courier;
import courier.CourierClient;
import courier.CourierCredentials;
import courier.UniqueData;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CourierTest {
    private CourierClient courierClient;
    private Courier courier;
    private ValidatableResponse responseLogin;
    @Before
    @Step("Подготовка данных для создания курьера")
    public void setUp(){
        courier = UniqueData.randomCourier();
        courierClient = new CourierClient();
    }

    @Test
    @Description("Проверка создания курьера")
    @Step("Создание курьера")
    public void canCreateCourierTest(){
        ValidatableResponse response = courierClient.createCourier(courier);
        int actualStatusCode = response.extract().statusCode();
        boolean massage = response.extract().path("ok");
        assertEquals(201, actualStatusCode);
        assertTrue(massage);
        responseLogin = courierClient.loginCourier(CourierCredentials.from(courier));
        int messageLogin = responseLogin.extract().path("id");
        assertNotNull(messageLogin);

    }

    @After
    @Step("Удаление созданного курьера")
    public void cleanUp() {
        int id = responseLogin.extract().path("id");
        courierClient.deleteCourier(id);
    }

}
