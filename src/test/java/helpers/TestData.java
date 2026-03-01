package helpers;

import lombok.Data;
import utils.RandomUtils;

@Data
public class TestData {

  RandomUtils randomUtils = new RandomUtils();

private String login = randomUtils.getRandomUsername();
private String password = randomUtils.getRandomPassword();
private String name = randomUtils.getRandomFullName();
private String country = randomUtils.getRandomCounty();
private String city = randomUtils.getRandomCity();
private String creditCard = randomUtils.getRandomCard();
private int month = randomUtils.getRandomMonth();
private int year = randomUtils.getRandomYear("1950", "2020");

}
