package tests.ui;

import helpers.TestData;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.not;

@DisplayName("Sign up tests")
public class SignUpTests extends TestBase {

  private final MainPage mainPage = new MainPage();

  @DisplayName("Successful sign up with new user")
  @Test
  void successfulSignUpWithNewUserTest() {

    TestData testData = new TestData();
    String login = testData.getLogin();
    String password = testData.getPassword();

    mainPage.openMainPage()
            .openSignUpModal()
            .setUsernameOnSignUpModal(login)
            .setPasswordOnSignUpModal(password)
            .clickOnSignUpButtonOnSignUpModal()
            .verifyTextInAlertDialog("Sign up successful.")
            .clickOkInAlertDialog()
            .verifySignUpModalIsNotVisible()
            .verifyUserIsNotLoggedIn();

    Response response = authApi.loginWithRegisteredUser(login, password);
    step("Verify auth token is returned", () -> {
      String responseString = response.asString();
      String token = responseString.replace("\"Auth_token: ", "").replace("\"", "");
      assertThat(token, not(emptyString()));
    });
  }

  @DisplayName("Unsuccessful sign up with existing user")
  @Test
  void unsuccessfulSignUpWithExistingUserTest(){

    TestData testData = new TestData();
    String login = testData.getLogin();
    String password = testData.getPassword();

    signUpApi.sendRequestToSignUpUser(login, password);
    mainPage.openMainPage()
            .openSignUpModal()
            .setUsernameOnSignUpModal(login)
            .setPasswordOnSignUpModal(password)
            .clickOnSignUpButtonOnSignUpModal()
            .verifyTextInAlertDialog("This user already exist.")
            .clickOkInAlertDialog()
            .verifySignUpModalIsVisible();
  }

  @DisplayName("Unsuccessful sign up after clicking 'Close' button")
  @Test
  void unsuccessfulSignUpAfterClickingCloseButtonTest() {

    TestData testData = new TestData();
    String login = testData.getLogin();
    String password = testData.getPassword();

    mainPage.openMainPage()
            .openSignUpModal()
            .setUsernameOnSignUpModal(login)
            .setPasswordOnSignUpModal(password)
            .clickOnCloseButtonOnSignUpModal()
            .verifySignUpModalIsNotVisible();
  }

  @DisplayName("Unsuccessful sign up with empty username")
  @Test
  void unsuccessfulSignUpWithEmptyUsernameTest() {

    TestData testData = new TestData();
    String password = testData.getPassword();

    mainPage.openMainPage()
            .openSignUpModal()
            .setUsernameOnSignUpModal("")
            .setPasswordOnSignUpModal(password)
            .clickOnSignUpButtonOnSignUpModal()
            .verifyTextInAlertDialog("Please fill out Username and Password.")
            .clickOkInAlertDialog()
            .verifySignUpModalIsVisible();
  }

  @DisplayName("Unsuccessful sign up with empty password")
  @Test
  void unsuccessfulSignUpWithEmptyPasswordTest() {

    TestData testData = new TestData();
    String login = testData.getLogin();

    mainPage.openMainPage()
            .openSignUpModal()
            .setUsernameOnSignUpModal(login)
            .setPasswordOnSignUpModal("")
            .clickOnSignUpButtonOnSignUpModal()
            .verifyTextInAlertDialog("Please fill out Username and Password.")
            .clickOkInAlertDialog()
            .verifySignUpModalIsVisible();
  }
}
