package org.duolingo;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;

public class Main {

  private static final String DUOLINGO_SITE = "https://www.duolingo.com/";
  private static final String TRANSLATOR_SITE = "window.open('https://www.deepl.com/translator#ru/en/', '_blank');";
  private static final WebDriver driver = new FirefoxDriver();
  private static final JavascriptExecutor js = (JavascriptExecutor) driver;

  public static void main(String[] args) {
    driver.get(DUOLINGO_SITE);
    // Open a new tab with Translator
    js.executeScript(TRANSLATOR_SITE);
    ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
    // Switch to the Duolingo tab
    driver.switchTo().window(tabs.get(0));

    try {
      new DataParser();
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

    Level lvl = new Level(driver, tabs);
    while (true) {
      lvl.startLevel();
      lvl.passingLevel();
      lvl.exitLevel();
    }
  }
}
