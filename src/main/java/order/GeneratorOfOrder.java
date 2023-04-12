package order;

import java.util.List;

public class GeneratorOfOrder {

    public static OrderScooter getNewOrder(List<String> color) {
        return new OrderScooter(
                "Harry",
                "Potter",
                "Moscow",
                13,
                "+7 123 456 78 90",
                2,
                "2023-04-02",
                "Домофон не работает",
                color);
    }
}