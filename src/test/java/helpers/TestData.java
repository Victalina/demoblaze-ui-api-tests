package helpers;

import lombok.Data;
import utils.RandomUtils;

@Data
public class TestData {

  RandomUtils randomUtils = new RandomUtils();

private String login = randomUtils.getRandomUsername();
private String password = randomUtils.getRandomPassword();

}
