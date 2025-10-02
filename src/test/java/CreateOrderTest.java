import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.Order;
import ru.practicum.User;

import java.util.Arrays;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;


public class CreateOrderTest extends BaseTest {
    private UserSteps userSteps = new UserSteps();
    private OrderSteps orderSteps = new OrderSteps();
    private User user;
    private Order order;
    private String accessToken;

    @Before
    public void setUp() {
        user = new User();
        user.withEmail(RandomStringUtils.randomAlphabetic(6) + "@test.ru")
                .withPassword(RandomStringUtils.randomAlphabetic(8))
                .withName(RandomStringUtils.randomAlphabetic(8));
        userSteps.createUser(user);
        accessToken = userSteps.getAccessToken(user);

        order = new Order();
        order.withIngredients(orderSteps.getTwoIngredients());

    }

    @Test
    @DisplayName("Создание заказа с валидными ингредиентами с авторизацией")
    @Description("Позитивный тест: создание заказа с авторизированным пользователем")
    public void createOrderWithAuth() {


        orderSteps.createOrderWithAuth(order, accessToken)
                .statusCode(SC_OK)
                .body("success", is(true));


    }

    @Test
    @DisplayName("Создание заказа с валидными ингредиентами без авторизации")
    @Description("Негативныйй тест: создание заказа с не авторизированным пользователем")
    public void createOrderWithOutAuth() {


        orderSteps.createOrderWithOutAuth(order)
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false));


    }


    @Test
    @DisplayName("Создание заказа с авторизацией без ингредиентов")
    @Description("Негативный тест: создание заказа с авторизированным пользователем без ингридиентов")
    public void createOrderWithAuthWithOutIngredients() {

        order.withIngredients(null);

        orderSteps.createOrderWithAuth(order, accessToken)
                .statusCode(SC_BAD_REQUEST)
                .body("success", is(false));


    }

    @Test
    @DisplayName("Создание заказа с авторизацией и невалидным хэшем ингредиентов")
    @Description("Негативный тест: создание заказа с авторизированным пользователем и невалидным хэшем ингредиентов")

    public void createOrderWithAuthWithInvalidIngredients() {

        order.withIngredients(Arrays.asList("invalid_hash_1", "invalid_hash_2"));

        orderSteps.createOrderWithAuth(order, accessToken)
                .statusCode(SC_INTERNAL_SERVER_ERROR);


    }

    @After
    public void tearDown() {


        if (accessToken != null) {
            userSteps.deleteUser(accessToken);

        }
    }


}