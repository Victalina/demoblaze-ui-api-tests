package tests.ui;

import config.TestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;

@DisplayName("Login tests")
public class LoginTests extends TestBase {
  private final String login = TestConfig.get("test.user.login");
  private final String password = TestConfig.get("test.user.password");
  private final MainPage mainPage = new MainPage();

  @DisplayName("Successful login with existing user")
  @Test
  void successfulLoginWithExistingUserTest() {
    mainPage.openMainPage()
            .verifyUserIsNotLoggedIn()
            .openLogInModal()
            .setUsernameOnLogInModal(login)
            .setPasswordOnLogInModal(password)
            .clickOnLogInButtonOnLogInModal()
            .verifyUserIsLoggedIn(login);
  }

  @DisplayName("Unsuccessful login after clicking 'Close' button")
  @Test
  void unsuccessfulLoginAfterClickingCloseButtonTest() {
    mainPage.openMainPage()
            .openLogInModal()
            .setUsernameOnLogInModal(login)
            .setPasswordOnLogInModal(password)
            .clickOnCloseButtonOnLogInModal()
            .verifyUserIsNotLoggedIn();
  }

  @DisplayName("Unsuccessful login with wrong password")
  @Test
  void unsuccessfulLoginWithWrongPasswordTest() {
    mainPage.openMainPage()
            .openLogInModal()
            .setUsernameOnLogInModal(login)
            .setPasswordOnLogInModal("test123")
            .clickOnLogInButtonOnLogInModal()
            .verifyTextInAlertDialog("Wrong password.")
            .clickOkInAlertDialog()
            .verifyLogInModalIsVisible();
  }

  @DisplayName("Unsuccessful login with empty username")
  @Test
  void unsuccessfulLoginWithEmptyUsernameTest() {
    mainPage.openMainPage()
            .openLogInModal()
            .setUsernameOnLogInModal("")
            .setPasswordOnLogInModal(password)
            .clickOnLogInButtonOnLogInModal()
            .verifyTextInAlertDialog("Please fill out Username and Password.")
            .clickOkInAlertDialog()
            .verifyLogInModalIsVisible();
  }

  @DisplayName("Unsuccessful login with empty password")
  @Test
  void unsuccessfulLoginWithEmptyPasswordTest() {
    mainPage.openMainPage()
            .openLogInModal()
            .setUsernameOnLogInModal(login)
            .setPasswordOnLogInModal("")
            .clickOnLogInButtonOnLogInModal()
            .verifyTextInAlertDialog("Please fill out Username and Password.")
            .clickOkInAlertDialog()
            .verifyLogInModalIsVisible();
  }

  @DisplayName("Successful log out")
  @Test
  void successfulLogOutTest() {
    mainPage.openMainPage()
            .openLogInModal()
            .setUsernameOnLogInModal(login)
            .setPasswordOnLogInModal(password)
            .clickOnLogInButtonOnLogInModal()
            .verifyUserIsLoggedIn(login)
            .logOutUser()
            .verifyUserIsNotLoggedIn();
  }
}
