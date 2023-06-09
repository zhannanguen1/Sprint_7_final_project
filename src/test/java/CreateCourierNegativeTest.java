import courier.Courier;
import courier.CourierClient;
import courier.UniqueData;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateCourierNegativeTest {
    private CourierClient courierClient;
    private Courier courier;

    @Before
    @Step("Подготовка данных для тестирования")
    public void setUp() {
        courier = UniqueData.randomCourier();
        courierClient = new CourierClient();
    }

    @Test
    @Description("Проверка, нельзя создать двух одинаковых юзеров. Юзер с переданными ниже параметрами, уже существует")
    @Step("Создание курьера, с уже существующими в базе логином и паролем")
    public void canNotCreateSecondCourierWithSameParams() {
        Courier creatingACourier = new Courier(
                "test",
                "test",
                "test");
        ValidatableResponse response = courierClient.createCourier(creatingACourier);
        int actualCode = response.extract().statusCode();
        String message = response.extract().path("message");
        assertEquals(409, actualCode);
        assertEquals("Этот логин уже используется. Попробуйте другой.", message);
    }

    @Test
    @Description("Проверка невозможности создания курьера, без обязательного параметра login")
    @Step("Создание курьера без логина")
    public void canNotCreateCourierWithoutLogin() {
        Courier creatingACourier = new Courier(
                null,
                "test",
                "test");
        ValidatableResponse response = courierClient.createCourier(creatingACourier);
        int actualStatusCode = response.extract().statusCode();
        String massage = response.extract().path("message");
        assertEquals(400, actualStatusCode);
        assertEquals("Недостаточно данных для создания учетной записи", massage);
    }

    @Test
    @Description("Проверка невозможности создания курьера, без обязательного параметра Password")
    @Step("Создание курьера без пароля")
    public void canNotCreateCourierWithoutPassword() {
        Courier creatingACourier = new Courier(
                "test",
                null,
                "test");
        ValidatableResponse response = courierClient.createCourier(creatingACourier);
        int actualStatusCode = response.extract().statusCode();
        String massage = response.extract().path("message");
        assertEquals(400, actualStatusCode);
        assertEquals("Недостаточно данных для создания учетной записи", massage);
    }

    @Test
    @Description("Проверка невозможности создания курьера, с уже существующим логином")
    @Step("Создание курьера с уже существующим логином")
    public void canNotCreateCourierWithNonUniqueLogin() {
        Courier creatingACourier = new Courier(
                "test",
                "wwww",
                "wwww");
        ValidatableResponse response = courierClient.createCourier(creatingACourier);
        int actualStatusCode = response.extract().statusCode();
        String massage = response.extract().path("message");
        assertEquals(409, actualStatusCode);
        assertEquals("Этот логин уже используется. Попробуйте другой.", massage);
    }
}
