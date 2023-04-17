package org.duolingo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

public class Main {

  //  private static final String INPUT_SECTION = "//*[@id=\\\"textarea\\\"]";
//  private static final String OUTPUT_SECTION = "/html/body/div[1]/main/div[1]/div[1]/div[3]/div[2]/div[1]/div[1]/div[1]/pre/span";
  private static final String INPUT_SECTION = "//*[@id=\"panelTranslateText\"]/div[1]/div[2]/section[1]/div[3]/div[2]/d-textarea/div";
  private static final String OUTPUT_SECTION = "//*[@id=\"panelTranslateText\"]/div[1]/div[2]/section[2]/div[3]/div[1]/d-textarea/div";

  private static final String DUOLINGO_SITE = "https://www.duolingo.com/";
  //  private static final String TRANSLATOR_SITE = "window.open('https://translate.yandex.ru/?source_lang=en&target_lang=ru&', '_blank');";
  private static final String TRANSLATOR_SITE = "window.open('https://www.deepl.com/translator#ru/en/', '_blank');";
  private static final WebDriver driver = new FirefoxDriver();
  private static final JavascriptExecutor js = (JavascriptExecutor) driver;

  private static ArrayList<String> tabs;
  private static StringBuilder login = new StringBuilder();
  private static StringBuilder password = new StringBuilder();


  public static void main(String[] args) {
    driver.get(DUOLINGO_SITE);
    // Open a new tab with Translator
    js.executeScript(TRANSLATOR_SITE);
    tabs = new ArrayList<String>(driver.getWindowHandles());
    // Switch to the Duolingo tab
    driver.switchTo().window(tabs.get(0));

    try {
      DataParser dataParser = new DataParser();
    } catch (Exception e) {
      System.out.println("An exception occurred: " + e.getMessage());
    }
    try {
      LoginPage loginPage = new LoginPage(driver);
      loginPage.enterUsername();
      loginPage.enterPassword();
      loginPage.clickLoginButton();
    } catch (Exception e) {
      System.out.println("An exception occurred: " + e.getMessage());
    }

    while (true) {
      startLevel();
      passingLevel();
      exitTask();
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

  public static void startLevel() {
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

  public static void passingLevel() {
    for (int i = 0; i < 5; i++) {
      answer(translate(driver.findElement(By.xpath(
              "/html/body/div[1]/div[1]/div/div/div[2]/div/div/div/div/div[2]/div[1]/div/div[2]/div[1]/div/span"))
          .getText()));
    }
  }

  public static String translate(String input) {
    // Switch to the Translator tab
    driver.switchTo().window(tabs.get(1));

    // Translate
    driver.findElement(By.ByXPath.xpath(
            INPUT_SECTION))
        .clear();
    driver.findElement(By.ByXPath.xpath(
            INPUT_SECTION))
        .sendKeys(" " + input);
    delay(2);
    String answer = driver.findElement(By.xpath(
            OUTPUT_SECTION))
        .getText();
    delay(0.5);
    // Switch to the Duolingo tab
    driver.switchTo().window(tabs.get(0));

    return answer;
  }

  public static void answer(String answer) {
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

  public static void exitTask() {
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
