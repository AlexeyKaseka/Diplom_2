import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.User;

import static org.hamcrest.Matchers.is;


public class CreateUserTest {
    private UserSteps userSteps = new UserSteps();
    private User user;


    @Before
    public void setUp() {
        user = new User();
        user.withEmail(RandomStringUtils.randomAlphabetic(10).toLowerCase() + "@test.ru")
                .withPassword(RandomStringUtils.randomAlphabetic(12))
                .withName(RandomStringUtils.randomAlphabetic(12));
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    @DisplayName("Создание пользователя с валидными данными")
    @Description("Позитивный тест: проверка успешного создания пользователя с полными данными (почта, пароль, имя)")
    public void createUserWithValidData() {


        userSteps.createUser(user)
                .statusCode(200)
                .body("success", is(true));

    }


}
