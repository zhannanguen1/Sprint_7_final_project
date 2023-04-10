import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LoginCourierTest {
    private CourierClient courierClient;

    @Before
    public void setUp(){
        courierClient = new CourierClient();
    }

    @Test
    @Description("Проверка, что курьер может войти в систему")
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
    public void CourierCanNotLogInWithWrongPasswordParam(){
        CourierCredentials courierCredentials = new CourierCredentials("nnnggg1410", "14101");
        ValidatableResponse response = courierClient.loginCourier(courierCredentials);
        int actualStatusCode = response.extract().statusCode();
        String message = response.extract().path("message");
        assertEquals(404, actualStatusCode);
        assertEquals("Учетная запись не найдена", message);
    }

    @Test
    @Description("Проверка, что нельзя войти в систему под несуществующим юзером")
    public void CourierCanNotLogInWithUnExistingUser(){
        CourierCredentials courierCredentials = new CourierCredentials("no_such_user", "12345");
        ValidatableResponse response = courierClient.loginCourier(courierCredentials);
        int actualStatusCode = response.extract().statusCode();
        String message = response.extract().path("message");
        assertEquals(404, actualStatusCode);
        assertEquals("Учетная запись не найдена", message);
    }

}
