package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Alert;
import pages.components.LoginComponent;
import pages.components.NavBarComponent;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MainPage {

  private final SelenideElement productStore = $("#nava");
  private final SelenideElement contcar = $("#contcar");
  private final SelenideElement contcont = $("#contcont");

  private final LoginComponent loginComponent = new LoginComponent();
  private final NavBarComponent navBarComponent = new NavBarComponent();

  @Step("Open main page")
  public MainPage openMainPage() {
    open(baseUrl);
    productStore.shouldHave(text("PRODUCT STORE"));
    contcar.should(exist);
    contcont.should(exist);

    return this;
  }

  @Step("Open 'Log in' modal")
  public MainPage openLogInModal(){
    navBarComponent.clickOnNavBarItem("Log in");
    loginComponent.verifyLogInModalIsVisible();

    return this;
  }

  @Step("Verify 'Log in' modal is visible")
  public MainPage verifyLogInModalIsVisible(){
    loginComponent.verifyLogInModalIsVisible();

    return this;
  }

  @Step("Set username on 'Log in' modal")
  public MainPage setUsernameOnLogInModal(String username){
    loginComponent.setLogInUsername(username);

    return this;
  }

  @Step("Set password on 'Log in' modal")
  public MainPage setPasswordOnLogInModal(String password){
    loginComponent.setLogInPassword(password);

    return this;
  }

  @Step("Click on 'Log in' button on 'Log in' modal")
  public MainPage clickOnLogInButtonOnLogInModal(){
    loginComponent.clickOnButtonLogIn();

    return this;
  }

  @Step("Click on 'Close' button on 'Log in' modal")
  public MainPage clickOnCloseButtonOnLogInModal(){
    loginComponent.clickOnButtonClose();

    return this;
  }

  @Step("Verify user is logged in")
  public MainPage verifyUserIsLoggedIn(String username){
    navBarComponent.verifyUserIsLoggedIn(username);

    return this;
  }

  @Step("Verify user is not logged in")
  public MainPage verifyUserIsNotLoggedIn(){
    navBarComponent.verifyUserIsNotLoggedIn();

    return this;
  }

  @Step("Click 'Ok' in alert dialog")
  public MainPage clickOkInAlertDialog(){
    Selenide.confirm();

    return this;
  }

  @Step("Verify text in alert dialog")
  public MainPage verifyTextInAlertDialog(String text){
    Alert alert = Selenide.switchTo().alert();
    assertThat(text, is(alert.getText()));

    return this;
  }

  @Step("Log out user")
  public MainPage logOutUser(){
    navBarComponent.clickOnNavBarItem("Log out");

    return this;
  }
}
