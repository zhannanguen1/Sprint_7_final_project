package courier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import courier.Courier;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static java.lang.String.valueOf;

public class CourierClient {

    public static final String MAIN_URL = "http://qa-scooter.praktikum-services.ru";
    public static final String PATH = "api/v1/courier";
    public static final String LOGIN_PATH = "/api/v1/courier/login";
    public static final String DELETE_PATH = "/api/v1/courier/:";

    public CourierClient(){
        RestAssured.baseURI = MAIN_URL;
    }
    public ValidatableResponse createCourier (Courier courier){
        return given()
                .headers("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(PATH)
                .then();
    }

    public ValidatableResponse deleteCourier(int courierId) {
        Gson deleteGson = new GsonBuilder().setPrettyPrinting().create();
        CourierDelete courierDelete = new CourierDelete(valueOf(courierId));
        String deleteJson = deleteGson.toJson(courierDelete);
        return given()
                .headers("Content-type", "application/json")
                .and()
                .body(deleteJson)
                .when()
                .delete(DELETE_PATH + String.valueOf(courierId))
                .then();
    }
    public ValidatableResponse loginCourier(CourierCredentials courierCredentials){
        Gson loginGson = new GsonBuilder().setPrettyPrinting().create();
        String loginJson = loginGson.toJson(courierCredentials);
        return given()
                .headers("Content-type", "application/json")
                .and()
                .body(loginJson)
                .when()
                .post(LOGIN_PATH)
                .then();
    }

}
