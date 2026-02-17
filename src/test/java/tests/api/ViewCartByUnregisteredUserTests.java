package tests.api;

import helpers.TestDataFactory;
import models.ErrorMessageModel;
import models.ViewCartItemModel;
import models.ViewCartResponseModel;
import org.junit.jupiter.api.*;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static spec.Spec.requestSpec;
import static spec.Spec.responseSpecStatusCode;

@Tags({
        @Tag("all_api_tests"),
        @Tag("cart_api_tests")

})
@DisplayName("View cart by unregistered user")
public class ViewCartByUnregisteredUserTests extends TestBase {

  @Test
  @DisplayName("Product cart should be empty for new unregistered user")
  void viewCartByNewUnregisteredUserTest() {
    String cookie = TestDataFactory.newUnregisteredUserCookie();
    ViewCartResponseModel response = cartApi.viewCartByUser(cookie, false);

    ViewCartItemModel[] items = response.getItems();
    step("Verify returned array is empty", () ->
            assertThat(items.length, is(0)));
  }

  @Test
  @DisplayName("View cart request with incorrect cookie should return empty array")
  void viewCartByUnregisteredUserWithIncorrectCookieTest() {
    String cookie = "user=75fd60ac";
    ViewCartResponseModel response = cartApi.viewCartByUser(cookie, false);

    ViewCartItemModel[] items = response.getItems();
    step("Verify returned array is empty", () ->
            assertThat(items.length, is(0)));
  }

  @Disabled("View cart request with empty cookie returns non-empty array")
  @Test
  @DisplayName("View cart request with empty cookie should return empty array")
  void viewCartByUnregisteredUserWithEmptyCookieTest() {
    String cookie = "";
    ViewCartResponseModel response = cartApi.viewCartByUser(cookie, false);

    ViewCartItemModel[] items = response.getItems();
    step("Verify returned array is empty", () ->
            assertThat(items.length, is(0)));
  }

  @Test
  @DisplayName("View cart request with missing cookie should return error message")
  void viewCartByUnregisteredUserWithMissingCookieTest() {

    ErrorMessageModel response = step("Send view cart request with missing cookie", () ->
            given(requestSpec)
                    .body("{\"flag\":false}")
                    .when()
                    .post("/viewcart")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract()
                    .response().as(ErrorMessageModel.class));

    step("Verify error message in response", () ->
            assertThat(response.getErrorMessage(), is("Bad parameter, missing cookie")));
  }
}

