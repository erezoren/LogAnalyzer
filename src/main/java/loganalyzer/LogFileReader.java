package loganalyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import lombok.SneakyThrows;

public class LogFileReader {

  List<String> readFile(String filePath) {
    try {
      return Files.readAllLines(Paths.get(filePath));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
