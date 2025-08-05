package loganalyzer.collectors;

import loganalyzer.model.LogEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class OsCollectorReporterTest {

  private OsCollectorReporter collector;
  private ByteArrayOutputStream outputStream;
  private PrintStream originalOut;

  @BeforeEach
  void setUp() {
    collector = new OsCollectorReporter();
    outputStream = new ByteArrayOutputStream();
    originalOut = System.out;
    System.setOut(new PrintStream(outputStream));
  }

  @Test
  void shouldCollectOsInformation() {
    LogEntry logEntry = LogEntry.builder()
        .os("Windows")
        .clientIp("192.168.1.1")
        .browser("Chrome")
        .build();

    collector.collect(logEntry);

    String output = outputStream.toString();
    assertTrue(output.contains("Windows"));
    assertTrue(output.contains("OS Collector Reporter"));
  }

  @Test
  void shouldHandleNullOs() {
    LogEntry logEntry = LogEntry.builder()
        .os(null)
        .clientIp("192.168.1.1")
        .browser("Chrome")
        .build();

    collector.collect(logEntry);

    String output = outputStream.toString();
    assertFalse(output.contains("null"));
  }

  @Test
  void shouldCountMultipleEntriesForSameOs() {
    LogEntry logEntry1 = LogEntry.builder().os("Windows").build();
    LogEntry logEntry2 = LogEntry.builder().os("Windows").build();
    LogEntry logEntry3 = LogEntry.builder().os("macOS").build();

    collector.collect(logEntry1);
    collector.collect(logEntry2);
    collector.collect(logEntry3);

    String output = outputStream.toString();
    assertTrue(output.contains("Windows-> 2"));
    assertTrue(output.contains("macOS-> 1"));
  }

  @Test
  void shouldReturnCorrectAnalyzerName() {
    assertEquals("OS Collector Reporter", collector.analyzerName());
  }

  void tearDown() {
    System.setOut(originalOut);
  }
}
