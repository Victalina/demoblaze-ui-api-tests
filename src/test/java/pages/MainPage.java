package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Alert;
import pages.components.LoginComponent;
import pages.components.NavBarComponent;
import pages.components.SignUpComponent;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
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
  private final ElementsCollection catalogItems = $("#tbodyid").$$("div.col-lg-4");
  private final SelenideElement catalogFirstItemImg = $("#tbodyid").$$("div.col-lg-4").first()
          .$(".card-img-top");
  private final SelenideElement catalogFirstItemTitle = $("#tbodyid").$$("div.col-lg-4").first()
          .$(".card-title");

  private final LoginComponent loginComponent = new LoginComponent();
  private final NavBarComponent navBarComponent = new NavBarComponent();
  private final SignUpComponent signUpComponent = new SignUpComponent();


  @Step("Open main page")
  public MainPage openMainPage() {
    open(baseUrl);
    productStore.shouldHave(text("PRODUCT STORE"));
    contcar.should(exist);
    contcont.should(exist);

    return this;
  }

  @Step("Open 'Log in' modal")
  public MainPage openLogInModal() {
    navBarComponent.clickOnNavBarItem("Log in");
    loginComponent.verifyLogInModalIsVisible();

    return this;
  }

  @Step("Verify 'Log in' modal is visible")
  public MainPage verifyLogInModalIsVisible() {
    loginComponent.verifyLogInModalIsVisible();

    return this;
  }

  @Step("Verify 'Log in' modal is not visible")
  public MainPage verifyLogInModalIsNotVisible() {
    loginComponent.verifyLogInModalIsNotVisible();

    return this;
  }

  @Step("Set username on 'Log in' modal")
  public MainPage setUsernameOnLogInModal(String username) {
    loginComponent.setLogInUsername(username);

    return this;
  }

  @Step("Set password on 'Log in' modal")
  public MainPage setPasswordOnLogInModal(String password) {
    loginComponent.setLogInPassword(password);

    return this;
  }

  @Step("Click on 'Log in' button on 'Log in' modal")
  public MainPage clickOnLogInButtonOnLogInModal() {
    loginComponent.clickOnButtonLogIn();

    return this;
  }

  @Step("Click on 'Close' button on 'Log in' modal")
  public MainPage clickOnCloseButtonOnLogInModal() {
    loginComponent.clickOnButtonClose();

    return this;
  }

  @Step("Verify user is logged in")
  public MainPage verifyUserIsLoggedIn(String username) {
    navBarComponent.verifyUserIsLoggedIn(username);

    return this;
  }

  @Step("Verify user is not logged in")
  public MainPage verifyUserIsNotLoggedIn() {
    navBarComponent.verifyUserIsNotLoggedIn();

    return this;
  }

  @Step("Click 'Ok' in alert dialog")
  public MainPage clickOkInAlertDialog() {
    Selenide.confirm();

    return this;
  }

  @Step("Verify text in alert dialog")
  public MainPage verifyTextInAlertDialog(String text) {
    Alert alert = Selenide.switchTo().alert();
    assertThat(text, is(alert.getText()));

    return this;
  }

  @Step("Log out user")
  public MainPage logOutUser() {
    navBarComponent.clickOnNavBarItem("Log out");

    return this;
  }

  @Step("Open 'Sign up' modal")
  public MainPage openSignUpModal() {
    navBarComponent.clickOnNavBarItem("Sign up");
    signUpComponent.verifySignUpModalIsVisible();

    return this;
  }

  @Step("Verify 'Sign up' modal is visible")
  public MainPage verifySignUpModalIsVisible() {
    signUpComponent.verifySignUpModalIsVisible();

    return this;
  }

  @Step("Verify 'Sign up' modal is not visible")
  public MainPage verifySignUpModalIsNotVisible() {
    signUpComponent.verifySignUpModalIsNotVisible();

    return this;
  }

  @Step("Set username on 'Sign up' modal")
  public MainPage setUsernameOnSignUpModal(String username) {
    signUpComponent.setSignUpUsername(username);

    return this;
  }

  @Step("Set password on 'Sign up' modal")
  public MainPage setPasswordOnSignUpModal(String password) {
    signUpComponent.setSignUpPassword(password);

    return this;
  }

  @Step("Click on 'Sign Up' button on 'Sign up' modal")
  public MainPage clickOnSignUpButtonOnSignUpModal() {
    signUpComponent.clickOnButtonSignUp();

    return this;
  }

  @Step("Click on 'Close' button on 'Sign up' modal")
  public MainPage clickOnCloseButtonOnSignUpModal() {
    signUpComponent.clickOnButtonClose();

    return this;
  }

  @Step("Verify catalog is not empty")
  public MainPage verifyCatalogIsNotEmpty(){
    catalogItems.shouldHave(sizeGreaterThan(0));

    return this;
  }

  @Step("Click on first product img in catalog")
  public MainPage clickOnFirstProductItemImgInCatalog(){
    catalogFirstItemImg.click();

    return this;
  }

  @Step("Click on first product title in catalog")
  public MainPage clickOnFirstProductItemTitleInCatalog(){
    catalogFirstItemTitle.click();

    return this;
  }

}


