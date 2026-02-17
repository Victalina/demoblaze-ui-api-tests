package tests.api;

import config.TestConfig;
import helpers.TestDataFactory;
import io.restassured.response.Response;
import models.*;
import org.junit.jupiter.api.*;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

@Tags({
        @Tag("allApiTests"),
        @Tag("cartApiTests")

})
@DisplayName("Deletion cart by registered user")
public class DeleteCartByRegisteredUserTests extends TestBase {

  private final String login = TestConfig.get("test.user.login");
  private final String password = TestConfig.get("test.user.password");

  @BeforeEach
  void cleanCartIfNeeded() {

    cartApi.ensureCartIsEmptyForRegisteredUser(login,
            password);
  }

  @Test
  @DisplayName("Deletion cart with single product item by registered user")
  void deletionCartWithSingleProductItemByRegisteredUserTest() {

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

    Response responseDeletionCart = cartApi.deleteCart(login);

    step("Verify deletion message is returned", () -> {
      String responseMessage = responseDeletionCart.asString();
      assertThat(responseMessage, containsString("Item deleted."));
    });

    ViewCartResponseModel responseViewCart = cartApi.viewCartByUser(cookie, true);

    ViewCartItemModel[] itemsAfterDeletion = responseViewCart.getItems();
    step("Verify returned array is empty", () ->
            assertThat(itemsAfterDeletion.length, is(0)));
  }

  @Test
  @DisplayName("Deletion two product items from cart by registered user")
  void deletionCartWithTwoProductItemsByRegisteredUserTest() {

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();

    step("Verify catalog is not empty", () ->
            assertThat("Catalog must contain at least one entry",
                    entries.length, greaterThan(0)));

    int firstSelectedId = step("Select product id from catalog", () -> entries[0].getId());

    String cookie = authApi.getTokenForRegisteredUser(login, password);
    String uuidFirstProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidFirstProduct, cookie, firstSelectedId, false);
    ViewCartResponseModel response = cartApi.viewCartByUser(cookie, false);
    ViewCartItemModel[] items = response.getItems();
    step("Verify cart is contained one item", () ->
            assertThat(
                    items.length, is(1)));

    int secondSelectedId = step("Select second product id from catalog", () -> entries[1].getId());
    String uuidSecondProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidSecondProduct, cookie, secondSelectedId, false);
    ViewCartResponseModel response2 = cartApi.viewCartByUser(cookie, false);
    ViewCartItemModel[] items2 = response2.getItems();
    step("Verify cart is contained two items", () ->
            assertThat(
                    items2.length, is(2)));

    Response responseDeletionCart = cartApi.deleteCart(login);

    step("Verify deletion message is returned", () -> {
      String responseMessage = responseDeletionCart.asString();
      assertThat(responseMessage, containsString("Item deleted."));
    });

    ViewCartResponseModel responseViewCart = cartApi.viewCartByUser(cookie, true);

    ViewCartItemModel[] itemsAfterDeletion = responseViewCart.getItems();
    step("Verify returned array is empty", () ->
            assertThat(itemsAfterDeletion.length, is(0)));
  }

  @Test
  @DisplayName("Deletion emptied cart by registered user")
  void deletionEmptiedCartByRegisteredUserTest() {

    String cookie = authApi.getTokenForRegisteredUser(login, password);
    Response responseDeletionCart = cartApi.deleteCart(login);

    step("Verify deletion message is returned", () -> {
      String responseMessage = responseDeletionCart.asString();
      assertThat(responseMessage, containsString("Item deleted."));
    });

    ViewCartResponseModel responseViewCart = cartApi.viewCartByUser(cookie, true);

    ViewCartItemModel[] itemsAfterDeletion = responseViewCart.getItems();
    step("Verify returned array is empty", () ->
            assertThat(itemsAfterDeletion.length, is(0)));
  }
}
