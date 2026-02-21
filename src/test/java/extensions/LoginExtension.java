package extensions;

import api.AuthApi;
import config.TestConfig;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class LoginExtension implements BeforeEachCallback {

  private final AuthApi authApi = new AuthApi();

  @Override
  public void beforeEach(ExtensionContext context) {

    String login = TestConfig.get("test.user.login");
    String password = TestConfig.get("test.user.password");

    String token = authApi.getTokenForRegisteredUser(login, password);
    open("/blazemeter-favicon-32x32.png");
    getWebDriver().manage().addCookie(new Cookie("tokenp_", token));
  }
}
