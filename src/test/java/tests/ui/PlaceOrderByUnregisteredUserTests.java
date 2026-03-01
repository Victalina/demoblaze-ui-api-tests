package tests.ui;

import helpers.TestData;
import helpers.TestDataFactory;
import models.EntriesResponseModel;
import models.EntryResponseModel;
import org.junit.jupiter.api.*;
import pages.CartPage;
import pages.MainPage;

import static io.qameta.allure.Allure.step;

@Tags({
        @Tag("allUiTests"),
        @Tag("placeOrderUiTests")

})
@DisplayName("Placing order by unregistered user")
public class PlaceOrderByUnregisteredUserTests extends TestBase {

  private final MainPage mainPage = new MainPage();
  private final CartPage cartPage = new CartPage();

  @Test
  @DisplayName("Placing order with one product item in cart by unregistered user")
  void placeOrderOneProductInCartByUnregisteredUserTest() {

    TestData testData = new TestData();

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();
    int selectedId = step("Select product id from catalog", () -> entries[0].getId());

    String cookie = TestDataFactory.newUnregisteredUserCookie();
    String uuidProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidProduct, cookie, selectedId, false);

    cartPage.openCartByDirectLinkWithExistingUnregisteredUsersCookie(cookie)
            .verifyAmountOfProductItemsInCart(1)
            .clickPlaceOrderButton()
            .verifyPlaceOrderModalIsVisible()
            .verifyTotalPriceOnPlaceOrderModal()
            .setNameOnPlaceOrderModal(testData.getName())
            .setCountyOnPlaceOrderModal(testData.getCountry())
            .setCityOnPlaceOrderModal(testData.getCity())
            .setCardOnPlaceOrderModal(testData.getCreditCard())
            .setMonthOnPlaceOrderModal(String.valueOf(testData.getMonth()))
            .setYearOnPlaceOrderModal(String.valueOf(testData.getYear()))
            .clickOnPurchaseButtonOnPlaceOrderModal()
            .verifySuccessfulOrderAlertIsVisible()
            .verifyValueIsNotEmptyInSuccessfulOrderAlert("Id")
            .verifyAmountInSuccessfulOrderAlert()
            .verifyValueInSuccessfulOrderAlert("Card Number", testData.getCreditCard())
            .verifyValueInSuccessfulOrderAlert("Name", testData.getName())
            .clickOnOkButtonOnSuccessfulOrderAlert()
            .verifySuccessfulOrderAlertIsNotVisible();
    mainPage.verifyRedirectToMainPage();
  }

  @Disabled("Known issue: 'Place order modal' is visible after successful order alert is opened")
  @Test
  @DisplayName("Verify 'Place order modal' is closed after successful order alert is opened by unregistered user")
  void verifyPlaceOrderModalIsClosedAfterSuccessfulOrderAlertIsOpenedByUnregisteredUserTest() {

    TestData testData = new TestData();

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();
    int selectedId = step("Select product id from catalog", () -> entries[0].getId());

    String cookie = TestDataFactory.newUnregisteredUserCookie();
    String uuidProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidProduct, cookie, selectedId, false);

    cartPage.openCartByDirectLinkWithExistingUnregisteredUsersCookie(cookie)
            .verifyAmountOfProductItemsInCart(1)
            .clickPlaceOrderButton()
            .verifyPlaceOrderModalIsVisible()
            .verifyTotalPriceOnPlaceOrderModal()
            .setNameOnPlaceOrderModal(testData.getName())
            .setCountyOnPlaceOrderModal(testData.getCountry())
            .setCityOnPlaceOrderModal(testData.getCity())
            .setCardOnPlaceOrderModal(testData.getCreditCard())
            .setMonthOnPlaceOrderModal(String.valueOf(testData.getMonth()))
            .setYearOnPlaceOrderModal(String.valueOf(testData.getYear()))
            .clickOnPurchaseButtonOnPlaceOrderModal()
            .verifyPlaceOrderModalIsNotVisible();

  }

  @Disabled("Known issue: month in successful order displayed as current - 1")
  @Test
  @DisplayName("Verify date is correct in successful order alert by unregistered user")
  void verifyDateIsCorrectInSuccessfulOrderAlertByUnregisteredUserTest() {

    TestData testData = new TestData();

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();
    int selectedId = step("Select product id from catalog", () -> entries[0].getId());

    String cookie = TestDataFactory.newUnregisteredUserCookie();
    String uuidProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidProduct, cookie, selectedId, false);

    cartPage.openCartByDirectLinkWithExistingUnregisteredUsersCookie(cookie)
            .verifyAmountOfProductItemsInCart(1)
            .clickPlaceOrderButton()
            .verifyPlaceOrderModalIsVisible()
            .verifyTotalPriceOnPlaceOrderModal()
            .setNameOnPlaceOrderModal(testData.getName())
            .setCountyOnPlaceOrderModal(testData.getCountry())
            .setCityOnPlaceOrderModal(testData.getCity())
            .setCardOnPlaceOrderModal(testData.getCreditCard())
            .setMonthOnPlaceOrderModal(String.valueOf(testData.getMonth()))
            .setYearOnPlaceOrderModal(String.valueOf(testData.getYear()))
            .clickOnPurchaseButtonOnPlaceOrderModal()
            .verifySuccessfulOrderAlertIsVisible()
            .verifyCurrentDateInSuccessfulOrderAlert();
  }

  @Test
  @DisplayName("Placing order with two product items in cart by unregistered user")
  void placeOrderTwoProductsInCartByUnregisteredUserTest() {

    TestData testData = new TestData();

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();
    int firstSelectedId = step("Select product id from catalog", () -> entries[0].getId());

    String cookie = TestDataFactory.newUnregisteredUserCookie();
    String uuidFirstProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidFirstProduct, cookie, firstSelectedId, false);

    int secondSelectedId = step("Select second product id from catalog", () -> entries[1].getId());
    String uuidSecondProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidSecondProduct, cookie, secondSelectedId, false);

    cartPage.openCartByDirectLinkWithExistingUnregisteredUsersCookie(cookie)
            .verifyAmountOfProductItemsInCart(2)
            .clickPlaceOrderButton()
            .verifyPlaceOrderModalIsVisible()
            .verifyTotalPriceOnPlaceOrderModal()
            .setNameOnPlaceOrderModal(testData.getName())
            .setCountyOnPlaceOrderModal(testData.getCountry())
            .setCityOnPlaceOrderModal(testData.getCity())
            .setCardOnPlaceOrderModal(testData.getCreditCard())
            .setMonthOnPlaceOrderModal(String.valueOf(testData.getMonth()))
            .setYearOnPlaceOrderModal(String.valueOf(testData.getYear()))
            .clickOnPurchaseButtonOnPlaceOrderModal()
            .verifySuccessfulOrderAlertIsVisible()
            .verifyValueIsNotEmptyInSuccessfulOrderAlert("Id")
            .verifyAmountInSuccessfulOrderAlert()
            .verifyValueInSuccessfulOrderAlert("Card Number", testData.getCreditCard())
            .verifyValueInSuccessfulOrderAlert("Name", testData.getName())
            .clickOnOkButtonOnSuccessfulOrderAlert()
            .verifySuccessfulOrderAlertIsNotVisible();
    mainPage.verifyRedirectToMainPage();
  }

  @Test
  @DisplayName("Placing order with only required fields by unregistered user")
  void placeOrderWithOnlyRequiredFieldsByUnregisteredUserTest() {

    TestData testData = new TestData();

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();
    int selectedId = step("Select product id from catalog", () -> entries[0].getId());

    String cookie = TestDataFactory.newUnregisteredUserCookie();
    String uuidProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidProduct, cookie, selectedId, false);

    cartPage.openCartByDirectLinkWithExistingUnregisteredUsersCookie(cookie)
            .verifyAmountOfProductItemsInCart(1)
            .clickPlaceOrderButton()
            .verifyPlaceOrderModalIsVisible()
            .verifyTotalPriceOnPlaceOrderModal()
            .setNameOnPlaceOrderModal(testData.getName())
            .setCardOnPlaceOrderModal(testData.getCreditCard())
            .clickOnPurchaseButtonOnPlaceOrderModal()
            .verifySuccessfulOrderAlertIsVisible()
            .verifyValueIsNotEmptyInSuccessfulOrderAlert("Id")
            .verifyAmountInSuccessfulOrderAlert()
            .verifyValueInSuccessfulOrderAlert("Card Number", testData.getCreditCard())
            .verifyValueInSuccessfulOrderAlert("Name", testData.getName())
            .clickOnOkButtonOnSuccessfulOrderAlert()
            .verifySuccessfulOrderAlertIsNotVisible();
    mainPage.verifyRedirectToMainPage();
  }

  @Test
  @DisplayName("Unsuccessful placing order by unregistered user - clicking 'Close' button in place order modal")
  void unsuccessfulPlaceOrderByUnregisteredUserClickCloseButtonTest() {

    TestData testData = new TestData();

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();
    int selectedId = step("Select product id from catalog", () -> entries[0].getId());

    String cookie = TestDataFactory.newUnregisteredUserCookie();
    String uuidProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidProduct, cookie, selectedId, false);

    cartPage.openCartByDirectLinkWithExistingUnregisteredUsersCookie(cookie)
            .verifyAmountOfProductItemsInCart(1)
            .clickPlaceOrderButton()
            .verifyPlaceOrderModalIsVisible()
            .verifyTotalPriceOnPlaceOrderModal()
            .setNameOnPlaceOrderModal(testData.getName())
            .setCountyOnPlaceOrderModal(testData.getCountry())
            .setCityOnPlaceOrderModal(testData.getCity())
            .setCardOnPlaceOrderModal(testData.getCreditCard())
            .setMonthOnPlaceOrderModal(String.valueOf(testData.getMonth()))
            .setYearOnPlaceOrderModal(String.valueOf(testData.getYear()))
            .clickOnCloseButtonOnPlaceOrderModal()
            .verifyPlaceOrderModalIsNotVisible()
            .verifyAmountOfProductItemsInCart(1)
            .verifyCartTotalPrice();
  }

  @Test
  @DisplayName("Unsuccessful placing order by unregistered user - empty required field 'Name'")
  void unsuccessfulPlaceOrderByUnregisteredUserEmptyRequiredFieldNameTest() {

    TestData testData = new TestData();

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();
    int selectedId = step("Select product id from catalog", () -> entries[0].getId());

    String cookie = TestDataFactory.newUnregisteredUserCookie();
    String uuidProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidProduct, cookie, selectedId, false);

    cartPage.openCartByDirectLinkWithExistingUnregisteredUsersCookie(cookie)
            .verifyAmountOfProductItemsInCart(1)
            .clickPlaceOrderButton()
            .verifyPlaceOrderModalIsVisible()
            .verifyTotalPriceOnPlaceOrderModal()
            .setCountyOnPlaceOrderModal(testData.getCountry())
            .setCityOnPlaceOrderModal(testData.getCity())
            .setCardOnPlaceOrderModal(testData.getCreditCard())
            .setMonthOnPlaceOrderModal(String.valueOf(testData.getMonth()))
            .setYearOnPlaceOrderModal(String.valueOf(testData.getYear()))
            .clickOnPurchaseButtonOnPlaceOrderModal()
            .verifyTextInAlertDialog("Please fill out Name and Creditcard.")
            .clickOkInAlertDialog()
            .verifyPlaceOrderModalIsVisible();
  }

  @Test
  @DisplayName("Unsuccessful placing order by unregistered user - empty required field 'Credit card'")
  void unsuccessfulPlaceOrderByUnregisteredUserEmptyRequiredFieldCreditCardTest() {

    TestData testData = new TestData();

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();
    int selectedId = step("Select product id from catalog", () -> entries[0].getId());

    String cookie = TestDataFactory.newUnregisteredUserCookie();
    String uuidProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidProduct, cookie, selectedId, false);

    cartPage.openCartByDirectLinkWithExistingUnregisteredUsersCookie(cookie)
            .verifyAmountOfProductItemsInCart(1)
            .clickPlaceOrderButton()
            .verifyPlaceOrderModalIsVisible()
            .verifyTotalPriceOnPlaceOrderModal()
            .setNameOnPlaceOrderModal(testData.getName())
            .setCountyOnPlaceOrderModal(testData.getCountry())
            .setCityOnPlaceOrderModal(testData.getCity())
            .setMonthOnPlaceOrderModal(String.valueOf(testData.getMonth()))
            .setYearOnPlaceOrderModal(String.valueOf(testData.getYear()))
            .clickOnPurchaseButtonOnPlaceOrderModal()
            .verifyTextInAlertDialog("Please fill out Name and Creditcard.")
            .clickOkInAlertDialog()
            .verifyPlaceOrderModalIsVisible();
  }
}
