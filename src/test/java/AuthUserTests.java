import User.User;
import User.UserClient;
import User.UserCredentials;
import User.UserGeneration;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

public class AuthUserTests {
    private final UserClient userClient = new UserClient();
    private final User randomUser = UserGeneration.createRandomUser();
    private final UserCredentials userCredentials = new UserCredentials(randomUser.getEmail(), randomUser.getPassword(), randomUser.getName());
    private int actualStatusCode;
    private String accessToken;

    @Test
    @DisplayName("Авторизация под существующим юзером")
    public void authTest() {
        UserClient.createUser(randomUser);
        ValidatableResponse response = UserClient.authUser(userCredentials);
        actualStatusCode = response.extract().statusCode();
        accessToken = response.extract().path("accessToken");
        Assert.assertEquals(actualStatusCode, SC_OK);
    }

    @Test
    @DisplayName("Авторизация с некорректными логином и паролем")
    public void authWithWrongDataTest() {
        UserCredentials userCredentials = new UserCredentials("Fsdfsdf@gmail.com", "3465634", "Pavel");
        ValidatableResponse response = UserClient.authUser(userCredentials);
        actualStatusCode = response.extract().statusCode();
        Assert.assertEquals(actualStatusCode, SC_UNAUTHORIZED);
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }
}
