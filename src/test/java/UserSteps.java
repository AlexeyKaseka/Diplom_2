import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.practicum.User;

import static io.restassured.RestAssured.given;

public class UserSteps {
    public static final String CONTENT_TYPE = "Content-type";
    public static final String APPLICATION_JSON = "application/json";
    public static final String CREATE_USER_ENDPOINT = "/api/auth/register";
    public static final String LOGIN_USER_ENDPOINT = "/api/auth/login";
    public static final String DELETE_USER_ENDPOINT = "/api/auth/user";
    public static final String AUTHORIZATION = "Authorization";

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


    @Step("Получение accessToken")
    public String getAccessToken(User user) {
        return loginUser(user)
                .extract()
                .path("accessToken");
    }

    @Step("Удаление пользователя по accessToken")
    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(AUTHORIZATION, accessToken)
                .when()
                .delete(DELETE_USER_ENDPOINT)
                .then();
    }


}