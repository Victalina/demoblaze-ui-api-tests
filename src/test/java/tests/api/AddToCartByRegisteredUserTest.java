package tests.api;

import config.TestConfig;
import helpers.TestDataFactory;
import models.*;
import org.junit.jupiter.api.*;

import java.util.Arrays;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static spec.Spec.requestSpec;
import static spec.Spec.responseSpecStatusCode;

@Tags({
        @Tag("allApiTests"),
        @Tag("cartApiTests")

})
@DisplayName("Add product to cart by registered user")
public class AddToCartByRegisteredUserTest extends TestBase {

  private final String login = TestConfig.get("test.user.login");
  private final String password = TestConfig.get("test.user.password");

  @BeforeEach
  void cleanCartIfNeeded() {

    cartApi.ensureCartIsEmptyForRegisteredUser(login,
            password);
  }

  @Test
  @DisplayName("Add one product to cart by registered user")
  void addOneProductToCartByRegisteredUserTest() {

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();

    step("Verify catalog is not empty", () ->
            assertThat("Catalog must contain at least one entry",
                    entries.length, greaterThan(0)));

    int selectedId = step("Select product id from catalog", () -> entries[0].getId());

    String cookie = authApi.getTokenForRegisteredUser(login, password);
    String uuidProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidProduct, cookie, selectedId, true);
    ViewCartResponseModel response = cartApi.viewCartByUser(cookie, true);
    ViewCartItemModel[] items = response.getItems();
    step("Verify cart is contained one item", () ->
            assertThat(
                    items.length, is(1)));
    step("Verify product item has correct values", () -> {
      assertThat(items[0].getId(), is(uuidProduct));
      assertThat(items[0].getProdId(), is(selectedId));
      assertThat(items[0].getCookie(), is(login));
    });
  }

  @Test
  @DisplayName("Add second product to cart by registered user")
  void addSecondProductToCartByRegisteredUserTest() {

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();

    step("Verify catalog is not empty", () ->
            assertThat("Catalog must contain at least one entry",
                    entries.length, greaterThan(0)));

    int firstSelectedId = step("Select first product id from catalog", () -> entries[0].getId());

    String cookie = authApi.getTokenForRegisteredUser(login, password);
    String uuidFirstProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidFirstProduct, cookie, firstSelectedId, true);
    ViewCartResponseModel response = cartApi.viewCartByUser(cookie, true);
    ViewCartItemModel[] items = response.getItems();
    step("Verify cart is contained one item", () ->
            assertThat(
                    items.length, is(1)));

    int secondSelectedId = step("Select second product id from catalog", () -> entries[1].getId());
    String uuidSecondProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidSecondProduct, cookie, secondSelectedId, true);
    ViewCartResponseModel response2 = cartApi.viewCartByUser(cookie, true);
    ViewCartItemModel[] items2 = response2.getItems();
    step("Verify cart is contained two items", () ->
            assertThat(
                    items2.length, is(2)));

    step("Verify second product item is present in cart", () -> {
      ViewCartItemModel addedItem = Arrays.stream(items2)
              .filter(item -> item.getId().equals(uuidSecondProduct))
              .findFirst()
              .orElseThrow(() -> new AssertionError("Second product not found in cart"));

      assertThat(addedItem.getProdId(), is(secondSelectedId));
      assertThat(addedItem.getCookie(), is(login));
    });
  }

  @Test
  @DisplayName("Add second product with the same prod id and different uuid to cart by registered user")
  void addSecondProductWithTheSameProdIdToCartByRegisteredUserTest() {

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();

    step("Verify catalog is not empty", () ->
            assertThat("Catalog must contain at least one entry",
                    entries.length, greaterThan(0)));

    int selectedId = step("Select first product id from catalog", () -> entries[0].getId());

    String cookie = authApi.getTokenForRegisteredUser(login, password);
    String uuidFirstProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidFirstProduct, cookie, selectedId, true);
    ViewCartResponseModel response = cartApi.viewCartByUser(cookie, true);
    ViewCartItemModel[] items = response.getItems();
    step("Verify cart is contained one item", () ->
            assertThat(
                    items.length, is(1)));

    String uuidSecondProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidSecondProduct, cookie, selectedId, true);
    ViewCartResponseModel response2 = cartApi.viewCartByUser(cookie, true);
    ViewCartItemModel[] items2 = response2.getItems();
    step("Verify cart is contained two items", () ->
            assertThat(
                    items2.length, is(2)));

    step("Verify second product item is present in cart", () -> {
      ViewCartItemModel addedItem = Arrays.stream(items2)
              .filter(item -> item.getId().equals(uuidSecondProduct))
              .findFirst()
              .orElseThrow(() -> new AssertionError("Second product not found in cart"));

      assertThat(addedItem.getProdId(), is(selectedId));
      assertThat(addedItem.getCookie(), is(login));
    });
  }

  @Test
  @DisplayName("Add second product with the same uuid and different prod id to cart by registered user")
  void addSecondProductWithTheSameUuidToCartByRegisteredUserTest() {

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();

    step("Verify catalog is not empty", () ->
            assertThat("Catalog must contain at least one entry",
                    entries.length, greaterThan(0)));

    int firstSelectedId = step("Select first product id from catalog", () -> entries[0].getId());

    String cookie = authApi.getTokenForRegisteredUser(login, password);
    String uuidProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidProduct, cookie, firstSelectedId, true);
    ViewCartResponseModel response = cartApi.viewCartByUser(cookie, true);
    ViewCartItemModel[] items = response.getItems();
    step("Verify cart is contained one item", () ->
            assertThat(
                    items.length, is(1)));

    int secondSelectedId = step("Select second product id from catalog", () -> entries[1].getId());

    cartApi.addProductToCart(uuidProduct, cookie, secondSelectedId, true);
    ViewCartResponseModel response2 = cartApi.viewCartByUser(cookie, true);
    ViewCartItemModel[] items2 = response2.getItems();
    step("Verify cart is contained one item", () ->
            assertThat(
                    items2.length, is(1)));

    step("Verify second product item has correct values", () -> {
      assertThat(items2[0].getId(), is(uuidProduct));
      assertThat(items2[0].getProdId(), is(secondSelectedId));
      assertThat(items2[0].getCookie(), is(login));
    });
  }

  @Test
  @DisplayName("Request to add product to cart by registered user with missing cookie should return error message")
  void addProductToCartWithMissingCookieByRegisteredUserTest() {

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();

    step("Verify catalog is not empty", () ->
            assertThat("Catalog must contain at least one entry",
                    entries.length, greaterThan(0)));

    int selectedId = step("Select product id from catalog", () -> entries[0].getId());
    String uuidProduct = TestDataFactory.newCartItemUuid();
    String body = "{\"id\":\"" + uuidProduct + "\",\"prod_id\":" + selectedId + ",\"flag\":true}";

    ErrorMessageModel response = step("Send add to cart request with missing cookie", () ->
            given(requestSpec)
                    .body(body)
                    .when()
                    .post("/addtocart")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract()
                    .response().as(ErrorMessageModel.class));

    step("Verify error message in response", () ->
            assertThat(response.getErrorMessage(), is("Bad parameter, token malformed.")));

  }
}
