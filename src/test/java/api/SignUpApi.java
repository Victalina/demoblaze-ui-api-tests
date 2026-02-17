package api;

import helpers.PasswordEncoder;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.ErrorMessageModel;
import models.SignUpRequestModel;

import static io.restassured.RestAssured.given;
import static spec.Spec.requestSpec;
import static spec.Spec.responseSpecStatusCode;

public class SignUpApi {

  @Step("Send request to sign up user")
  public Response sendRequestToSignUpUser(String login, String password) {

    SignUpRequestModel signUpData = new SignUpRequestModel(login, PasswordEncoder.encode(password));
    return
            given(requestSpec)
                    .body(signUpData)
                    .when()
                    .post("/signup")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract()
                    .response();
  }

  @Step("Send request to unsuccessful sign up")
  public ErrorMessageModel unsuccessfulSignUpReturnsError(String login, String password) {
    // API returns 200 even for error cases
    SignUpRequestModel signUpData = new SignUpRequestModel(login, PasswordEncoder.encode(password));
    return
            given(requestSpec)
                    .body(signUpData)
                    .when()
                    .post("/signup")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(ErrorMessageModel.class);
  }
}
