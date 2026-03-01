package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.selector.ByText;
import io.qameta.allure.Step;
import org.openqa.selenium.Alert;

import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class ProductCardPage {

  private final SelenideElement cardTitle = $("#tbodyid").$("h2.name");
  private final SelenideElement cardPrice = $("#tbodyid").$("h3.price-container");
  private final SelenideElement productDescription = $("#tbodyid").$("#more-information");
  private final SelenideElement addToCartButton = $("#tbodyid").$(byText("Add to cart"));

  @Step("Open product card with id")
  public ProductCardPage openProductCardWithId(int id){
    open("/prod.html?idp_=" + id);

    return this;
  }

  @Step("Click on 'Add to cart' button on product card")
  public ProductCardPage clickAddToCartButton(){
    addToCartButton.click();

    return this;
  }

  @Step("Verify product description is not empty")
  public ProductCardPage verifyProductDescriptionIsNotEmpty(){
    productDescription.shouldNotBe(empty);

    return this;
  }

  @Step("Verify product title in product card")
  public ProductCardPage verifyProductTitle(String title){
    cardTitle.shouldHave(text(title));

    return this;
  }

  @Step("Verify product price in product card")
  public ProductCardPage verifyProductPrice(String title){
    cardPrice.shouldHave(text(title));

    return this;
  }

  @Step("Get product title from product card")
  public String getProductTitleFromProductCard(){

    return cardTitle.getText();
  }

  @Step("Get product price from product card")
  public String getProductPriceFromProductCard(){

    return cardPrice.getText().replace("$", "")
            .replace(" *includes tax", "");
  }

  @Step("Click 'Ok' in alert dialog")
  public ProductCardPage clickOkInAlertDialog() {
    Selenide.confirm();

    return this;
  }

  @Step("Verify text in alert dialog")
  public ProductCardPage verifyTextInAlertDialog(String text) {
    Alert alert = Selenide.switchTo().alert();
    assertThat(alert.getText(), containsString(text));

    return this;
  }
}
