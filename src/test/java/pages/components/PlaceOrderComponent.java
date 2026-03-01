package pages.components;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PlaceOrderComponent {
  private final SelenideElement orderModal = $("#orderModal");
  private final SelenideElement orderModalLabel = $("#orderModal #orderModalLabel");
  private final SelenideElement orderModalTotalm = $("#orderModal #totalm");
  private final SelenideElement orderModalNameInput = $("#orderModal #name");
  private final SelenideElement orderModalCountryInput = $("#orderModal #country");
  private final SelenideElement orderModalCityInput = $("#orderModal #city");
  private final SelenideElement orderModalCreditCardInput = $("#orderModal #card");
  private final SelenideElement orderModalMonthInput = $("#orderModal #month");
  private final SelenideElement orderModalYearInput = $("#orderModal #year");
  private final SelenideElement orderModalPurchaseButton = $("#orderModal").$(".modal-footer").$$("button")
          .findBy(text("Purchase"));
  private final SelenideElement orderModalCloseButton = $("#orderModal").$(".modal-footer").$$("button")
          .findBy(text("Close"));
  private final SelenideElement successfulOrderAlert = $(".sweet-alert");
  private final SelenideElement successfulOrderAlertTitle = $(".sweet-alert h2");
  private final SelenideElement successfulOrderAlertText = $(".sweet-alert .text-muted");
  private final SelenideElement successfulOrderAlertOkButton = $(".sweet-alert").$(".sa-confirm-button-container").$(".btn-primary");


  public void verifyOrderModalIsVisible() {

    orderModal.shouldBe(visible);
    orderModalLabel.shouldHave(text("Place order"));
  }

  public void verifyOrderModalIsNotVisible() {
    orderModal.shouldNotBe(visible);
  }

  public void verifyOrderModalTotalPrice(String price) {
    orderModalTotalm.shouldHave(text("Total: " + price));
  }

  public void setOrderModalName(String name) {
    orderModalNameInput.setValue(name);
  }

  public void setOrderModalCountry(String country) {
    orderModalCountryInput.setValue(country);
  }

  public void setOrderModalCity(String city) {
    orderModalCityInput.setValue(city);
  }

  public void setOrderModalCreditCard(String card) {
    orderModalCreditCardInput.setValue(card);
  }

  public void setOrderModalMonth(String month) {
    orderModalMonthInput.setValue(month);
  }

  public void setOrderModalYear(String year) {
    orderModalYearInput.setValue(year);
  }

  public void clickOnButtonPurchase() {
    orderModalPurchaseButton.click();
  }

  public void clickOnButtonClose() {
    orderModalCloseButton.click();
  }

  public void verifySuccessfulOrderAlertIsVisible() {
    successfulOrderAlert.shouldBe(visible);
    successfulOrderAlertTitle.shouldHave(text("Thank you for your purchase!"));
  }

  public void verifySuccessfulOrderAlertIsNotVisible() {
    successfulOrderAlert.shouldNotBe(visible);
  }

  public void verifyValueInSuccessfulOrderAlert(String key, String value) {

    String orderAlertText = successfulOrderAlertText.getText();

    Map<String, String> map = new HashMap<>();
    for (String pair : orderAlertText.split("\n")) {
      String[] kv = pair.split(": ");
      map.put(kv[0], kv[1]);
    }
    assertThat(map.get(key), is(value));
  }

  public void verifyValueInSuccessfulOrderAlertIsNotEmpty(String key) {

    String orderAlertText = successfulOrderAlertText.getText();

    Map<String, String> map = new HashMap<>();
    for (String pair : orderAlertText.split("\n")) {
      String[] kv = pair.split(": ");
      map.put(kv[0], kv[1]);
    }
    assertThat(map.get(key),not(emptyString()));
  }

  public void verifyCurrentDateInSuccessfulOrderAlert() {

    String orderAlertText = successfulOrderAlertText.getText();

    Map<String, String> map = new HashMap<>();
    for (String pair : orderAlertText.split("\n")) {
      String[] kv = pair.split(": ");
      map.put(kv[0], kv[1]);
    }

    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter dft = DateTimeFormatter.ofPattern("d/M/yyyy");

    assertThat(map.get("Date"),is(currentDate.format(dft)));
  }

  public void clickOnOkButtonOnSuccessfulOrderAlert() {
    Selenide.Wait().until(d -> executeJavaScript("return jQuery.active == 0"));
    successfulOrderAlertOkButton.click();
  }
}