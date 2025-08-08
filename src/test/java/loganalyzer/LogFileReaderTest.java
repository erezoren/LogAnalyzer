package loganalyzer;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import loganalyzer.model.LogEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LogFileReaderTest {

  @Mock
  private LogfileParser mockParser;
  @Mock
  private Consumer<LogEntry> mockConsumer;
  private LogFileReader logFileReader;
  private ExecutorService executorService;
  @TempDir
  Path tempDir;

  @BeforeEach
  void setUp() {
    executorService = Executors.newFixedThreadPool(2);
    logFileReader = new LogFileReader(mockParser, executorService);
  }

  @Test
  void shouldReadFileAndParseLines() throws IOException {

    LogEntry mockLogEntry = LogEntry.builder()
        .clientIp("192.168.1.1")
        .browser("Chrome")
        .os("Windows")
        .build();

    when(mockParser.parseLine(anyString())).thenReturn(mockLogEntry);

    logFileReader.readFile("sml.log", mockConsumer);

    verify(mockParser, times(10)).parseLine(anyString());
    verify(mockConsumer, times(10)).accept(mockLogEntry);
  }

  @Test
  void shouldHandleNullLogEntries() throws IOException {
    String logFile = "sml.log";

    when(mockParser.parseLine(anyString())).thenReturn(null);

    logFileReader.readFile(logFile.toString(), mockConsumer);

    verify(mockParser, times(10)).parseLine(anyString());
    verify(mockConsumer, times(10)).accept(null);
  }

  @Test
  void shouldThrowRuntimeExceptionForNonExistentFile() {
    String nonExistentFile = "/path/to/nonexistent/file.log";

    assertThrows(RuntimeException.class, () -> {
      logFileReader.readFile(nonExistentFile, mockConsumer);
    });
  }

  @Test
  void shouldHandleEmptyFile() throws IOException {
    try {
      logFileReader.readFile("empty.log", mockConsumer);
      fail("Should not have reached here");
    } catch (Exception e) {
      verify(mockParser, never()).parseLine(anyString());
      verify(mockConsumer, never()).accept(any());
    }
  }
}
