package org.duolingo;

public class Data {

  private static Data instance = null;
  private StringBuilder username;
  private StringBuilder password;

  private Data() {
    this.username = new StringBuilder();
    this.password = new StringBuilder();
  }

  public static synchronized Data getInstance() {
    if (instance == null) {
      instance = new Data();
    }
    return instance;
  }

  public StringBuilder getUsername() {
    return username;
  }

  public StringBuilder getPassword() {
    return password;
  }

  public void setUsername(String username) {
    this.username.append(username);
  }

  public void setPassword(String password) {
    this.password.append(password);
  }

}
