package utils;

import com.github.javafaker.Faker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class RandomUtils {

  Faker faker = new Faker();

  public String getRandomUsername() {

    return faker.name().username();
  }

  public String getRandomPassword() {

    return faker.internet().password(
            6, 8, true, true, true);
  }

  public String getRandomFullName(){

    return faker.name().fullName();
  }

  public String getRandomCounty(){

    return faker.country().name();
  }

  public String getRandomCity(){

    return faker.address().cityName();
  }

  public String getRandomCard(){

    return faker.finance().creditCard();
  }

  public int getRandomMonth(){

    return faker.date().birthday().getMonth();
  }

  public int getRandomYear(String startYear, String endYear){
    try {

      Date start = new SimpleDateFormat("yyyy").parse(startYear);
      Date end = new SimpleDateFormat("yyyy").parse(endYear);
      return faker.date().between(start, end).getYear();

    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }
}
