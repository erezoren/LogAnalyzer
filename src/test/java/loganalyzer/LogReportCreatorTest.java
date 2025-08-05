package loganalyzer;

import loganalyzer.collectors.LogCollectorReporter;
import loganalyzer.model.LogEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogReportCreatorTest {

  @Mock
  private LogFileReader mockLogFileReader;

  @Mock
  private LogCollectorReporter mockCollector1;

  @Mock
  private LogCollectorReporter mockCollector2;

  private LogReportCreator logReportCreator;

  @BeforeEach
  void setUp() {
    logReportCreator = new LogReportCreator(mockLogFileReader);
  }

  @Test
  void shouldCreateLogReportSuccessfully() {
    String testPath = "/test/path";
    List<LogCollectorReporter> collectors = List.of(mockCollector1, mockCollector2);

    doAnswer(invocation -> {
      Consumer<LogEntry> consumer = invocation.getArgument(1);
      LogEntry logEntry = LogEntry.builder()
          .clientIp("192.168.1.1")
          .browser("Chrome")
          .os("Windows")
          .build();
      consumer.accept(logEntry);
      return null;
    }).when(mockLogFileReader).readFile(eq(testPath), any(Consumer.class));

    logReportCreator.createLogReport(testPath, collectors);

    verify(mockLogFileReader).readFile(eq(testPath), any(Consumer.class));
    verify(mockCollector1).collect(any(LogEntry.class));
    verify(mockCollector2).collect(any(LogEntry.class));
    verify(mockCollector1).print();
    verify(mockCollector2).print();
  }

  @Test
  void shouldHandleNullLogEntries() {
    String testPath = "/test/path";
    List<LogCollectorReporter> collectors = List.of(mockCollector1);

    doAnswer(invocation -> {
      Consumer<LogEntry> consumer = invocation.getArgument(1);
      consumer.accept(null);
      return null;
    }).when(mockLogFileReader).readFile(eq(testPath), any(Consumer.class));

    logReportCreator.createLogReport(testPath, collectors);

    verify(mockLogFileReader).readFile(eq(testPath), any(Consumer.class));
    verify(mockCollector1, never()).collect(any(LogEntry.class));
    verify(mockCollector1).print();
  }

  @Test
  void shouldPrintReportsEvenWhenExceptionOccurs() {
    String testPath = "/test/path";
    List<LogCollectorReporter> collectors = List.of(mockCollector1);

    doThrow(new RuntimeException("Test exception"))
        .when(mockLogFileReader).readFile(anyString(), any(Consumer.class));

    logReportCreator.createLogReport(testPath, collectors);

    verify(mockCollector1).print();
  }
}
