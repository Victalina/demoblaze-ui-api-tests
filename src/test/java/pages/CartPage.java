package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Cookie;
import pages.components.PlaceOrderComponent;


import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class CartPage {

  private final SelenideElement cartTitle = $("#page-wrapper h2");
  private final SelenideElement cardTotalTitle = $(".col-lg-1").$("h2");
  private final SelenideElement cardTotalPrice = $(".col-lg-1").$("#totalp");
  private final SelenideElement cartTableHeadPic = $(".table thead").$$("th").first();
  private final SelenideElement cartTableHeadTitle = $(".table thead").$$("th").get(1);
  private final SelenideElement cartTableHeadPrice = $(".table thead").$$("th").get(2);
  private final SelenideElement cartTableHeadX = $(".table thead").$$("th").get(3);
  private final SelenideElement cartTableBody = $(".table").$("#tbodyid");
  private final ElementsCollection productsInCart = $(".table").$("#tbodyid").$$("tr");
  private final SelenideElement placeOrderButton = $(".col-lg-1").$(byText("Place Order"));

  private final PlaceOrderComponent placeOrderComponent = new PlaceOrderComponent();


  @Step("Open cart by direct link")
  public CartPage openCartByDirectLink() {

    open("/cart.html");

    return this;
  }

  @Step("Open cart by direct link with existing unregistered user's cookie")
  public CartPage openCartByDirectLinkWithExistingUnregisteredUsersCookie(String cookie) {

    open("/cart.html");
    String uuid = cookie.replace("user=", "");
    getWebDriver().manage().addCookie(new Cookie("user", uuid));
    refresh();

    return this;
  }

  @Step("Verify cart title")
  public CartPage verifyCartTitle(String title) {

    cartTitle.shouldHave(text(title));

    return this;
  }

  @Step("Verify titles in cart table")
  public CartPage verifyTitlesInCartTable(String firstTitle, String secondTitle, String thirdTitle, String fourthTitle) {

    cartTableHeadPic.shouldHave(text(firstTitle));
    cartTableHeadTitle.shouldHave(text(secondTitle));
    cartTableHeadPrice.shouldHave(text(thirdTitle));
    cartTableHeadX.shouldHave(text(fourthTitle));

    return this;
  }

  @Step("Verify cart total title")
  public CartPage verifyCartTotalTitle(String totalTitle) {

    cardTotalTitle.shouldHave(text(totalTitle));

    return this;
  }

  @Step("Verify cart total price is empty")
  public CartPage verifyCartTotalPriceIsEmpty() {

    Selenide.Wait().until(d -> executeJavaScript("return jQuery.active == 0"));
    cardTotalPrice.shouldBe(empty);

    return this;
  }

  @Step("Verify cart total price")
  public CartPage verifyCartTotalPrice() {

    int price = 0;
    for (SelenideElement item : productsInCart) {
      price += Integer.parseInt(item.$$("td").get(2).getText());
    }

    cardTotalPrice.shouldHave(text(String.valueOf(price)));

    return this;
  }

  @Step("Verify cart table body is empty")
  public CartPage verifyCartIsEmpty() {

    Selenide.Wait().until(d -> executeJavaScript("return jQuery.active == 0"));
    cartTableBody.shouldBe(empty);

    return this;
  }

  @Step("Verify amount product items in cart")
  public CartPage verifyAmountOfProductItemsInCart(int amount) {

    productsInCart.shouldHave(size(amount));

    return this;
  }

  @Step("Verify product title in cart")
  public CartPage verifyProductTitleInCart(String title) {

    List<String> allTitles = new ArrayList<>();

    for (SelenideElement item : productsInCart) {
      allTitles.add(item.$$("td").get(1).getText());
    }

    allTitles.stream()
            .filter(t -> t.equals(title))
            .findFirst()
            .orElseThrow(() -> new AssertionError("Title for product not found in cart"));

    return this;
  }

  @Step("Verify product price in cart")
  public CartPage verifyProductPriceInCart(String price) {

    List<String> allPrices = new ArrayList<>();

    for (SelenideElement item : productsInCart) {
      allPrices.add(item.$$("td").get(2).getText());
    }

    allPrices.stream()
            .filter(t -> t.equals(price))
            .findFirst()
            .orElseThrow(() -> new AssertionError("Price for product not found in cart"));

    return this;
  }

  @Step("Click on 'Delete' button for product in cart")
  public CartPage clickOnDeleteButtonForProductInCart(int productNumber) {
    productsInCart.get(productNumber - 1).$$("td").get(3).$(byText("Delete")).click();

    return this;
  }

  @Step("Click on 'Delete' button for product in cart found by title")
  public CartPage clickOnDeleteButtonForProductInCartFoundByTitle(String title) {

    boolean found = false;

    for (SelenideElement item : productsInCart) {
      String titleInCart = item.$$("td").get(1).getText();

      if (titleInCart.equals(title)) {
        item.$$("td").get(3).$(byText("Delete")).click();
        found = true;
        break;
      }
    }

    if (!found) {
      throw new AssertionError("Title for product not found in cart");
    }

    return this;
  }

  @Step("Get all product titles in cart")
  public List<String> getAllProductTitlesInCart() {

      List<String> allTitles = new ArrayList<>();

      for (SelenideElement item : productsInCart) {
        allTitles.add(item.$$("td").get(1).getText());
      }

      return allTitles;
  }

  @Step("Get all product prices in cart")
  public List<String> getAllProductPricesInCart() {

    List<String> allPrices = new ArrayList<>();

    for (SelenideElement item : productsInCart) {
      allPrices.add(item.$$("td").get(2).getText());
    }

    return allPrices;
  }

  @Step("Click 'Place order' button")
  public CartPage clickPlaceOrderButton() {
    placeOrderButton.click();
    placeOrderComponent.verifyOrderModalIsVisible();

    return this;
  }

  @Step("Verify 'Place order' modal is visible")
  public CartPage verifyPlaceOrderModalIsVisible() {
    placeOrderComponent.verifyOrderModalIsVisible();

    return this;
  }

  @Step("Verify 'Place order' modal is not visible")
  public CartPage verifyPlaceOrderModalIsNotVisible() {
    placeOrderComponent.verifyOrderModalIsNotVisible();

    return this;
  }

  @Step("Verify total price on 'Place Order' modal")
  public CartPage verifyTotalPriceOnPlaceOrderModal() {

    int price = 0;
    for (SelenideElement item : productsInCart) {
      price += Integer.parseInt(item.$$("td").get(2).getText());
    }

    placeOrderComponent.verifyOrderModalTotalPrice(String.valueOf(price));

    return this;
  }

  @Step("Set name on 'Place Order' modal")
  public CartPage setNameOnPlaceOrderModal(String name) {
    placeOrderComponent.setOrderModalName(name);

    return this;
  }

  @Step("Set country on 'Place Order' modal")
  public CartPage setCountyOnPlaceOrderModal(String country) {
    placeOrderComponent.setOrderModalCountry(country);

    return this;
  }

  @Step("Set city on 'Place Order' modal")
  public CartPage setCityOnPlaceOrderModal(String city) {
    placeOrderComponent.setOrderModalCity(city);

    return this;
  }

  @Step("Set credit card on 'Place Order' modal")
  public CartPage setCardOnPlaceOrderModal(String card) {
    placeOrderComponent.setOrderModalCreditCard(card);

    return this;
  }

  @Step("Set month on 'Place Order' modal")
  public CartPage setMonthOnPlaceOrderModal(String month) {
    placeOrderComponent.setOrderModalMonth(month);

    return this;
  }

  @Step("Set year on 'Place Order' modal")
  public CartPage setYearOnPlaceOrderModal(String year) {
    placeOrderComponent.setOrderModalYear(year);

    return this;
  }

  @Step("Click on 'Purchase' button on 'Place Order' modal")
  public CartPage clickOnPurchaseButtonOnPlaceOrderModal() {
    placeOrderComponent.clickOnButtonPurchase();

    return this;
  }

  @Step("Click on 'Close' button on 'Place Order' modal")
  public CartPage clickOnCloseButtonOnPlaceOrderModal() {
    placeOrderComponent.clickOnButtonClose();

    return this;
  }

  @Step("Verify successful order alert is visible")
  public CartPage verifySuccessfulOrderAlertIsVisible() {
    placeOrderComponent.verifySuccessfulOrderAlertIsVisible();

    return this;
  }

  @Step("Verify successful order alert is not visible")
  public CartPage verifySuccessfulOrderAlertIsNotVisible() {
    placeOrderComponent.verifySuccessfulOrderAlertIsNotVisible();

    return this;
  }

  @Step("Verify value in successful order alert")
  public CartPage verifyValueInSuccessfulOrderAlert(String key, String value) {
    placeOrderComponent.verifyValueInSuccessfulOrderAlert(key, value);

    return this;
  }

  @Step("Verify amount in successful order alert")
  public CartPage verifyAmountInSuccessfulOrderAlert() {

    int price = 0;
    for (SelenideElement item : productsInCart) {
      price += Integer.parseInt(item.$$("td").get(2).getText());
    }

    placeOrderComponent.verifyValueInSuccessfulOrderAlert("Amount", price + " USD");

    return this;
  }

  @Step("Verify value in not empty in successful order alert")
  public CartPage verifyValueIsNotEmptyInSuccessfulOrderAlert(String key) {
    placeOrderComponent.verifyValueInSuccessfulOrderAlertIsNotEmpty(key);

    return this;
  }

  @Step("Verify current date in successful order alert")
  public CartPage verifyCurrentDateInSuccessfulOrderAlert(){
    placeOrderComponent.verifyCurrentDateInSuccessfulOrderAlert();

    return this;
  }

  @Step("Click on 'Ok' button on successful order alert")
  public CartPage clickOnOkButtonOnSuccessfulOrderAlert(){
    placeOrderComponent.clickOnOkButtonOnSuccessfulOrderAlert();

    return this;
  }

  @Step("Click 'Ok' in alert dialog")
  public CartPage clickOkInAlertDialog() {
    Selenide.confirm();

    return this;
  }

  @Step("Verify text in alert dialog")
  public CartPage verifyTextInAlertDialog(String text) {
    Alert alert = Selenide.switchTo().alert();
    assertThat(alert.getText(), containsString(text));

    return this;
  }
}
