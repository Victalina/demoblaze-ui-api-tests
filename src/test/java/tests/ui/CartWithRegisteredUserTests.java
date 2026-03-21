package tests.ui;

import context.TestUserContext;
import context.User;
import extensions.WithLogin;
import helpers.TestDataFactory;
import models.EntriesResponseModel;
import models.EntryResponseModel;
import org.junit.jupiter.api.*;
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
public class CartWithRegisteredUserTests extends TestBase {

  @BeforeEach
  void cleanCartIfNeeded() {

    User user = TestUserContext.get();

    if (user != null) {
      cartApi.ensureCartIsEmptyForRegisteredUser(
              user.getLogin(),
              user.getPassword());
    }
  }

  private final MainPage mainPage = new MainPage();
  private final CartPage cartPage = new CartPage();
  private final ProductCardPage productCardPage = new ProductCardPage();

  @Test
  @DisplayName("Open emptied cart by menu item by registered user")
  @WithLogin
  void openCartByMenuItemByRegisteredUserTest() {
    mainPage.openMainPage()
            .clickOnMenuItem("Cart");
    cartPage.verifyCartTitle("Products")
            .verifyTitlesInCartTable("Pic", "Title", "Price", "x")
            .verifyCartTotalTitle("Total")
            .verifyCartTotalPriceIsEmpty()
            .verifyCartIsEmpty();
  }

  @Test
  @DisplayName("Open empty cart via direct link by registered user")
  @WithLogin
  void openCartViaDirectLinkByRegisteredUserTest() {

    cartPage.openCartByDirectLink()
            .verifyCartTitle("Products")
            .verifyTitlesInCartTable("Pic", "Title", "Price", "x")
            .verifyCartTotalTitle("Total")
            .verifyCartTotalPriceIsEmpty()
            .verifyCartIsEmpty();
  }

  @Test
  @DisplayName("Adding one product to cart by registered user")
  @WithLogin
  void addOneProductToCartByRegisteredUserTest() {

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();
    int selectedId = step("Select product id from catalog", () -> entries[0].getId());

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
  @DisplayName("Adding two identical products to cart by registered user")
  @WithLogin
  void addTwoIdenticalProductsToCartByRegisteredUserTest() {

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();
    int selectedId = step("Select product id from catalog", () -> entries[0].getId());

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
  @DisplayName("Adding two different products to cart by registered user")
  @WithLogin
  void addTwoDifferentProductsToCartByRegisteredUserTest() {

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();
    int firstSelectedId = step("Select product id from catalog", () -> entries[0].getId());
    int secondSelectedId = step("Select product id from catalog", () -> entries[1].getId());

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
  @DisplayName("Deletion single product item from cart by registered user")
  @WithLogin
  void deletionSingleProductItemFromCartByRegisteredUserTest() {

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();
    int selectedId = step("Select product id from catalog", () -> entries[0].getId());

    String cookie = authApi.getTokenForRegisteredUser(TestUserContext.get().getLogin(), TestUserContext.get().getPassword());
    String uuidProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidProduct, cookie, selectedId, true);

    cartPage.openCartByDirectLink()
            .verifyAmountOfProductItemsInCart(1)
            .clickOnDeleteButtonForProductInCart(1)
            .verifyCartIsEmpty()
            .verifyCartTotalPriceIsEmpty();
  }

  @Test
  @DisplayName("Deletion one of two product items from cart by registered user")
  @WithLogin
  void deletionOneOfTwoProductItemsFromCartByRegisteredUserTest() {

    EntriesResponseModel responseCatalog = catalogApi.getCatalogEntries();
    EntryResponseModel[] entries = responseCatalog.getItems();

    int firstSelectedId = step("Select first product id from catalog", () -> entries[0].getId());

    String cookie = authApi.getTokenForRegisteredUser(TestUserContext.get().getLogin(), TestUserContext.get().getPassword());
    String uuidFirstProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidFirstProduct, cookie, firstSelectedId, true);

    int secondSelectedId = step("Select second product id from catalog", () -> entries[1].getId());
    String uuidSecondProduct = TestDataFactory.newCartItemUuid();

    cartApi.addProductToCart(uuidSecondProduct, cookie, secondSelectedId, true);

    cartPage.openCartByDirectLink()
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
