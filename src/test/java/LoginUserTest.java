import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.User;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.is;

public class LoginUserTest extends BaseTest {
    private UserSteps userSteps = new UserSteps();
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        user = new User();
        user.withEmail(RandomStringUtils.randomAlphabetic(6) + "@test.ru")
                .withPassword(RandomStringUtils.randomAlphabetic(8))
                .withName(RandomStringUtils.randomAlphabetic(8));

        userSteps.createUser(user);
        accessToken = userSteps.getAccessToken(user);


    }


    @Test
    @DisplayName("Авторизация пользователя")
    @Description("Позитивный тест: проверка успешной авторизации пользователя")
    public void loginUserWithValidData() {

        user.withEmail(user.getEmail())
                .withPassword(user.getPassword());

        userSteps.loginUser(user)
                .statusCode(SC_OK)
                .body("success", is(true));


    }


    @Test
    @DisplayName("Авторизация пользователя без почты")
    @Description("Негативный тест: проверка авторизации пользователя без почты")
    public void loginUserWithOutEmail() {

        user.withEmail(null)
                .withPassword(user.getPassword());


        userSteps.loginUser(user)
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", is("email or password are incorrect"));

    }


    @Test
    @DisplayName("Авторизация пользователя без пароля")
    @Description("Негативный тест: проверка авторизации пользователя без пароля")
    public void loginUserWithOutPassword() {

        user.withEmail(user.getEmail())
                .withPassword(null);


        userSteps.loginUser(user)
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", is("email or password are incorrect"));

    }

    @Test
    @DisplayName("Авторизация пользователя с неверной почтой")
    @Description("Негативный тест: проверка авторизации пользователя с неверной почтой")
    public void loginUserWithIncorrectEmail() {

        user.withEmail("1234@k.com")
                .withPassword(user.getPassword());


        userSteps.loginUser(user)
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", is("email or password are incorrect"));

    }

    @Test
    @DisplayName("Авторизация пользователя c неверным паролем")
    @Description("Негативный тест: проверка авторизации пользователя с неверным паролем")
    public void loginUserWithIncorrectPassword() {

        user.withEmail(user.getEmail())
                .withPassword("12345678");


        userSteps.loginUser(user)
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", is("email or password are incorrect"));

    }


    @After
    public void tearDown() {


        if (accessToken != null) {
            userSteps.deleteUser(accessToken);

        }
    }


}