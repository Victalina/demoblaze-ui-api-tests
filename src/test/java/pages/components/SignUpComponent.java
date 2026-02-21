package pages.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class SignUpComponent {

  private final SelenideElement signInModal = $("#signInModal");
  private final SelenideElement signInModalLabel = $("#signInModal #signInModalLabel");
  private final SelenideElement signInUsernameInput = $("#signInModal #sign-username");
  private final SelenideElement signInPasswordInput = $("#signInModal #sign-password");
  private final SelenideElement signInButton = $("#signInModal").$(".modal-footer").$$("button")
          .findBy(text("Sign up"));
  private final SelenideElement closeButton = $("#signInModal").$(".modal-footer").$$("button")
          .findBy(text("Close"));

  public void verifySignUpModalIsVisible() {

    signInModal.shouldBe(visible);
    signInModalLabel.shouldHave(text("Sign up"));
  }

  public void verifySignUpModalIsNotVisible() {
    signInModal.shouldNotBe(visible);
  }

  public void setSignUpUsername(String username) {
    signInUsernameInput.setValue(username);
  }

  public void setSignUpPassword(String password) {
    signInPasswordInput.setValue(password);
  }

  public void clickOnButtonSignUp() {
    signInButton.click();
  }

  public void clickOnButtonClose() {
    closeButton.click();
  }
}
