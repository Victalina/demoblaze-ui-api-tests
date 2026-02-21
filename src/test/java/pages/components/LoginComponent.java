package pages.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginComponent {

  private final SelenideElement logInModal = $("#logInModal");
  private final SelenideElement logInModalLabel = $("#logInModal #logInModalLabel");
  private final SelenideElement logInUsernameInput = $("#logInModal #loginusername");
  private final SelenideElement logInPasswordInput = $("#logInModal #loginpassword");
  private final SelenideElement logInButton = $("#logInModal").$(".modal-footer").$$("button")
          .findBy(text("Log in"));
  private final SelenideElement closeButton = $("#logInModal").$(".modal-footer").$$("button")
          .findBy(text("Close"));

  public void verifyLogInModalIsVisible() {

    logInModal.shouldBe(visible);
    logInModalLabel.shouldHave(text("Log in"));
  }

  public void verifyLogInModalIsNotVisible() {
    logInModal.shouldNotBe(visible);
  }

  public void setLogInUsername(String username) {
    logInUsernameInput.setValue(username);
  }

  public void setLogInPassword(String password) {
    logInPasswordInput.setValue(password);
  }

  public void clickOnButtonLogIn() {
    logInButton.click();
  }

  public void clickOnButtonClose() {
    closeButton.click();
  }
}
