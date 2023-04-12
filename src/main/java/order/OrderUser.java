package order;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import courier.CourierClient;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderUser extends CourierClient {
    public static final String CREATE_PATH = "/api/v1/orders";
    public static final String CANCEL_PATH = "/api/v1/orders/cancel";

    public ValidatableResponse createOrder(OrderScooter orderScooter) {
        return given()
                .headers("Content-type", "application/json")
                .and()
                .body(orderScooter)
                .when()
                .post(CREATE_PATH)
                .then();
    }

    public ValidatableResponse cancelOrder(int track) {
        Gson cancelGson = new GsonBuilder().setPrettyPrinting().create();
        OrderTrackNumber orderTrackNumber = new OrderTrackNumber(track);
        String cancelJson = cancelGson.toJson(orderTrackNumber);
        return given()
                .headers("Content-type", "application/json")
                .and()
                .body(cancelJson)
                .when()
                .post(CANCEL_PATH)
                .then();
    }

    public ValidatableResponse getOrders() {
        return given()
                .headers("Content-type", "application/json")
                .and()
                .get(CREATE_PATH)
                .then();
    }
}
