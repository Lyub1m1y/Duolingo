package org.duolingo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

  private static final String FILE_PATH = "src/main/resources/login_info.txt";

  public static void main(String[] args) {
    WebDriver driver = new FirefoxDriver();
    driver.get("https://www.duolingo.com/");

//    driver.findElements(By.ByXPath.xpath("".get));
    loggingInAccount(driver);

    while (true) {
      startLevel(driver);
      passingLevel(driver);
      exitTask(driver);
    }
  }

  public static void loggingInAccount(WebDriver driver) {
    try {
      // Click the "Log in" button
      driver.findElement(
              By.ByXPath.xpath("/html/body/div[1]/div[1]/div/div[2]/div[1]/div[2]/div[2]/button"))
          .click();

      // Enter login credentials
      StringBuilder login = new StringBuilder();
      StringBuilder password = new StringBuilder();
      readFileData(login, password);
      driver.findElement(By.ByXPath.xpath(
              "/html/body/div[2]/div[2]/div/div/form/div[1]/div[1]/div[1]/label/div/input"))
          .sendKeys(login);
      driver.findElement(By.ByXPath.xpath(
              "/html/body/div[2]/div[2]/div/div/form/div[1]/div[1]/div[2]/label/div[1]/input"))
          .sendKeys(password);

      // Click the "Log in" button
      delay(1);
      driver.findElement(By.ByXPath.xpath("/html/body/div[2]/div[2]/div/div/form/div[1]/button"))
          .click();
    } catch (Exception e) {
      System.out.println("An exception occurred: " + e.getMessage());
    }
  }

  public static void readFileData(StringBuilder login, StringBuilder password) {
    System.out.println("----------hui-------------");
    System.out.println(System.getProperty("user.dir"));
    System.out.println(FILE_PATH);
    System.out.println("----------hui-------------");
    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
      String line;
      int lineCount = 1;
      while ((line = reader.readLine()) != null && lineCount != 5) {
        if (lineCount == 2) {
          login.append(line);
        } else if (lineCount == 4) {
          password.append(line);
        }
        lineCount++;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void delay(double seconds) {
    try {
      int milliseconds = (int) (seconds * 1000);
      Thread.sleep(milliseconds);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      e.printStackTrace();
    }
  }

  public static void startLevel(WebDriver driver) {
    try {
      delay(9);
      // Scroll to the bottom of the page
      JavascriptExecutor js = (JavascriptExecutor) driver;
      // Scroll by a specific amount of pixels
      js.executeScript(
          "window.scrollTo(0, 1402);"); // start position 8322px, but need absolute 1402px
      // Select level
      delay(2);
      driver.findElement(By.ByXPath.xpath(
              "/html/body/div[1]/div[2]/div/div[2]/div/div[2]/div[1]/section[2]/div/div[12]/button"))
          .click();
      // Click start button
      try {
        driver.findElement(By.ByXPath.xpath("/html/body/div[2]/div[2]/div/div/div/div/a"))
            .click();
      } catch (Exception e) {
        System.out.println("Start button xPath no valid (div[2])");
      }
      try {
        driver.findElement(By.ByXPath.xpath("/html/body/div[2]/div[5]/div/div/div/div/a"))
            .click();
      } catch (Exception e) {
        System.out.println("Start button xPath no valid (div[5])");
      }
      // Click further
      delay(4);
      driver.findElement(
          By.ByXPath.xpath("/html/body/div[1]/div[1]/div/div/div[3]/div/div/div/button")).click();
    } catch (Exception e) {
      System.out.println("An exception occurred: " + e.getMessage());
    }
  }

  public static void passingLevel(WebDriver driver) {
    // Task 1
    answer(driver, "Good");
    // Task 2
    answer(driver, "Good");
    // Task 3
    answer(driver, "Good thanks");
    // Task 4
    answer(driver, "Goodbye");
    // Task 5
    answer(driver, "Goodbye");
    // Switch task 5
    answer(driver, "Good and you?");
  }

  public static void answer(WebDriver driver, String answer) {
    try {
      delay(0.3);
      driver.findElement(By.ByXPath.xpath(
              "/html/body/div[1]/div[1]/div/div/div[2]/div/div/div/div/div[2]/div[2]/div/textarea"))
          .sendKeys(answer);
      driver.findElement(
          By.ByXPath.xpath("/html/body/div[1]/div[1]/div/div/div[3]/div/div/div/button")).click();
      driver.findElement(
              By.ByXPath.xpath("/html/body/div[1]/div[1]/div/div/div[3]/div/div/div[2]/button"))
          .click();
    } catch (Exception e) {
      System.out.println("An exception occurred: " + e.getMessage());
    }
  }

  public static void exitTask(WebDriver driver) {
    // Exit task
    try {
      delay(3);
      driver.findElement(
          By.ByXPath.xpath("/html/body/div[1]/div[1]/div/div/div[1]/div/div/button")).click();
      delay(3);
      driver.findElement(By.ByXPath.xpath("/html/body/div[2]/div[5]/div/div/div/div[1]/button"))
          .click();
      delay(3);
      driver.findElement(
              By.ByXPath.xpath("/html/body/div[1]/div[1]/div/div/div[2]/div/div/div[2]/button"))
          .click();
    } catch (Exception e) {
      System.out.println("An exception occurred: " + e.getMessage());
    }

    // Exit fail task
    try {
      delay(1);
      driver.findElement(
              By.ByXPath.xpath("/html/body/div[1]/div[1]/div[1]/div/div[2]/div/div/div[1]/button"))
          .click();
    } catch (Exception e) {
      System.out.println("An exception occurred: " + e.getMessage());
    }
  }
}
