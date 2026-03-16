package context;

import config.ParallelConfig;
import config.TestConfig;

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

    if (ParallelConfig.isParallelRun()) {

      User user = USERS.poll();

      if (user == null) {
        throw new RuntimeException("No available test users in UserPool");

      }
      return user;
    }

    return new User(TestConfig.get("test.user.login"), TestConfig.get("test.user.password"));
  }

  public static void releaseUser(User user) {
    USERS.add(user);
  }
}
