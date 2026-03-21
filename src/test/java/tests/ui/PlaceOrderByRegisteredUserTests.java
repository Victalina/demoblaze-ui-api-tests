package tests.ui;

import context.TestUserContext;
import context.User;
import extensions.WithLogin;
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
@DisplayName("Placing order by registered user")
public class PlaceOrderByRegisteredUserTests extends TestBase {

  @BeforeEach
  void cleanCartIfNeeded() {

    User user = TestUserContext.get();

    if (user != null) {
      cartApi.ensureCartIsEmptyForRegisteredUser(
              user.getLogin(),
              user.getPassword());
    }
  }

  private final MainPage mainPage = new MainPage();
  private final CartPage cartPage = new CartPage();

  @Test
  @DisplayName("Placing order with one product item in cart by registered user")
  @WithLogin
  void placeOrderOneProductInCartByRegisteredUserTest() {

    TestData testData = new TestData();

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();
    int selectedId = step("Select product id from catalog", () -> entries[0].getId());

    String cookie = authApi.getTokenForRegisteredUser(TestUserContext.get().getLogin(), TestUserContext.get().getPassword());
    String uuidProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidProduct, cookie, selectedId, true);

    cartPage.openCartByDirectLink()
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
  @DisplayName("Verify 'Place order modal' is closed after successful order alert is opened by registered user")
  @WithLogin
  void verifyPlaceOrderModalIsClosedAfterSuccessfulOrderAlertIsOpenedByRegisteredUserTest() {

    TestData testData = new TestData();

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();
    int selectedId = step("Select product id from catalog", () -> entries[0].getId());

    String cookie = authApi.getTokenForRegisteredUser(TestUserContext.get().getLogin(), TestUserContext.get().getPassword());
    String uuidProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidProduct, cookie, selectedId, true);

    cartPage.openCartByDirectLink()
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
  @DisplayName("Verify date is correct in successful order alert by registered user")
  @WithLogin
  void verifyDateIsCorrectInSuccessfulOrderAlertByRegisteredUserTest() {

    TestData testData = new TestData();

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();
    int selectedId = step("Select product id from catalog", () -> entries[0].getId());

    String cookie = authApi.getTokenForRegisteredUser(TestUserContext.get().getLogin(), TestUserContext.get().getPassword());
    String uuidProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidProduct, cookie, selectedId, true);

    cartPage.openCartByDirectLink()
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
  @DisplayName("Placing order with two product items in cart by registered user")
  @WithLogin
  void placeOrderTwoProductsInCartByRegisteredUserTest() {

    TestData testData = new TestData();

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();
    int firstSelectedId = step("Select product id from catalog", () -> entries[0].getId());

    String cookie = authApi.getTokenForRegisteredUser(TestUserContext.get().getLogin(), TestUserContext.get().getPassword());
    String uuidFirstProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidFirstProduct, cookie, firstSelectedId, true);

    int secondSelectedId = step("Select second product id from catalog", () -> entries[1].getId());
    String uuidSecondProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidSecondProduct, cookie, secondSelectedId, true);

    cartPage.openCartByDirectLink()
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
  @DisplayName("Placing order with only required fields by registered user")
  @WithLogin
  void placeOrderWithOnlyRequiredFieldsByRegisteredUserTest() {

    TestData testData = new TestData();

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();
    int selectedId = step("Select product id from catalog", () -> entries[0].getId());

    String cookie = authApi.getTokenForRegisteredUser(TestUserContext.get().getLogin(), TestUserContext.get().getPassword());
    String uuidProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidProduct, cookie, selectedId, true);

    cartPage.openCartByDirectLink()
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
  @DisplayName("Unsuccessful placing order by registered user - clicking 'Close' button in place order modal")
  @WithLogin
  void unsuccessfulPlaceOrderByRegisteredUserClickCloseButtonTest() {

    TestData testData = new TestData();

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();
    int selectedId = step("Select product id from catalog", () -> entries[0].getId());

    String cookie = authApi.getTokenForRegisteredUser(TestUserContext.get().getLogin(), TestUserContext.get().getPassword());
    String uuidProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidProduct, cookie, selectedId, true);

    cartPage.openCartByDirectLink()
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
  @DisplayName("Unsuccessful placing order by registered user - empty required field 'Name'")
  @WithLogin
  void unsuccessfulPlaceOrderByRegisteredUserEmptyRequiredFieldNameTest() {

    TestData testData = new TestData();

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();
    int selectedId = step("Select product id from catalog", () -> entries[0].getId());

    String cookie = authApi.getTokenForRegisteredUser(TestUserContext.get().getLogin(), TestUserContext.get().getPassword());
    String uuidProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidProduct, cookie, selectedId, true);

    cartPage.openCartByDirectLink()
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
  @DisplayName("Unsuccessful placing order by registered user - empty required field 'Credit card'")
  @WithLogin
  void unsuccessfulPlaceOrderByRegisteredUserEmptyRequiredFieldCreditCardTest() {

    TestData testData = new TestData();

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();
    int selectedId = step("Select product id from catalog", () -> entries[0].getId());

    String cookie = authApi.getTokenForRegisteredUser(TestUserContext.get().getLogin(), TestUserContext.get().getPassword());
    String uuidProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidProduct, cookie, selectedId, true);

    cartPage.openCartByDirectLink()
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
