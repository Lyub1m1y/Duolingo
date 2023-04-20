package org.duolingo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataParser {

  private static final String FILE_PATH = "src/main/resources/data.txt";

  public DataParser() {
    Data data = Data.getInstance();
    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
      String line;
      int lineCount = 1;
      while ((line = reader.readLine()) != null && lineCount != 7) {
        if (lineCount == 2) {
          data.setUsername(line);
        } else if (lineCount == 4) {
          data.setPassword(line);
        }
        lineCount++;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
