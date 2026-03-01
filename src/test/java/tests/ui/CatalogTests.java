package tests.ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.ProductCardPage;

@Tags({
        @Tag("allUiTests"),
        @Tag("catalogUiTests")

})
@DisplayName("Catalog tests")
public class CatalogTests extends TestBase {

  private final MainPage mainPage = new MainPage();
  private final ProductCardPage productCardPage = new ProductCardPage();

  @Test
  @DisplayName("Catalog should not be empty")
  void catalogShouldNotBeEmptyTest() {
    mainPage.openMainPage()
            .verifyCatalogIsNotEmpty();
  }

  @Test
  @DisplayName("Product card in catalog listing should not be empty")
  void productCardInCatalogListingShouldNotBeEmptyTest() {
    mainPage.openMainPage()
            .verifyProductCardInCatalogIsNotEmpty();
  }

  @Test
  @DisplayName("Open product card by clicking on product image in catalog")
  void openProductCardByClickingOnImageTest() {
    mainPage.openMainPage();

    String productTitleFromCatalog = mainPage.getFirstProductTitleFromCatalogListing();
    String productPriceFromCatalog = mainPage.getFirstProductPriceFromCatalogListing();

    mainPage.clickOnFirstProductItemImgInCatalog();

    productCardPage.verifyProductTitle(productTitleFromCatalog)
            .verifyProductPrice(productPriceFromCatalog)
            .verifyProductDescriptionIsNotEmpty();
  }

  @Test
  @DisplayName("Open product card by clicking on product title in catalog")
  void openProductCardByClickingOnTitleTest() {
    mainPage.openMainPage();

    String productTitleFromCatalog = mainPage.getFirstProductTitleFromCatalogListing();
    String productPriceFromCatalog = mainPage.getFirstProductPriceFromCatalogListing();

    mainPage.openMainPage()
            .clickOnFirstProductItemTitleInCatalog();

    productCardPage.verifyProductTitle(productTitleFromCatalog)
            .verifyProductPrice(productPriceFromCatalog)
            .verifyProductDescriptionIsNotEmpty();
  }
}
