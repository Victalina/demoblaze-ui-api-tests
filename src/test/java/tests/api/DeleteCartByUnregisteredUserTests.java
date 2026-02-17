package tests.api;

import helpers.TestDataFactory;
import io.restassured.response.Response;
import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static spec.Spec.requestSpec;
import static spec.Spec.responseSpecStatusCode;

@Tags({
        @Tag("all_api_tests"),
        @Tag("cart_api_tests")

})
@DisplayName("Deletion cart by unregistered user")
public class DeleteCartByUnregisteredUserTests extends TestBase {

  @Test
  @DisplayName("Deletion cart with single product item by registered user")
  void deletionCartWithSingleProductItemByRegisteredUserTest() {

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();

    step("Verify catalog is not empty", () ->
            assertThat("Catalog must contain at least one entry",
                    entries.length, greaterThan(0)));

    int selectedId = step("Select product id from catalog", () -> entries[0].getId());

    String cookie = TestDataFactory.newUnregisteredUserCookie();
    String uuidProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidProduct, cookie, selectedId, false);
    ViewCartResponseModel response = cartApi.viewCartByUser(cookie, false);
    ViewCartItemModel[] items = response.getItems();
    step("Verify cart is contained one item", () ->
            assertThat(
                    items.length, is(1)));

    Response responseDeletionCart = cartApi.deleteCart(cookie);

    step("Verify deletion message is returned", () -> {
      String responseMessage = responseDeletionCart.asString();
      assertThat(responseMessage, containsString("Item deleted."));
    });

    ViewCartResponseModel responseViewCart = cartApi.viewCartByUser(cookie, false);

    ViewCartItemModel[] itemsAfterDeletion = responseViewCart.getItems();
    step("Verify returned array is empty", () ->
            assertThat(itemsAfterDeletion.length, is(0)));
  }

  @Test
  @DisplayName("Deletion two product items from cart by Unregistered user")
  void deletionCartWithTwoProductItemsByUnregisteredUserTest() {

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();

    step("Verify catalog is not empty", () ->
            assertThat("Catalog must contain at least one entry",
                    entries.length, greaterThan(0)));

    int firstSelectedId = step("Select product id from catalog", () -> entries[0].getId());

    String cookie = TestDataFactory.newUnregisteredUserCookie();
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

    Response responseDeletionCart = cartApi.deleteCart(cookie);

    step("Verify deletion message is returned", () -> {
      String responseMessage = responseDeletionCart.asString();
      assertThat(responseMessage, containsString("Item deleted."));
    });

    ViewCartResponseModel responseViewCart = cartApi.viewCartByUser(cookie, false);

    ViewCartItemModel[] itemsAfterDeletion = responseViewCart.getItems();
    step("Verify returned array is empty", () ->
            assertThat(itemsAfterDeletion.length, is(0)));
  }

  @Test
  @DisplayName("Deletion empty cart by unregistered user")
  void deletionEmptyCartByUnregisteredUserTest() {

    String cookie = TestDataFactory.newUnregisteredUserCookie();
    Response responseDeletionCart = cartApi.deleteCart(cookie);

    step("Verify deletion message is returned", () -> {
      String responseMessage = responseDeletionCart.asString();
      assertThat(responseMessage, containsString("Item deleted."));
    });

    ViewCartResponseModel responseViewCart = cartApi.viewCartByUser(cookie, false);

    ViewCartItemModel[] itemsAfterDeletion = responseViewCart.getItems();
    step("Verify returned array is empty", () ->
            assertThat(itemsAfterDeletion.length, is(0)));
  }

  @Test
  @DisplayName("Request to delete cart with missing cookie should return error message")
  void unsuccessfulDeletionCartWithMissingCookieTest() {
    String emptyBody = "{}";

    ErrorMessageModel response = step("Send delete cart request with missing cookie", () ->
            given(requestSpec)
                    .body(emptyBody)
                    .when()
                    .post("/deletecart")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract()
                    .response().as(ErrorMessageModel.class));

    step("Verify error message in response", () ->
            assertThat(response.getErrorMessage(), is("Bad parameter, missing cookie.")));
  }
}
