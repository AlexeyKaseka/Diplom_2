import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.practicum.User;
import static io.restassured.RestAssured.given;

public class UserSteps {
    public static final String CONTENT_TYPE = "Content-type";
    public static final String APPLICATION_JSON = "application/json";
    public static final String CREATE_USER_ENDPOINT = "/api/auth/register";
    public static final String LOGIN_USER_ENDPOINT = "/api/auth/login";


    @Step("Создание пользователя")
    public ValidatableResponse createUser(User user) {
        return given()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .body(user)
                .when()
                .post(CREATE_USER_ENDPOINT)
                .then();
    }

    @Step("Авторизация пользователя")
    public ValidatableResponse loginUser(User user) {
        return given()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .body(user)
                .when()
                .post(LOGIN_USER_ENDPOINT)
                .then();


    }
}