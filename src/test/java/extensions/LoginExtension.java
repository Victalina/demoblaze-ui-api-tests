package extensions;

import api.AuthApi;
import context.TestUserContext;
import context.User;
import context.UserPool;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class LoginExtension implements BeforeEachCallback, AfterEachCallback {

  private final AuthApi authApi = new AuthApi();

  @Override
  public void beforeEach(ExtensionContext context) {

    User user = UserPool.getUser();
    TestUserContext.set(user);

    String token = authApi.getTokenForRegisteredUser(user.getLogin(), user.getPassword());
    open("/blazemeter-favicon-32x32.png");
    getWebDriver().manage().addCookie(new Cookie("tokenp_", token));
  }

  @Override
  public void afterEach(ExtensionContext context) {

    User user = TestUserContext.get();

    if (user != null) {
      UserPool.releaseUser(user);
      TestUserContext.clear();
    }
  }
}
