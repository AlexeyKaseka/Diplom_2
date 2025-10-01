import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.junit.Before;

public class BaseTest {
    @Before
    public void startUp(){

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://stellarburgers.nomoreparties.site")
                .build();
    }
}
