package helpers;

import java.util.UUID;

public class TestDataFactory {


  public static String newUnregisteredUserCookie() {
    return "user=" + UUID.randomUUID();
  }

  public static String newCartItemUuid() {
    return UUID.randomUUID().toString();
  }

}
