package utils;

import com.github.javafaker.Faker;

public class RandomUtils {

  Faker faker = new Faker();

  public String getRandomUsername() {

    return faker.name().username();
  }

  public String getRandomPassword() {

    return faker.internet().password(
            6, 8, true, true, true);
  }
}
