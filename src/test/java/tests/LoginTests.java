package tests;

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

  @Test
  void SuccessfulLoginTest() {

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
    assertThat(responseString, not(emptyOrNullString()));
    assertThat(responseString, startsWith("\"Auth_token: "));
  }

  @Test
  void unsuccessfulLoginUserNotFoundTest() {

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
  void unsuccessfulLoginWrongPasswordTest() {

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
  void unsuccessfulLoginEmptyUsernameAndPasswordTest() {

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
  void unsuccessfulLoginEmptyRequestBodyTest() {

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
