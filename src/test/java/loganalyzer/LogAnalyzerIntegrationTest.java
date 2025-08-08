package loganalyzer;

import loganalyzer.collectors.BrowserCollectorReporter;
import loganalyzer.collectors.LogCollectorReporter;
import loganalyzer.collectors.OsCollectorReporter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class LogAnalyzerIntegrationTest {

  @TempDir
  Path tempDir;

  @Test
  void shouldProcessLogFileEndToEnd() throws IOException {
    // Create a test log file
    Path logFile = tempDir.resolve("test.log");
    List<String> logLines = List.of(
        "192.168.1.1 - - [10/Oct/2000:13:55:36 -0700] \"GET /apache_pb.gif HTTP/1.0\" 200 2326 \"http://www.example.com/start.html\" \"Mozilla/4.08 [en] (Win98; I ;Nav)\" 100 200 - 12345",
        "192.168.1.2 - - [10/Oct/2000:13:56:36 -0700] \"GET /test.html HTTP/1.0\" 200 1234 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36\" 150 250 - 67890"
    );
    Files.write(logFile, logLines);

    // Set up the application components
    ExecutorService executorService = Executors.newFixedThreadPool(2);
    List<LogCollectorReporter> collectors = List.of(
        new OsCollectorReporter(),
        new BrowserCollectorReporter()
    );

    LogFileReader logFileReader = new LogFileReader(new LogfileParser(), executorService);
    LogReportCreator logReportCreator = new LogReportCreator(logFileReader);

    // This should run without throwing exceptions
    assertDoesNotThrow(() -> {
      logReportCreator.createLogReport(logFile.toString(), collectors);
    });

    executorService.shutdown();
  }

  @Test
  void shouldHandleInvalidLogLines() throws IOException {
    Path logFile = tempDir.resolve("invalid.log");
    List<String> logLines = List.of(
        "invalid log line 1",
        "another invalid line",
        "192.168.1.1 - - [10/Oct/2000:13:55:36 -0700] \"GET /test HTTP/1.0\" 200 1234 \"-\" \"Mozilla/5.0\" 100 200 - 12345"
    );
    Files.write(logFile, logLines);

    ExecutorService executorService = Executors.newFixedThreadPool(2);
    List<LogCollectorReporter> collectors = List.of(new OsCollectorReporter());

    LogFileReader logFileReader = new LogFileReader(new LogfileParser(), executorService);
    LogReportCreator logReportCreator = new LogReportCreator(logFileReader);

    // Should handle invalid lines gracefully
    assertDoesNotThrow(() -> {
      logReportCreator.createLogReport(logFile.toString(), collectors);
    });

    executorService.shutdown();
  }
}
