package tests.api;

import models.EntryResponseModel;
import models.PaginationRequestModel;
import models.PaginationResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static spec.Spec.requestSpec;
import static spec.Spec.responseSpecStatusCode;

@DisplayName("Catalog pagination")
public class CatalogPaginationTests extends TestBase {

  @Test
  @DisplayName("Get paginating catalog entries")
  void paginatingCatalogEntriesTest() {

    PaginationRequestModel id = new PaginationRequestModel("9");

    PaginationResponseModel response = step("Get catalog entities after id = " + id.getId(), () ->
            given(requestSpec)
                    .when()
                    .body(id)
                    .post("/pagination")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(PaginationResponseModel.class));

    EntryResponseModel[] entries = response.getItems();

    step("Verify paginating catalog is not empty", () ->
            assertThat("Catalog must contain at least one entry",
                    entries.length, greaterThan(0)));
    step("Verify first entity in paginating catalog has id = " + (Integer.parseInt(id.getId()) + 1), () ->
            assertThat(entries[0].getId(), is(Integer.parseInt(id.getId()) + 1)));
  }

  @Test
  @DisplayName("Last evaluated key is equal to last entry id")
  void lastEvaluatedKeyTest() {

    PaginationRequestModel id = new PaginationRequestModel("9");

    PaginationResponseModel response = step("Get catalog entities after id = " + id.getId(), () ->
            given(requestSpec)
                    .when()
                    .body(id)
                    .post("/pagination")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(PaginationResponseModel.class));

    EntryResponseModel[] entries = response.getItems();

    step("Verify paginating catalog is not empty", () ->
            assertThat("Catalog must contain at least one entry",
                    entries.length, greaterThan(0)));
    step("Verify last evaluated key is last entry id", () ->
            assertThat(response.getLastEvaluatedKey().getId(),
                    is(String.valueOf(entries[entries.length - 1].getId()))));
  }

  @Test
  @DisplayName("Scanned count is equal to number of entries")
  void scannedCountTest() {

    PaginationRequestModel id = new PaginationRequestModel("9");

    PaginationResponseModel response = step("Get catalog entities after id = " + id.getId(), () ->
            given(requestSpec)
                    .when()
                    .body(id)
                    .post("/pagination")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(PaginationResponseModel.class));

    EntryResponseModel[] entries = response.getItems();

    step("Verify paginating catalog is not empty", () ->
            assertThat("Catalog must contain at least one entry",
                    entries.length, greaterThan(0)));

    step("Verify scanned count is number of entries", () ->
            assertThat(response.getScannedCount(), is(entries.length)));
  }
}


