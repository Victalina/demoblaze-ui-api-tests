package tests.api;

import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;


import static io.qameta.allure.Allure.step;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Tags({
        @Tag("all_api_tests"),
        @Tag("catalog_api_tests")

})
@DisplayName("Get product card")
public class GetProductCardTests extends TestBase {

  @Test
  @DisplayName("Get existing product card")
  void getExistingProductCardTest() {

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();

    step("Verify catalog is not empty", () ->
            assertThat("Catalog must contain at least one entry",
                    entries.length, greaterThan(0)));

    int selectedId = step("Select product id from catalog", () -> entries[0].getId());

    ProductCardResponseModel responseProductCard = catalogApi.getExistingProductCardById(String.valueOf(selectedId));

    step("Verify product card has correct structure", () -> {
      assertThat(responseProductCard.getCat(), not(emptyString()));
      assertThat(responseProductCard.getDesc(), not(emptyString()));
      assertThat(responseProductCard.getId(), is(selectedId));
      assertThat(responseProductCard.getImg(), not(emptyString()));
      assertThat(responseProductCard.getPrice(), is(greaterThan(0.0)));
      assertThat(responseProductCard.getTitle(), not(emptyString()));
    });
  }

  @Test
  @DisplayName("Get non-existent product card")
  void getNonExistentProductCardTest() {

    String nonExistentId = "999999";
    ErrorMessageModel response = catalogApi.getNonExistentProductCardById(nonExistentId);

    step("Verify error in response", () ->
            assertThat(response.getErrorMessage(), is("Not found.")));
  }
}
