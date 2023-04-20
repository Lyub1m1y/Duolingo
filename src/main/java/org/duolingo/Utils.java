package org.duolingo;

public class Utils {

  public static void delay(double seconds) {
    try {
      int milliseconds = (int) (seconds * 1000);
      Thread.sleep(milliseconds);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      e.printStackTrace();
    }
  }
}
