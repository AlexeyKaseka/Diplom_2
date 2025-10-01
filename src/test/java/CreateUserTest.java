import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.User;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.is;


public class CreateUserTest extends BaseTest{
    private UserSteps userSteps = new UserSteps();
    private User user;


    @Before
    public void setUp() {
        user = new User();
        user.withEmail(RandomStringUtils.randomAlphabetic(12) + "@test.ru")
                .withPassword(RandomStringUtils.randomAlphabetic(12))
                .withName(RandomStringUtils.randomAlphabetic(12));

    }

    @Test
    @DisplayName("Создание пользователя с валидными данными")
    @Description("Позитивный тест: проверка успешного создания пользователя с полными данными (почта, пароль, имя)")
    public void createUserWithValidData() {


        userSteps.createUser(user)
                .statusCode(SC_OK)
                .body("success", is(true));

    }

    @Test
    @DisplayName("Создание пользователя который уже зарегестрирован")
    @Description("Негативный тест: повторное создание пользователя с теми же данными")
    public void createAlreadyRegisteredUser() {


        userSteps.createUser(user);


        userSteps.createUser(user)
                .statusCode(SC_FORBIDDEN)
                .body("success", is(false));

    }


    @Test
    @DisplayName("Создание пользователя без почты")
    @Description("ПНегативный тест: проверка успешного создания пользователя без указания почты")
    public void createUserWithOutEmail() {
        user.withEmail(null)
                .withPassword(RandomStringUtils.randomAlphabetic(12))
                .withName(RandomStringUtils.randomAlphabetic(12));

        userSteps.createUser(user)
                .statusCode(SC_FORBIDDEN)
                .body("success", is(false));

    }

    @Test
    @DisplayName("Создание пользователя без пароля")
    @Description("Негативный тест: проверка успешного создания пользователя без указания пароля")
    public void createUserWithOutPassword() {
        user.withEmail(RandomStringUtils.randomAlphabetic(12) + "@test.ru")
                .withPassword(null)
                .withName(RandomStringUtils.randomAlphabetic(12));

        userSteps.createUser(user)
                .statusCode(SC_FORBIDDEN)
                .body("success", is(false));

    }

    @Test
    @DisplayName("Создание пользователя без имени")
    @Description("Негативный тест: проверка успешного создания пользователя без указания имени")
    public void createUserWithOutName() {
        user.withEmail(RandomStringUtils.randomAlphabetic(12) + "@test.ru")
                .withPassword(RandomStringUtils.randomAlphabetic(12))
                .withName(null);

        userSteps.createUser(user)
                .statusCode(SC_FORBIDDEN)
                .body("success", is(false));

    }


    @After
    public void tearDown() {
        String accessToken = userSteps.getAccessToken(user);

        if (accessToken != null) {
            userSteps.deleteUser(accessToken);

        }
    }
}