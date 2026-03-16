package config;

public class ParallelConfig {

  public static boolean isParallelRun(){
    String threads = System.getProperty("threads");
    return threads != null && Integer.parseInt(threads) > 1;
  }
}
