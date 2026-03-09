package context;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UserPool {

  private static final Queue<User> USERS = new ConcurrentLinkedQueue<>(getListOfUsers());

  private static List<User> getListOfUsers() {

    List<User> listOfUsers = new ArrayList<>();

    for (Map.Entry<String, String> entry : TestUsers.getUsersMap().entrySet()) {

      listOfUsers.add(new User(entry.getKey(), entry.getValue()));
    }

    return listOfUsers;
  }

  public static User getUser() {
    User user = USERS.poll();

    if (user == null) {
      throw new RuntimeException("No available test users in UserPool");
    }

    return user;
  }

  public static void releaseUser(User user) {
    USERS.add(user);
  }
}
