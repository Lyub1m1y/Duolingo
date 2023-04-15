package org.duolingo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStreamReader;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

  private static final String FILE_PATH = "src/main/resources/data.txt";

  private static StringBuilder login = new StringBuilder();
  private static StringBuilder password = new StringBuilder();
  private static StringBuilder keyApi = new StringBuilder();

  public static void main(String[] args) throws IOException {
    WebDriver driver = new FirefoxDriver();
    driver.get("https://www.duolingo.com/");

    loggingInAccount(driver);

    while (true) {
      startLevel(driver);
      passingLevel(driver);
      exitTask(driver);
    }
  }

  public static void loggingInAccount(WebDriver driver) {
    try {
      // Open login page
      driver.findElement(
              By.ByXPath.xpath("/html/body/div[1]/div[1]/div/div[2]/div[1]/div[2]/div[2]/button"))
          .click();

      // Enter login credentials
      readFileData(login, password, keyApi);
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

  public static void readFileData(StringBuilder login, StringBuilder password,
      StringBuilder keyApi) {
    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
      String line;
      int lineCount = 1;
      while ((line = reader.readLine()) != null && lineCount != 7) {
        if (lineCount == 2) {
          login.append(line);
        } else if (lineCount == 4) {
          password.append(line);
        } else if (lineCount == 6) {
          keyApi.append(line);
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

  public static void passingLevel(WebDriver driver) throws IOException {
    // Task 1
    String answer;
    for (int i = 0; i < 5; i++) {
      answer(driver, translate(driver.findElement(By.xpath(
              "/html/body/div[1]/div[1]/div/div/div[2]/div/div/div/div/div[2]/div[1]/div/div[2]/div[1]/div/span"))
          .getText()));
    }
  }

  public static String translate(String input) throws IOException {
    String to = "en";
    String from = "ru";
    String url = "https://api.lecto.ai/v1/translate/text";

    String[] cmd = {"curl", "-X", "POST", "-H", "X-API-Key: " + keyApi, "-H",
        "Content-Type: application/json", "-H",
        "Accept: application/json", "--data-raw",
        "{\"texts\": " + toJson(input) + ", \"to\": " + toJson(to) + ", \"from\": \"" + from
            + "\"}",
        url};

    ProcessBuilder pb = new ProcessBuilder(cmd);
    Process p = pb.start();
    BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

    String line;
    StringBuilder response = new StringBuilder();
    while ((line = reader.readLine()) != null) {
      response.append(line);
    }

    System.out.println("-------input---------");
    System.out.println(input);
    System.out.println("-------input---------");
    System.out.println("-------json---------");
    System.out.println(response.toString());
    System.out.println("-------json---------");
    System.out.println("-------answer---------");
    String an = getAnswer(response.toString());
    System.out.println(an);
    System.out.println("-------anwer---------");
    return an;
  }

  private static String getAnswer(String json) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode rootNode = objectMapper.readTree(json);
    return rootNode.get("translations").get(0).get("translated").get(0).asText();
  }

  private static String toJson(String array) {
    StringBuilder sb = new StringBuilder();
    sb.append("[\"");
    sb.append(array
    );
    sb.append("\"]");
    return sb.toString();
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
