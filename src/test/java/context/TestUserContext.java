package context;

public class TestUserContext {

  private static final ThreadLocal<User> USER = new ThreadLocal<>();

  public static void set(User user) {
    USER.set(user);
  }

  public static User get() {
    return USER.get();
  }

  public static void clear() {
    USER.remove();
  }
}
