package tests.api;

import models.CategoryResponseModel;
import models.EntryResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import static io.qameta.allure.Allure.step;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Tags({
        @Tag("allApiTests"),
        @Tag("catalogApiTests")

})
@DisplayName("Catalog category")
public class CatalogCategoryTests extends TestBase {

  @ValueSource(strings = {"phone", "notebook", "monitor"})
  @ParameterizedTest(name = "Get entries by category {0} returns only requested category")
  void getEntriesByCategoryReturnsOnlyRequestedCategoryTest(String category) {

    CategoryResponseModel response = catalogApi.getEntriesByCategory(category);
    EntryResponseModel[] entries = response.getItems();

    step("Verify category catalog is not empty", () ->
            assertThat("Catalog must contain at least one entry",
                    entries.length, greaterThan(0)));

    step("Verify category entries have only requested category", () -> {
      for (EntryResponseModel entry : entries) {

        assertThat(entry.getCat(), is(category));
      }
    });
  }

  @Test
  @DisplayName("Get entries by non-existent category returns empty array")
  void getEntriesByNonExistentCategoryReturnsEmptyArrayTest() {

    CategoryResponseModel response = catalogApi.getEntriesByCategory("tablets");
    EntryResponseModel[] entries = response.getItems();

    step("Verify category catalog is empty", () ->
            assertThat(entries.length, is(0)));
  }
}
