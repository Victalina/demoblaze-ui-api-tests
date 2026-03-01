package tests.ui;

import helpers.TestDataFactory;
import models.EntriesResponseModel;
import models.EntryResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import pages.CartPage;
import pages.MainPage;
import pages.ProductCardPage;

import java.util.List;

import static io.qameta.allure.Allure.step;

@Tags({
        @Tag("allUiTests"),
        @Tag("cartUiTests")

})
@DisplayName("Cart tests with unregistered user")
public class CartWithUnregisteredUserTests extends TestBase {

  private final MainPage mainPage = new MainPage();
  private final CartPage cartPage = new CartPage();
  private final ProductCardPage productCardPage = new ProductCardPage();

  @Test
  @DisplayName("Open empty cart by menu item by new unregistered user")
  void openCartByMenuItemByUnregisteredUserTest() {
    mainPage.openMainPage()
            .clickOnMenuItem("Cart");
    cartPage.verifyCartTitle("Products")
            .verifyTitlesInCartTable("Pic", "Title", "Price", "x")
            .verifyCartTotalTitle("Total")
            .verifyCartTotalPriceIsEmpty()
            .verifyCartIsEmpty();
  }

  @Test
  @DisplayName("Open empty cart via direct link by new unregistered user")
  void openCartViaDirectLinkByUnregisteredUserTest() {

    mainPage.openMainPage();
    cartPage.openCartByDirectLink()
            .verifyCartTitle("Products")
            .verifyTitlesInCartTable("Pic", "Title", "Price", "x")
            .verifyCartTotalTitle("Total")
            .verifyCartTotalPriceIsEmpty()
            .verifyCartIsEmpty();
  }

  @Test
  @DisplayName("Adding one product to cart by unregistered user")
  void addOneProductToCartByUnregisteredUserTest() {

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();
    int selectedId = step("Select product id from catalog", () -> entries[0].getId());

    mainPage.openMainPage();
    productCardPage.openProductCardWithId(selectedId);
    String productTitle = productCardPage.getProductTitleFromProductCard();
    String productPrice = productCardPage.getProductPriceFromProductCard();

    productCardPage.clickAddToCartButton()
            .verifyTextInAlertDialog("Product added")
            .clickOkInAlertDialog();

    cartPage.openCartByDirectLink()
            .verifyAmountOfProductItemsInCart(1)
            .verifyProductTitleInCart(productTitle)
            .verifyProductPriceInCart(productPrice)
            .verifyCartTotalPrice();
  }

  @Test
  @DisplayName("Adding two identical products to cart by unregistered user")
  void addTwoIdenticalProductsToCartByUnregisteredUserTest() {

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();
    int selectedId = step("Select product id from catalog", () -> entries[0].getId());

    mainPage.openMainPage();
    productCardPage.openProductCardWithId(selectedId);
    String productTitle = productCardPage.getProductTitleFromProductCard();
    String productPrice = productCardPage.getProductPriceFromProductCard();

    productCardPage.clickAddToCartButton()
            .verifyTextInAlertDialog("Product added")
            .clickOkInAlertDialog();

    productCardPage.clickAddToCartButton()
            .verifyTextInAlertDialog("Product added")
            .clickOkInAlertDialog();

    cartPage.openCartByDirectLink()
            .verifyAmountOfProductItemsInCart(2)
            .verifyProductTitleInCart(productTitle)
            .verifyProductPriceInCart(productPrice)
            .verifyCartTotalPrice();
  }

  @Test
  @DisplayName("Adding two different products to cart by unregistered user")
  void addTwoDifferentProductsToCartByUnregisteredUserTest() {

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();
    int firstSelectedId = step("Select product id from catalog", () -> entries[0].getId());
    int secondSelectedId = step("Select product id from catalog", () -> entries[1].getId());

    mainPage.openMainPage();
    productCardPage.openProductCardWithId(firstSelectedId);
    String product1Title = productCardPage.getProductTitleFromProductCard();
    String product1Price = productCardPage.getProductPriceFromProductCard();

    productCardPage.clickAddToCartButton()
            .verifyTextInAlertDialog("Product added")
            .clickOkInAlertDialog();

    productCardPage.openProductCardWithId(secondSelectedId);
    String product2Title = productCardPage.getProductTitleFromProductCard();
    String product2Price = productCardPage.getProductPriceFromProductCard();

    productCardPage.clickAddToCartButton()
            .verifyTextInAlertDialog("Product added")
            .clickOkInAlertDialog();

    cartPage.openCartByDirectLink()
            .verifyAmountOfProductItemsInCart(2)
            .verifyProductTitleInCart(product1Title)
            .verifyProductPriceInCart(product1Price)
            .verifyProductTitleInCart(product2Title)
            .verifyProductPriceInCart(product2Price)
            .verifyCartTotalPrice();
  }

  @Test
  @DisplayName("Deletion single product item from cart by unregistered user")
  void deletionSingleProductItemFromCartByUnregisteredUserTest() {

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();
    int selectedId = step("Select product id from catalog", () -> entries[0].getId());

    String cookie = TestDataFactory.newUnregisteredUserCookie();
    String uuidProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidProduct, cookie, selectedId, false);

    cartPage.openCartByDirectLinkWithExistingUnregisteredUsersCookie(cookie)
            .verifyAmountOfProductItemsInCart(1)
            .clickOnDeleteButtonForProductInCart(1)
            .verifyCartIsEmpty()
            .verifyCartTotalPriceIsEmpty();
  }

  @Test
  @DisplayName("Deletion one of two product items from cart by unregistered user")
  void deletionOneOfTwoProductItemsFromCartByUnregisteredUserTest() {

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();

    int firstSelectedId = step("Select first product id from catalog", () -> entries[0].getId());

    String cookie = TestDataFactory.newUnregisteredUserCookie();
    String uuidFirstProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidFirstProduct, cookie, firstSelectedId, false);

    int secondSelectedId = step("Select second product id from catalog", () -> entries[1].getId());
    String uuidSecondProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidSecondProduct, cookie, secondSelectedId, false);

    cartPage.openCartByDirectLinkWithExistingUnregisteredUsersCookie(cookie)
            .verifyAmountOfProductItemsInCart(2);

    List<String> allTitlesInCart = cartPage.getAllProductTitlesInCart();
    List<String> allPricesInCart = cartPage.getAllProductPricesInCart();

    cartPage.clickOnDeleteButtonForProductInCartFoundByTitle(allTitlesInCart.get(1))
            .verifyAmountOfProductItemsInCart(1)
            .verifyCartTotalPrice()
            .verifyProductTitleInCart(allTitlesInCart.get(0))
            .verifyProductPriceInCart(allPricesInCart.get(0));
  }
}
