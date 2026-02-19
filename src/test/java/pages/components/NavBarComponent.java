package pages.components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class NavBarComponent {

  private final SelenideElement navBar = $("#navbarExample");
  private final ElementsCollection navBarItems = navBar.$$("ul.navbar-nav li.nav-item a.nav-link");
  private final SelenideElement loggedInUserItem = navBar.$("#nameofuser");

  public void clickOnNavBarItem(String item) {

    navBarItems.findBy(text(item)).click();
  }

  public void verifyNavBarItemVisible(String item) {

    navBarItems.findBy(text(item)).shouldBe(visible);
  }

  public void verifyNavBarItemNotVisible(String item) {

    navBarItems.findBy(text(item)).shouldNotBe(visible);
  }

  public void verifyUserIsLoggedIn(String username) {
    loggedInUserItem.shouldHave(text("Welcome " + username));
    verifyNavBarItemNotVisible("Log in");
    verifyNavBarItemNotVisible("Sign up");
    verifyNavBarItemVisible("Log out");
  }

  public void verifyUserIsNotLoggedIn() {
    loggedInUserItem.shouldNotBe(visible);
    verifyNavBarItemVisible("Log in");
    verifyNavBarItemVisible("Sign up");
    verifyNavBarItemNotVisible("Log out");
  }
}
