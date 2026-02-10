package tests.api;

import helpers.PasswordEncoder;
import io.restassured.response.Response;
import models.ErrorMessageModel;
import models.LoginRequestModel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.MatcherAssert.assertThat;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static spec.Spec.requestSpec;
import static spec.Spec.responseSpecStatusCode;

@DisplayName("Login Tests")
public class LoginTests extends TestBase {
  // API returns 200 even for error cases

  @Test
  @DisplayName("Successful login should return auth token")
  void successfulLoginShouldReturnAuthTokenTest() {

    LoginRequestModel authData = new LoginRequestModel("Auto Tests",
            PasswordEncoder.encode("Test2026!"));
    Response response = step("Send login request with valid credentials", () ->
            given(requestSpec)
                    .body(authData)
                    .contentType("application/json")
                    .when()
                    .post("/login")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract()
                    .response());
    step("Verify auth token is returned", () ->{
    String responseString = response.asString();
    String token = responseString.replace("\"Auth_token: ", "").replace("\"", "");
    assertThat(token, not(emptyString()));
    });
  }

  @Test
  @DisplayName("Unsuccessful login with non-existent user should return error message")
  void loginWithNonExistentUserShouldReturnErrorMessageTest() {

    LoginRequestModel authData = new LoginRequestModel("Auto Tests1",
            PasswordEncoder.encode("Test2026!"));
    ErrorMessageModel response = step("Send login request with non-existent user", () ->
            given(requestSpec)
                    .body(authData)
                    .contentType("application/json")
                    .when()
                    .post("/login")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(ErrorMessageModel.class));
    step("Verify error in response", () ->
    assertThat(response.getErrorMessage(), is("User does not exist.")));
  }

  @Test
  @DisplayName("Unsuccessful login with wrong password should return error message")
  void loginWithWrongPasswordShouldReturnErrorMessageTest() {

    LoginRequestModel authData = new LoginRequestModel("Auto Tests",
            PasswordEncoder.encode("Test2026"));
    ErrorMessageModel response = step("Send login request with wrong password", () ->
            given(requestSpec)
                    .body(authData)
                    .contentType("application/json")
                    .when()
                    .post("/login")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(ErrorMessageModel.class));
    step("Verify error in response", () ->
    assertThat(response.getErrorMessage(), is("Wrong password.")));
  }

  @Disabled("Status code: 500 Internal Server Error")
  @Test
  @DisplayName("Unsuccessful login with empty username and password should return error message")
  void loginWithEmptyUsernameAndPasswordShouldReturnErrorMessageTest() {

    LoginRequestModel authData = new LoginRequestModel("",
            PasswordEncoder.encode(""));
    ErrorMessageModel response = step("Send login request with empty username and password", () ->
            given(requestSpec)
                    .body(authData)
                    .contentType("application/json")
                    .when()
                    .post("/login")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(ErrorMessageModel.class));
    step("Verify error in response", () ->
    assertThat(response.getErrorMessage(), is("Bad parameter, missing username")));
  }

  @Test
  @DisplayName("Unsuccessful login with empty request body should return error message")
  void loginWithEmptyRequestBodyShouldReturnErrorMessageTest() {

    String emptyBody = "{}";
    ErrorMessageModel response = step("Send login request with empty body", () ->
            given(requestSpec)
                    .contentType("application/json")
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
            .contentType("application/json")
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
            .contentType("application/json")
            .body(wrongBody)
            .when()
            .post("/login")
            .then()
            .spec(responseSpecStatusCode(400)));
  }
}
