package api;

import helpers.TestDataFactory;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.*;

import static io.restassured.RestAssured.given;
import static spec.Spec.requestSpec;
import static spec.Spec.responseSpecStatusCode;

public class CartApi {

  @Step("Send request to view product cart by user")
  public ViewCartResponseModel viewCartByUser(String cookie, boolean flag) {

    ViewCartRequestModel userData = new ViewCartRequestModel(cookie, flag);
    return
            given(requestSpec)
                    .body(userData)
                    .when()
                    .post("/viewcart")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract()
                    .response().as(ViewCartResponseModel.class);
  }

  @Step("Add product to cart")
  public void addProductToCart(String uuidProduct, String cookie, int productId, boolean flag) {

    AddToCartRequestModel addToCartRequest = new AddToCartRequestModel(
            uuidProduct, cookie, productId, flag
    );
    given(requestSpec)
            .body(addToCartRequest)
            .when()
            .post("/addtocart")
            .then()
            .spec(responseSpecStatusCode(200));
  }

  @Step("Add product to cart by product id with uuid generation")
  public void addProductToCartWithUuidGeneration(String cookie, int productId, boolean flag) {

    String uuidProduct = TestDataFactory.newCartItemUuid();
    AddToCartRequestModel addToCartRequest = new AddToCartRequestModel(
            uuidProduct, cookie, productId, flag
    );
    given(requestSpec)
            .body(addToCartRequest)
            .when()
            .post("/addtocart")
            .then()
            .spec(responseSpecStatusCode(200));
  }

  @Step("Delete item from cart")
  public Response deleteItemFromCart(String uuidProduct) {

    DeleteItemFromCartRequestModel item = new DeleteItemFromCartRequestModel(uuidProduct);
    return
            given(requestSpec)
                    .body(item)
                    .when()
                    .post("/deleteitem")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract()
                    .response();
  }

  @Step("Ensure cart is empty for registered user")
  public void ensureCartIsEmptyForRegisteredUser(String login, String password) {
    AuthApi authApi = new AuthApi();
    String token = authApi.getTokenForRegisteredUser(login, password);

    ViewCartResponseModel response = viewCartByUser(token, true);
    ViewCartItemModel[] items = response.getItems();

    for (ViewCartItemModel item : items) {
      Response responseDeletion = deleteItemFromCart(item.getId());
    }

  }

  @Step("Delete cart")
  public Response deleteCart(String cookie) {
    DeleteCartRequestModel body = new DeleteCartRequestModel(cookie);
    return
            given(requestSpec)
                    .body(body)
                    .when()
                    .post("/deletecart")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract()
                    .response();
  }
}
