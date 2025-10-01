import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.practicum.User;

import static io.restassured.RestAssured.given;

public class UserSteps {

    public static final String CREATE_USER_ENDPOINT = "/api/auth/register";
    public static final String LOGIN_USER_ENDPOINT = "/api/auth/login";
    public static final String DELETE_USER_ENDPOINT = "/api/auth/user";
    public static final String AUTHORIZATION = "Authorization";

    @Step("Создание пользователя")
    public ValidatableResponse createUser(User user) {
        return given()
                .body(user)
                .when()
                .post(CREATE_USER_ENDPOINT)
                .then();
    }


    @Step("Авторизация пользователя")
    public ValidatableResponse loginUser(User user) {
        return given()
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
                .header(AUTHORIZATION, accessToken)
                .when()
                .delete(DELETE_USER_ENDPOINT)
                .then();
    }


}