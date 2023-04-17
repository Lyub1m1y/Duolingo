package org.duolingo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

  private WebDriver driver;
  private Data data = Data.getInstance();

  private static final String LOGIN_PAGE = "/html/body/div[1]/div[1]/div/div[2]/div[1]/div[2]/div[2]/button";
  private static final String USERNAME_FIELD = "/html/body/div[2]/div[2]/div/div/form/div[1]/div[1]/div[1]/label/div/input";
  private static final String PASSWORD_FIELD = "/html/body/div[2]/div[2]/div/div/form/div[1]/div[1]/div[2]/label/div[1]/input";
  private static final String LOGIN_BUTTON = "/html/body/div[2]/div[2]/div/div/form/div[1]/button";

  public LoginPage(WebDriver driver) {
    this.driver = driver;
    this.driver.findElement(
            By.ByXPath.xpath(LOGIN_PAGE))
        .click();
  }

  public void enterUsername() {
    driver.findElement(By.ByXPath.xpath(
            USERNAME_FIELD))
        .sendKeys(data.getUsername());

  }

  public void enterPassword() {
    driver.findElement(By.ByXPath.xpath(
        PASSWORD_FIELD)).sendKeys(data.getPassword());
  }

  public void clickLoginButton() {
    driver.findElement(By.ByXPath.xpath(LOGIN_BUTTON)).click();
  }
}
