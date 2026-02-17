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
@DisplayName("Deletion item from cart")
public class DeleteItemFromCartTests extends TestBase {

  @Test
  @DisplayName("Deletion single product item from cart")
  void deletionSingleProductItemFromCartTest() {

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

    String idForDeletion = step("Get product id to delete", () -> items[0].getId());

    Response responseDeletion = cartApi.deleteItemFromCart(idForDeletion);
    String massage = responseDeletion.asString();
    step("Verify product deletion message", () ->
            assertThat(massage, containsString("Item deleted.")));

    ViewCartResponseModel responseViewCart = cartApi.viewCartByUser(cookie, false);

    ViewCartItemModel[] itemsAfterDeletion = responseViewCart.getItems();
    step("Verify returned array is empty", () ->
            assertThat(itemsAfterDeletion.length, is(0)));
  }

  @Test
  @DisplayName("Deletion one of two product items from cart")
  void deletionOneOfTwoProductItemsFromCartTest() {

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

    String idForDeletion = step("Get product id to delete", () -> items2[1].getId());

    Response responseDeletion = cartApi.deleteItemFromCart(idForDeletion);
    String massage = responseDeletion.asString();
    step("Verify product deletion message", () ->
            assertThat(massage, containsString("Item deleted.")));

    ViewCartResponseModel responseViewCart = cartApi.viewCartByUser(cookie, false);

    ViewCartItemModel[] itemsAfterDeletion = responseViewCart.getItems();
    step("Verify cart is contained one item", () ->
            assertThat(itemsAfterDeletion.length, is(1)));

    step("Verify second product item has correct values", () -> {
      assertThat(itemsAfterDeletion[0].getId(), is(items2[0].getId()));
      assertThat(itemsAfterDeletion[0].getProdId(), is(items2[0].getProdId()));
      assertThat(itemsAfterDeletion[0].getCookie(), is(items2[0].getCookie()));
    });
  }

  @Test
  @DisplayName("Request to delete product item from cart with missing id should return error message")
  void unsuccessfulDeletionItemFromCartWithMissingProductIdTest() {
    String emptyBody = "{}";

    ErrorMessageModel response = step("Send delete item form cart request with missing id", () ->
            given(requestSpec)
                    .body(emptyBody)
                    .when()
                    .post("/deleteitem")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract()
                    .response().as(ErrorMessageModel.class));

    step("Verify error message in response", () ->
            assertThat(response.getErrorMessage(), is("Bad parameter, missing id")));
  }
}
