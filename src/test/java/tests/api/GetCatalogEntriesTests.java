package tests.api;

import models.EntriesResponseModel;
import models.EntryResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static spec.Spec.requestSpec;
import static spec.Spec.responseSpecStatusCode;

import static org.hamcrest.MatcherAssert.assertThat;

@DisplayName("Get catalog entries")
public class GetCatalogEntriesTests extends TestBase {

  @Test
  @DisplayName("Get catalog entries returns non-empty array")
  @Tag("smoke")
  void getCatalogEntriesReturnsNonEmptyArrayTest() {

    EntriesResponseModel response = step("Send request to get product catalog", () ->
            given(requestSpec)
                    .when()
                    .get("/entries")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(EntriesResponseModel.class));

    EntryResponseModel[] entries = response.getItems();

    step("Verify catalog is not empty", () ->
            assertThat(entries.length, is(greaterThan(0))));
  }

  @Test
  @DisplayName("Catalog entry has correct structure")
  void getCatalogEntryHasCorrectStructureTest() {

    EntriesResponseModel response = step("Send request to get product catalog", () ->
            given(requestSpec)
                    .when()
                    .get("/entries")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(EntriesResponseModel.class));

    EntryResponseModel[] entries = response.getItems();

    step("Verify catalog is not empty", () ->
            assertThat("Catalog must contain at least one entry",
                    entries.length, greaterThan(0)));

    EntryResponseModel randomEntry = entries[0];

    step("Verify random catalog entry has correct structure", () -> {
      assertThat(randomEntry.getCat(), not(emptyString()));
      assertThat(randomEntry.getDesc(), not(emptyString()));
      assertThat(randomEntry.getId(), notNullValue());
      assertThat(randomEntry.getImg(), not(emptyString()));
      assertThat(randomEntry.getPrice(), is(greaterThan(0.0)));
      assertThat(randomEntry.getTitle(), not(emptyString()));
    });
  }

  @Test
  @DisplayName("Last evaluated key is equal to last entry id")
  void getCatalogLastEvaluatedKeyTest() {

    EntriesResponseModel response = step("Send request to get product catalog", () ->
            given(requestSpec)
                    .when()
                    .get("/entries")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(EntriesResponseModel.class));

    EntryResponseModel[] entries = response.getItems();

    step("Verify catalog is not empty", () ->
            assertThat("Catalog must contain at least one entry",
                    entries.length, greaterThan(0)));

    step("Verify last evaluated key is last entry id", () ->
            assertThat(response.getLastEvaluatedKey().getId(),
                    is(String.valueOf(entries[entries.length - 1].getId()))));
  }
}


