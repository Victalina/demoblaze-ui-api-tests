package context;

public class TestUserContext {

  private static final ThreadLocal<User> USER = new ThreadLocal<>();

  public static void set(User user) {
    USER.set(user);
  }

  public static User get() {

    User user = USER.get();

    if (user == null) {
      user = UserPool.getUser();
      USER.set(user);
    }

    return user;
  }

  public static void clear() {
    USER.remove();
  }

}
