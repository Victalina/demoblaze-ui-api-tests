package tests.api;

import models.EntryResponseModel;
import models.PaginationResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.qameta.allure.Allure.step;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DisplayName("Catalog pagination")
public class CatalogPaginationTests extends TestBase {

  @Test
  @DisplayName("Get paginating catalog entries")
  void paginatingCatalogEntriesTest() {

    int selectedId = 9;

    PaginationResponseModel response = catalogApi.getPaginatingCatalogEntriesAfterId(String.valueOf(selectedId));
    EntryResponseModel[] entries = response.getItems();

    step("Verify paginating catalog is not empty", () ->
            assertThat("Catalog must contain at least one entry",
                    entries.length, greaterThan(0)));
    step("Verify first entry in paginating catalog has id = " + (selectedId + 1), () ->
            assertThat(entries[0].getId(), is(selectedId + 1)));
  }

  @Test
  @DisplayName("Last evaluated key is equal to the last entry id")
  void lastEvaluatedKeyTest() {

    PaginationResponseModel response = catalogApi.getPaginatingCatalogEntriesAfterId("9");
    EntryResponseModel[] entries = response.getItems();

    step("Verify paginating catalog is not empty", () ->
            assertThat("Catalog must contain at least one entry",
                    entries.length, greaterThan(0)));
    step("Verify last evaluated key is the last entry id", () ->
            assertThat(response.getLastEvaluatedKey().getId(),
                    is(String.valueOf(entries[entries.length - 1].getId()))));
  }

  @Test
  @DisplayName("Scanned count is equal to number of entries")
  void scannedCountTest() {

    PaginationResponseModel response = catalogApi.getPaginatingCatalogEntriesAfterId("9");
    EntryResponseModel[] entries = response.getItems();

    step("Verify paginating catalog is not empty", () ->
            assertThat("Catalog must contain at least one entry",
                    entries.length, greaterThan(0)));

    step("Verify scanned count is number of entries", () ->
            assertThat(response.getScannedCount(), is(entries.length)));
  }
}


