package tests.api;

import models.CategoryRequestModel;
import models.CategoryResponseModel;
import models.EntryResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static spec.Spec.requestSpec;
import static spec.Spec.responseSpecStatusCode;

@DisplayName("Catalog category")
public class CatalogCategoryTests extends TestBase {

  @ValueSource(strings = {"phone", "notebook", "monitor"})
  @ParameterizedTest(name = "Get entries by category {0} returns only requested category")
  void getEntriesByCategoryReturnsOnlyRequestedCategoryTest(String category) {

    CategoryRequestModel cat = new CategoryRequestModel(category);

    CategoryResponseModel response = step("Send request to get product from category", () ->
            given(requestSpec)
                    .body(cat)
                    .when()
                    .post("/bycat")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(CategoryResponseModel.class));

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

    CategoryRequestModel cat = new CategoryRequestModel("tablets");

    CategoryResponseModel response = step("Send request to get product from category", () ->
            given(requestSpec)
                    .body(cat)
                    .when()
                    .post("/bycat")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(CategoryResponseModel.class));

    EntryResponseModel[] entries = response.getItems();

    step("Verify category catalog is empty", () ->
            assertThat(entries.length, is(0)));
  }
}
