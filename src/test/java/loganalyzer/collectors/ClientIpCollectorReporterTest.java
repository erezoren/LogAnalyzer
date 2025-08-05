package loganalyzer.collectors;

import loganalyzer.model.LogEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ClientIpCollectorReporterTest {

  private ClientIpCollectorReporter collector;
  private ByteArrayOutputStream outputStream;
  private PrintStream originalOut;

  @BeforeEach
  void setUp() {
    collector = new ClientIpCollectorReporter();
    outputStream = new ByteArrayOutputStream();
    originalOut = System.out;
    System.setOut(new PrintStream(outputStream));
  }

  @Test
  void shouldCollectClientIpInformation() {
    LogEntry logEntry = LogEntry.builder()
        .clientIp("192.168.1.1")
        .browser("Chrome")
        .os("Windows")
        .build();

    collector.collect(logEntry);

    String output = outputStream.toString();
    assertTrue(output.contains("192.168.1.1"));
    assertTrue(output.contains("Client IP Collector Reporter"));
  }

  @Test
  void shouldHandleNullClientIp() {
    LogEntry logEntry = LogEntry.builder()
        .clientIp(null)
        .browser("Chrome")
        .os("Windows")
        .build();

    collector.collect(logEntry);

    String output = outputStream.toString();
    assertFalse(output.contains("null"));
  }

  @Test
  void shouldReturnCorrectAnalyzerName() {
    assertEquals("Client IP Collector Reporter", collector.analyzerName());
  }

  void tearDown() {
    System.setOut(originalOut);
  }
}
