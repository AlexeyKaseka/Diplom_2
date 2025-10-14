import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.practicum.Order;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    public static final String INGREDIENT_LIST_ENDPOINT = "/api/ingredients";
    public static final String CREATE_ORDERD_EBDPOINT = "/api/orders";
    public static final String AUTHORIZATION = "Authorization";

    @Step("Получение списка ингредиентов")
    public ValidatableResponse getIngredientsList() {
        return given()
                .when()
                .get(INGREDIENT_LIST_ENDPOINT)
                .then();
    }

    @Step("Получение списка ID ингредиентов")
    public List<String> getIngredientIds() {
        return getIngredientsList()
                .extract()
                .jsonPath()
                .getList("data._id");
    }


    @Step("Получение двух  ингредиентов из списка")
    public List<String> getTwoIngredients() {
        List<String> allIngredients = getIngredientIds();
        return List.of(allIngredients.get(0), allIngredients.get(1));
    }

    @Step("Создание заказа с авторизацией")
    public ValidatableResponse createOrderWithAuth(Order order, String accessToken) {
        return given()
                .header(AUTHORIZATION, accessToken)
                .body(order)
                .when()
                .post(CREATE_ORDERD_EBDPOINT)
                .then();
    }

    @Step("Создание заказа без авторизации")
    public ValidatableResponse createOrderWithOutAuth(Order order) {
        return given()
                .body(order)
                .when()
                .post(CREATE_ORDERD_EBDPOINT)
                .then();
    }


}
