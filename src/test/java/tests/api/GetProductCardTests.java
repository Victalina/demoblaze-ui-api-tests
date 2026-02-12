package tests.api;

import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static spec.Spec.requestSpec;
import static spec.Spec.responseSpecStatusCode;

@DisplayName("Get product card")
public class GetProductCardTests extends TestBase {

  @Test
  @DisplayName("Get existing product card")
  void getExistingProductCardTest() {

    EntriesResponseModel responseCatalog = step("Send request to get product catalog", () ->
            given(requestSpec)
                    .when()
                    .get("/entries")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(EntriesResponseModel.class));

    EntryResponseModel[] entries = responseCatalog.getItems();

    step("Verify catalog is not empty", () ->
            assertThat("Catalog must contain at least one entry",
                    entries.length, greaterThan(0)));

    ProductCardRequestModel entryId = step("Prepare product card request body", () ->
            new ProductCardRequestModel(String.valueOf(entries[0].getId())));


    ProductCardResponseModel responseProductCard = step("Send request to get product card with id = "
            + entryId.getId(), () ->
            given(requestSpec)
                    .body(entryId)
                    .when()
                    .post("/view")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(ProductCardResponseModel.class));

    step("Verify product card has correct structure", () -> {
      assertThat(responseProductCard.getCat(), not(emptyString()));
      assertThat(responseProductCard.getDesc(), not(emptyString()));
      assertThat(responseProductCard.getId(), is(Integer.parseInt(entryId.getId())));
      assertThat(responseProductCard.getImg(), not(emptyString()));
      assertThat(responseProductCard.getPrice(), is(greaterThan(0.0)));
      assertThat(responseProductCard.getTitle(), not(emptyString()));
    });
  }

  @Test
  @DisplayName("Get non-existent product card")
  void getNonExistentProductCardTest() {

    String nonExistentId = "999999";
    ProductCardRequestModel entryId = new ProductCardRequestModel(nonExistentId);

    ErrorMessageModel response = step("Send request to get product card with id = " + entryId.getId(), () ->
            given(requestSpec)
                    .body(entryId)
                    .when()
                    .post("/view")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(ErrorMessageModel.class));

    step("Verify error in response", () ->
            assertThat(response.getErrorMessage(), is("Not found.")));
  }
}
