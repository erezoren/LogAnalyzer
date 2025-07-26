package loganalyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import loganalyzer.model.LogEntry;

public class LogFileReader {

  private final LogfileParser logfileParser;

  public LogFileReader(LogfileParser logfileParser) {
    this.logfileParser = logfileParser;
  }

  void readFile(String filePath, Consumer<LogEntry> logEntryConsumer) {
    System.out.println("Reading log file......");
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        LogEntry logEntry = logfileParser.parseLine(line);
        logEntryConsumer.accept(logEntry);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
