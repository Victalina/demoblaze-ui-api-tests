package tests.ui;

import context.TestUserContext;
import context.User;
import extensions.WithLogin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import pages.MainPage;

@Tags({
        @Tag("allUiTests"),
        @Tag("loginUiTests")

})
@DisplayName("Login tests")
public class LoginTests extends TestBase {
  User user = TestUserContext.get();
  private final MainPage mainPage = new MainPage();

  @Test
  @DisplayName("Successful login with existing user")
  void successfulLoginWithExistingUserTest() {
    mainPage.openMainPage()
            .verifyUserIsNotLoggedIn()
            .openLogInModal()
            .setUsernameOnLogInModal(user.getLogin())
            .setPasswordOnLogInModal(user.getPassword())
            .clickOnLogInButtonOnLogInModal()
            .verifyUserIsLoggedIn(user.getLogin());
  }

  @Test
  @DisplayName("Unsuccessful login after clicking 'Close' button")
  void unsuccessfulLoginAfterClickingCloseButtonTest() {
    mainPage.openMainPage()
            .openLogInModal()
            .setUsernameOnLogInModal(user.getLogin())
            .setPasswordOnLogInModal(user.getPassword())
            .clickOnCloseButtonOnLogInModal()
            .verifyLogInModalIsNotVisible()
            .verifyUserIsNotLoggedIn();
  }

  @Test
  @DisplayName("Unsuccessful login with wrong password")
  void unsuccessfulLoginWithWrongPasswordTest() {
    mainPage.openMainPage()
            .openLogInModal()
            .setUsernameOnLogInModal(user.getLogin())
            .setPasswordOnLogInModal("test123")
            .clickOnLogInButtonOnLogInModal()
            .verifyTextInAlertDialog("Wrong password.")
            .clickOkInAlertDialog()
            .verifyLogInModalIsVisible();
  }

  @Test
  @DisplayName("Unsuccessful login with non-existent user")
  void unsuccessfulLoginWithNonExistentUserTest() {
    mainPage.openMainPage()
            .openLogInModal()
            .setUsernameOnLogInModal("testtest123544")
            .setPasswordOnLogInModal(user.getPassword())
            .clickOnLogInButtonOnLogInModal()
            .verifyTextInAlertDialog("User does not exist.")
            .clickOkInAlertDialog()
            .verifyLogInModalIsVisible();
  }

  @Test
  @DisplayName("Unsuccessful login with empty username")
  void unsuccessfulLoginWithEmptyUsernameTest() {
    mainPage.openMainPage()
            .openLogInModal()
            .setUsernameOnLogInModal("")
            .setPasswordOnLogInModal(user.getPassword())
            .clickOnLogInButtonOnLogInModal()
            .verifyTextInAlertDialog("Please fill out Username and Password.")
            .clickOkInAlertDialog()
            .verifyLogInModalIsVisible();
  }

  @Test
  @DisplayName("Unsuccessful login with empty password")
  void unsuccessfulLoginWithEmptyPasswordTest() {
    mainPage.openMainPage()
            .openLogInModal()
            .setUsernameOnLogInModal(user.getLogin())
            .setPasswordOnLogInModal("")
            .clickOnLogInButtonOnLogInModal()
            .verifyTextInAlertDialog("Please fill out Username and Password.")
            .clickOkInAlertDialog()
            .verifyLogInModalIsVisible();
  }

  @Test
  @WithLogin
  @DisplayName("Successful log out")
  void successfulLogOutTest() {
    mainPage.openMainPage()
            .verifyUserIsLoggedIn(TestUserContext.get().getLogin())
            .logOutUser()
            .verifyUserIsNotLoggedIn();
  }
}
