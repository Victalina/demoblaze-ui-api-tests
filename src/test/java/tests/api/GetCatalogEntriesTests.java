package tests.api;

import models.EntriesResponseModel;
import models.EntryResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.Matchers.*;

import static org.hamcrest.MatcherAssert.assertThat;

@Tags({
        @Tag("all_api_tests"),
        @Tag("catalog_api_tests")

})
@DisplayName("Get catalog entries")
public class GetCatalogEntriesTests extends TestBase {

  @Test
  @DisplayName("Get catalog entries returns non-empty array")
  @Tag("smoke")
  void getCatalogEntriesReturnsNonEmptyArrayTest() {

    EntriesResponseModel response = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = response.getItems();

    step("Verify catalog is not empty", () ->
            assertThat(entries.length, is(greaterThan(0))));
  }

  @Test
  @DisplayName("Catalog entry has correct structure")
  void getCatalogEntryHasCorrectStructureTest() {

    EntriesResponseModel response = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = response.getItems();

    step("Verify catalog is not empty", () ->
            assertThat("Catalog must contain at least one entry",
                    entries.length, greaterThan(0)));

    EntryResponseModel firstEntry = step("Select first entry from catalog", () -> entries[0]);

    step("Verify first catalog entry has correct structure", () -> {
      assertThat(firstEntry.getCat(), not(emptyString()));
      assertThat(firstEntry.getDesc(), not(emptyString()));
      assertThat(firstEntry.getId(), notNullValue());
      assertThat(firstEntry.getImg(), not(emptyString()));
      assertThat(firstEntry.getPrice(), is(greaterThan(0.0)));
      assertThat(firstEntry.getTitle(), not(emptyString()));
    });
  }

  @Test
  @DisplayName("Last evaluated key is equal to the last entry id")
  void getCatalogLastEvaluatedKeyTest() {

    EntriesResponseModel response = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = response.getItems();

    step("Verify catalog is not empty", () ->
            assertThat("Catalog must contain at least one entry",
                    entries.length, greaterThan(0)));

    step("Verify last evaluated key is the last entry id", () ->
            assertThat(response.getLastEvaluatedKey().getId(),
                    is(String.valueOf(entries[entries.length - 1].getId()))));
  }
}


