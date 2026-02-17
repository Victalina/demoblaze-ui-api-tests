package tests.api;

import helpers.TestData;
import io.restassured.response.Response;
import models.ErrorMessageModel;
import org.junit.jupiter.api.*;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static spec.Spec.requestSpec;
import static spec.Spec.responseSpecStatusCode;

@Tags({
        @Tag("all_api_tests"),
        @Tag("signup_api_tests")

})
@DisplayName("Sign up tests")
public class SignUpTests extends TestBase {

  TestData testData = new TestData();
  private final String login = testData.getLogin();
  private final String password = testData.getPassword();

  @Test
  @DisplayName("After successful sign up, login should return auth token")
  void afterSuccessfulSignUpLoginShouldReturnAuthTokenTest() {

    Response responseSignUp = signUpApi.sendRequestToSignUpUser(login, password);

    Response responseAuth = authApi.loginWithRegisteredUser(login, password);
    step("Verify auth token is returned", () -> {
      String responseAuthString = responseAuth.asString();
      String token = responseAuthString.replace("\"Auth_token: ", "").replace("\"", "");
      assertThat(token, not(emptyString()));
    });
  }

  @Test
  @DisplayName("Unsuccessful sign up with existing user should return error message")
  void signUpWithExistingUserShouldReturnErrorMessageTest() {

    Response responseSignUp = signUpApi.sendRequestToSignUpUser(login, password);
    ErrorMessageModel responseReSignUp = signUpApi.unsuccessfulSignUpReturnsError(login, password);

    step("Verify error in response", () ->
            assertThat(responseReSignUp.getErrorMessage(), is("This user already exist.")));
  }

  @Disabled("User can sign up with empty password")
  @Test
  @DisplayName("Unsuccessful sign up with empty password should return error message")
  void signUpWithEmptyPasswordShouldReturnErrorMessageTest() {
    ErrorMessageModel response = signUpApi.unsuccessfulSignUpReturnsError(login, "");

    step("Verify error in response", () ->
            assertThat(response.getErrorMessage(), is("Bad parameter, missing username or password")));
  }

  @Disabled("Status code: 500 Internal Server Error")
  @Test
  @DisplayName("Unsuccessful sign up with empty username should return error message")
  void signUpWithEmptyUsernameShouldReturnErrorMessageTest() {

    ErrorMessageModel response = signUpApi.unsuccessfulSignUpReturnsError("", password);

    step("Verify error in response", () ->
            assertThat(response.getErrorMessage(), is("Bad parameter, missing username or password")));
  }

  @Test
  @DisplayName("Unsuccessful sign up with empty request body should return error message")
  void signUpWithEmptyRequestBodyShouldReturnErrorMessageTest() {

    String emptyBody = "{}";
    ErrorMessageModel response = step("Send sign up request with empty body", () ->
            given(requestSpec)
                    .body(emptyBody)
                    .when()
                    .post("/signup")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(ErrorMessageModel.class));
    step("Verify error in response", () ->
            assertThat(response.getErrorMessage(), is("Bad parameter, missing username or password")));
  }

  @Test
  @DisplayName("Unsuccessful sign up with missing request body")
  void unsuccessfulSignUpMissingRequestBodyTest() {

    step("Send sign up request with missing body", () -> given(requestSpec)
            .when()
            .post("/signup")
            .then()
            .spec(responseSpecStatusCode(400)));
  }

  @Test
  @DisplayName("Unsuccessful sign up with wrong request body")
  void unsuccessfulSignUpWrongRequestBodyTest() {

    String wrongBody = "%}";

    step("Send sign up request with wrong body", () -> given(requestSpec)
            .body(wrongBody)
            .when()
            .post("/signup")
            .then()
            .spec(responseSpecStatusCode(400)));
  }
}
