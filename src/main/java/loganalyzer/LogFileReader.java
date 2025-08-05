package loganalyzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import loganalyzer.model.LogEntry;

public class LogFileReader {

  private final LogfileParser logfileParser;
  private final ExecutorService executor;

  public LogFileReader(LogfileParser logfileParser, ExecutorService executor) {
    this.logfileParser = logfileParser;
    this.executor = executor;
  }

  void readFile(String filePath, Consumer<LogEntry> logEntryConsumer) {
    System.out.println("Reading log file......");
    List<Future<LogEntry>> futures = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(
        LogFileReader.class.getClassLoader().getResourceAsStream(filePath)))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String finalLine = line;
        Future<LogEntry> logEntryFuture = executor.submit(() -> logfileParser.parseLine(finalLine));
        futures.add(logEntryFuture);
      }
      for (Future<LogEntry> future : futures) {
        logEntryConsumer.accept(future.get());
      }
    } catch (IOException | ExecutionException | InterruptedException e) {
      throw new RuntimeException(e);
    } finally {
      executor.shutdown();
      try {
        if (!executor.awaitTermination(60, java.util.concurrent.TimeUnit.SECONDS)) {
          executor.shutdownNow();
        }
      } catch (InterruptedException e) {
        executor.shutdownNow();
        Thread.currentThread().interrupt();
      }
      System.out.println("Log file reading completed.");
    }
  }
}
