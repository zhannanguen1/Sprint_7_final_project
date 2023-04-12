import courier.CourierClient;
import courier.CourierCredentials;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LoginCourierTest {
    private CourierClient courierClient;

    @Before
    @Step("Подготовка данных для входа курьера в систему")
    public void setUp(){
        courierClient = new CourierClient();
    }

    @Test
    @Description("Проверка, что курьер может войти в систему")
    @Step("Логин курьера")
    public void CourierCanLogIn(){
        CourierCredentials courierCredentials = new CourierCredentials("nnnggg1410", "1410");
        ValidatableResponse response = courierClient.loginCourier(courierCredentials);
        int actualStatusCode = response.extract().statusCode();
        int id = response.extract().path("id");
        assertEquals(200, actualStatusCode);
        assertNotNull(id);
    }
    @Test
    @Description("Проверка, что курьер не может войти в систему, если не передать его login")
    @Step("Вход курьера без логина")
    public void CourierCanNotLogInWithoutLoginParam(){
        CourierCredentials courierCredentials = new CourierCredentials(null, "1410");
        ValidatableResponse response = courierClient.loginCourier(courierCredentials);
        int actualStatusCode = response.extract().statusCode();
        String message = response.extract().path("message");
        assertEquals(400, actualStatusCode);
        assertEquals("Недостаточно данных для входа", message);
    }

    @Test
    @Description("Проверка, что курьер не может войти в систему, если не передать его password")
    @Step("Вход курьера без пароля")
    public void CourierCanNotLogInWithoutPasswordParam(){
        CourierCredentials courierCredentials = new CourierCredentials("nnnggg1410", "");
        ValidatableResponse response = courierClient.loginCourier(courierCredentials);
        int actualStatusCode = response.extract().statusCode();
        String message = response.extract().path("message");
        assertEquals(400, actualStatusCode);
        assertEquals("Недостаточно данных для входа", message);
    }

    @Test
    @Description("Проверка, что курьер не может войти в систему, если передать неверный логин")
    @Step("Вход курьера в систему под неверным логином")
    public void CourierCanNotLogInWithWrongLoginParam(){
        CourierCredentials courierCredentials = new CourierCredentials("nnnggg14101", "1410");
        ValidatableResponse response = courierClient.loginCourier(courierCredentials);
        int actualStatusCode = response.extract().statusCode();
        String message = response.extract().path("message");
        assertEquals(404, actualStatusCode);
        assertEquals("Учетная запись не найдена", message);
    }

    @Test
    @Description("Проверка, что курьер не может войти в систему, если передать неверный пароль")
    @Step("Вход курьера в систему под неверным паролем")
    public void CourierCanNotLogInWithWrongPasswordParam(){
        CourierCredentials courierCredentials = new CourierCredentials("nnnggg1410", "14101");
        ValidatableResponse response = courierClient.loginCourier(courierCredentials);
        int actualStatusCode = response.extract().statusCode();
        String message = response.extract().path("message");
        assertEquals(404, actualStatusCode);
        assertEquals("Учетная запись не найдена", message);
    }

    @Test
    @Step("Вход в систему под несуществующим юзером")
    public void CourierCanNotLogInWithUnExistingUser(){
        CourierCredentials courierCredentials = new CourierCredentials("no_such_user", "12345");
        ValidatableResponse response = courierClient.loginCourier(courierCredentials);
        int actualStatusCode = response.extract().statusCode();
        String message = response.extract().path("message");
        assertEquals(404, actualStatusCode);
        assertEquals("Учетная запись не найдена", message);
    }

}
