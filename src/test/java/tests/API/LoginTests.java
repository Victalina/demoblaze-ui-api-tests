package tests.API;

import helpers.PasswordEncoder;
import io.restassured.response.Response;
import models.ErrorMessageModel;
import models.LoginRequestModel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static spec.Spec.requestSpec;
import static spec.Spec.responseSpecStatusCode;

public class LoginTests extends TestBase {
  // API returns 200 even for error cases

  @Test
  void successfulLoginShouldReturnAuthTokenTest() {

    LoginRequestModel authData = new LoginRequestModel("Auto Tests",
            PasswordEncoder.encode("Test2026!"));
    Response response =
            given(requestSpec)
                    .body(authData)
                    .contentType("application/json")
                    .when()
                    .post("/login")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract()
                    .response();

    String responseString = response.asString();
    String token = responseString.replace("\"Auth_token: ", "").replace("\"", "");
    assertThat(token, not(emptyString()));
  }

  @Test
  void loginWithNonExistingUserShouldReturnErrorMessageTest() {

    LoginRequestModel authData = new LoginRequestModel("Auto Tests1",
            PasswordEncoder.encode("Test2026!"));
    ErrorMessageModel response =
            given(requestSpec)
                    .body(authData)
                    .contentType("application/json")
                    .when()
                    .post("/login")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(ErrorMessageModel.class);

    assertThat(response.getErrorMessage(), is("User does not exist."));
  }

  @Test
  void loginWithWrongPasswordShouldReturnErrorMessageTest() {

    LoginRequestModel authData = new LoginRequestModel("Auto Tests",
            PasswordEncoder.encode("Test2026"));
    ErrorMessageModel response =
            given(requestSpec)
                    .body(authData)
                    .contentType("application/json")
                    .when()
                    .post("/login")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(ErrorMessageModel.class);

    assertThat(response.getErrorMessage(), is("Wrong password."));
  }

  @Disabled("Status code: 500 Internal Server Error")
  @Test
  void loginWithEmptyUsernameAndPasswordShouldReturnErrorMessageTest() {

    LoginRequestModel authData = new LoginRequestModel("",
            PasswordEncoder.encode(""));
    ErrorMessageModel response =
            given(requestSpec)
                    .body(authData)
                    .contentType("application/json")
                    .when()
                    .post("/login")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(ErrorMessageModel.class);

    assertThat(response.getErrorMessage(), is("Bad parameter, missing username"));
  }

  @Test
  void loginWithEmptyRequestBodyShouldReturnErrorMessageTest() {

    String emptyBody = "{}";
    ErrorMessageModel response =
            given(requestSpec)
                    .contentType("application/json")
                    .body(emptyBody)
                    .when()
                    .post("/login")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(ErrorMessageModel.class);

    assertThat(response.getErrorMessage(), is("Bad parameter, missing username"));
  }

  @Test
  void unsuccessfulLoginMissingRequestBodyTest() {

    given(requestSpec)
            .contentType("application/json")
            .when()
            .post("/login")
            .then()
            .spec(responseSpecStatusCode(400));
  }

  @Test
  void unsuccessfulLoginWrongRequestBodyTest() {

    String wrongBody = "%}";

    given(requestSpec)
            .contentType("application/json")
            .body(wrongBody)
            .when()
            .post("/login")
            .then()
            .spec(responseSpecStatusCode(400));
  }
}
