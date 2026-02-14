package api;

import helpers.PasswordEncoder;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.ErrorMessageModel;
import models.LoginRequestModel;

import static io.restassured.RestAssured.given;
import static spec.Spec.requestSpec;
import static spec.Spec.responseSpecStatusCode;

public class AuthApi {

  @Step("Send request to login with registered user")
  public Response loginWithRegisteredUser(String login, String password) {
    LoginRequestModel authData = new LoginRequestModel(login, PasswordEncoder.encode(password));
    return
            given(requestSpec)
                    .body(authData)
                    .when()
                    .post("/login")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract()
                    .response();
  }

  @Step("Send request to unsuccessful login")
  public ErrorMessageModel unsuccessfulLoginReturnsError(String login, String password) {
    // API returns 200 even for error cases
    LoginRequestModel authData = new LoginRequestModel(login, PasswordEncoder.encode(password));
    return
            given(requestSpec)
                    .body(authData)
                    .when()
                    .post("/login")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(ErrorMessageModel.class);
  }
}
