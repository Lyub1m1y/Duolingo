package org.duolingo;

import static org.duolingo.Utils.delay;

import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class Level {

  private final WebDriver driver;
  private final ArrayList<String> tabs;
  private static final String INPUT_SECTION = "//*[@id=\"panelTranslateText\"]/div[1]/div[2]/section[1]/div[3]/div[2]/d-textarea/div";
  private static final String OUTPUT_SECTION = "//*[@id=\"panelTranslateText\"]/div[1]/div[2]/section[2]/div[3]/div[1]/d-textarea/div";

  public Level(WebDriver driver, ArrayList<String> tabs) {
    this.driver = driver;
    this.tabs = tabs;
  }

  public void startLevel() {
    try {
      delay(9);
      // Scroll to the bottom of the page
      JavascriptExecutor js = (JavascriptExecutor) driver;
      // Scroll by a specific amount of pixels
      js.executeScript(
          "window.scrollTo(0, 1402);"); // I have a starting position 8322px, but need absolute 1402px
      // Select level
      driver.findElement(By.ByXPath.xpath(
              "/html/body/div[1]/div[2]/div/div[2]/div/div[2]/div[1]/section[2]/div/div[12]/button"))
          .click();
      // Click start button
      try {
        driver.findElement(By.ByXPath.xpath("/html/body/div[2]/div[2]/div/div/div/div/a"))
            .click();
      } catch (Exception e1) {
        try {
          driver.findElement(By.ByXPath.xpath("/html/body/div[2]/div[5]/div/div/div/div/a"))
              .click();
        } catch (Exception e2) {
          System.out.println("Start button xPath no valid (div[5])");
        }
      }

      // Click further
      delay(4);
      driver.findElement(
          By.ByXPath.xpath("/html/body/div[1]/div[1]/div/div/div[3]/div/div/div/button")).click();
    } catch (Exception e) {
      System.out.println("An exception occurred: " + e.getMessage());
    }
  }

  public void passingLevel() {
    for (int i = 0; i < 5; i++) {
      answer(translate(driver.findElement(By.xpath(
              "/html/body/div[1]/div[1]/div/div/div[2]/div/div/div/div/div[2]/div[1]/div/div[2]/div[1]/div/span"))
          .getText()));
    }
  }

  public String translate(String input) {
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

  public void answer(String answer) {
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

  public void exitLevel() {
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
  }
}
