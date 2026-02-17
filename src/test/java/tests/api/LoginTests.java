package tests.api;

import config.TestConfig;
import io.restassured.response.Response;
import models.ErrorMessageModel;
import org.junit.jupiter.api.*;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.MatcherAssert.assertThat;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static spec.Spec.requestSpec;
import static spec.Spec.responseSpecStatusCode;

@Tags({
        @Tag("allApiTests"),
        @Tag("loginApiTests")

})
@DisplayName("Login tests")
public class LoginTests extends TestBase {

  @Test
  @DisplayName("Successful login should return auth token")
  void successfulLoginShouldReturnAuthTokenTest() {
    String login = TestConfig.get("test.user.login");
    String password = TestConfig.get("test.user.password");

    Response response = authApi.loginWithRegisteredUser(login, password);
    step("Verify auth token is returned", () -> {
      String responseString = response.asString();
      String token = responseString.replace("\"Auth_token: ", "").replace("\"", "");
      assertThat(token, not(emptyString()));
    });
  }

  @Test
  @DisplayName("Unsuccessful login with non-existent user should return error message")
  void loginWithNonExistentUserShouldReturnErrorMessageTest() {

    ErrorMessageModel response = authApi.unsuccessfulLoginReturnsError("Auto Tests1", "Test2026!");

    step("Verify error in response", () ->
            assertThat(response.getErrorMessage(), is("User does not exist.")));
  }

  @Test
  @DisplayName("Unsuccessful login with wrong password should return error message")
  void loginWithWrongPasswordShouldReturnErrorMessageTest() {

    ErrorMessageModel response = authApi.unsuccessfulLoginReturnsError("Auto Tests", "Test2026");

    step("Verify error in response", () ->
            assertThat(response.getErrorMessage(), is("Wrong password.")));
  }

  @Disabled("Status code: 500 Internal Server Error")
  @Test
  @DisplayName("Unsuccessful login with empty username and password should return error message")
  void loginWithEmptyUsernameAndPasswordShouldReturnErrorMessageTest() {

    ErrorMessageModel response = authApi.unsuccessfulLoginReturnsError("", "");

    step("Verify error in response", () ->
            assertThat(response.getErrorMessage(), is("Bad parameter, missing username")));
  }

  @Test
  @DisplayName("Unsuccessful login with empty request body should return error message")
  void loginWithEmptyRequestBodyShouldReturnErrorMessageTest() {

    String emptyBody = "{}";
    ErrorMessageModel response = step("Send login request with empty body", () ->
            given(requestSpec)
                    .body(emptyBody)
                    .when()
                    .post("/login")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(ErrorMessageModel.class));
    step("Verify error in response", () ->
            assertThat(response.getErrorMessage(), is("Bad parameter, missing username")));
  }

  @Test
  @DisplayName("Unsuccessful login with missing request body")
  void unsuccessfulLoginMissingRequestBodyTest() {

    step("Send login request with missing body", () -> given(requestSpec)
            .when()
            .post("/login")
            .then()
            .spec(responseSpecStatusCode(400)));
  }

  @Test
  @DisplayName("Unsuccessful login with wrong request body")
  void unsuccessfulLoginWrongRequestBodyTest() {

    String wrongBody = "%}";

    step("Send login request with wrong body", () -> given(requestSpec)
            .body(wrongBody)
            .when()
            .post("/login")
            .then()
            .spec(responseSpecStatusCode(400)));
  }
}
