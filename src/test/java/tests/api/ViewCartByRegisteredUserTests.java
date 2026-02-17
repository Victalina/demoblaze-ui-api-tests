package tests.api;

import config.TestConfig;
import models.ErrorMessageModel;
import models.ViewCartItemModel;
import models.ViewCartRequestModel;
import models.ViewCartResponseModel;
import org.junit.jupiter.api.*;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static spec.Spec.requestSpec;
import static spec.Spec.responseSpecStatusCode;

@Tags({
        @Tag("allApiTests"),
        @Tag("cartApiTests")

})
@DisplayName("View cart by registered user")
public class ViewCartByRegisteredUserTests extends TestBase {

  private final String login = TestConfig.get("test.user.login");
  private final String password = TestConfig.get("test.user.password");

  @BeforeEach
  void cleanCartIfNeeded() {

    cartApi.ensureCartIsEmptyForRegisteredUser(login,
            password);
  }

  @Test
  @DisplayName("Emptied cart should return empty array")
  void viewEmptiedCartByRegisteredUserTest() {

    String token = authApi.getTokenForRegisteredUser(login,
            password);
    ViewCartResponseModel response = cartApi.viewCartByUser(token, true);

    ViewCartItemModel[] items = response.getItems();

    step("Verify returned array is empty", () ->
            assertThat(items.length, is(0)));
  }

  @Test
  @DisplayName("View cart request with incorrect token should return error message")
  void viewCartByRegisteredUserWithIncorrectTokenTest() {
    String token = "dmlja3ktdGVzd";
    ViewCartRequestModel request = new ViewCartRequestModel(token, true);

    ErrorMessageModel response = step("Send view cart request with missing cookie", () ->
            given(requestSpec)
                    .body(request)
                    .when()
                    .post("/viewcart")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract()
                    .response().as(ErrorMessageModel.class));

    step("Verify error message in response", () ->
            assertThat(response.getErrorMessage(), is("Bad parameter, token malformed.")));
  }

  @Disabled("Status code: 500 Internal Server Error")
  @Test
  @DisplayName("View cart request with empty cookie should return empty array")
  void viewCartByRegisteredUserWithEmptyCookieTest() {
    String cookie = "";
    ViewCartResponseModel response = cartApi.viewCartByUser(cookie, true);

    ViewCartItemModel[] items = response.getItems();
    step("Verify returned array is empty", () ->
            assertThat(items.length, is(0)));
  }

  @Test
  @DisplayName("View cart request with missing cookie should return error message")
  void viewCartByRegisteredUserWithMissingCookieTest() {

    ErrorMessageModel response = step("Send view cart request with missing cookie", () ->
            given(requestSpec)
                    .body("{\"flag\":true}")
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

