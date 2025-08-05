package loganalyzer.collectors;

import loganalyzer.model.LogEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class BrowserCollectorReporterTest {

  private BrowserCollectorReporter collector;
  private ByteArrayOutputStream outputStream;
  private PrintStream originalOut;

  @BeforeEach
  void setUp() {
    collector = new BrowserCollectorReporter();
    outputStream = new ByteArrayOutputStream();
    originalOut = System.out;
    System.setOut(new PrintStream(outputStream));
  }

  @Test
  void shouldCollectBrowserInformation() {
    LogEntry logEntry = LogEntry.builder()
        .browser("Chrome")
        .clientIp("192.168.1.1")
        .os("Windows")
        .build();

    collector.collect(logEntry);

    String output = outputStream.toString();
    assertTrue(output.contains("Chrome"));
    assertTrue(output.contains("Browser Collector Reporter"));
  }

  @Test
  void shouldHandleNullBrowser() {
    LogEntry logEntry = LogEntry.builder()
        .browser(null)
        .clientIp("192.168.1.1")
        .os("Windows")
        .build();

    collector.collect(logEntry);

    String output = outputStream.toString();
    assertFalse(output.contains("null"));
  }

  @Test
  void shouldCountMultipleEntriesForSameBrowser() {
    LogEntry logEntry1 = LogEntry.builder().browser("Chrome").build();
    LogEntry logEntry2 = LogEntry.builder().browser("Chrome").build();
    LogEntry logEntry3 = LogEntry.builder().browser("Firefox").build();

    collector.collect(logEntry1);
    collector.collect(logEntry2);
    collector.collect(logEntry3);

    String output = outputStream.toString();
    assertTrue(output.contains("Chrome-> 2"));
    assertTrue(output.contains("Firefox-> 1"));
  }

  @Test
  void shouldReturnCorrectAnalyzerName() {
    assertEquals("Browser Collector Reporter", collector.analyzerName());
  }

  @Test
  void shouldPrintReport() {
    LogEntry logEntry = LogEntry.builder().browser("Safari").build();
    collector.collect(logEntry);

    outputStream.reset();
    collector.printReport();

    String output = outputStream.toString();
    assertTrue(output.contains("Browser"));
    assertTrue(output.contains("Safari"));
  }

  void tearDown() {
    System.setOut(originalOut);
  }
}
