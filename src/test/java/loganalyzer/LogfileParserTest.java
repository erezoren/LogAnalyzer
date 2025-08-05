package loganalyzer;

import loganalyzer.model.LogEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogfileParserTest {

  private LogfileParser parser;

  @BeforeEach
  void setUp() {
    parser = new LogfileParser();
  }

  @Test
  void shouldParseValidLogLine() {
    String logLine = "192.168.1.1 - - [10/Oct/2000:13:55:36 -0700] \"GET /apache_pb.gif HTTP/1.0\" 200 2326 \"http://www.example.com/start.html\" \"Mozilla/4.08 [en] (Win98; I ;Nav)\" 100 200 - 12345";

    LogEntry result = parser.parseLine(logLine);

    assertNotNull(result);
    assertEquals("192.168.1.1", result.getClientIp());
    assertNotNull(result.getBrowser());
    assertNotNull(result.getOs());
  }

  @Test
  void shouldReturnNullForInvalidLogLine() {
    String invalidLogLine = "invalid log line format";

    LogEntry result = parser.parseLine(invalidLogLine);

    assertNull(result);
  }

  @Test
  void shouldParseUserAgentCorrectly() {
    String logLine = "127.0.0.1 - - [10/Oct/2000:13:55:36 -0700] \"GET /test HTTP/1.0\" 200 1234 \"-\" \"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36\" 50 100 - 67890";

    LogEntry result = parser.parseLine(logLine);

    assertNotNull(result);
    assertEquals("127.0.0.1", result.getClientIp());
    assertTrue(result.getBrowser().contains("Chrome") || result.getBrowser().contains("Mozilla"));
    assertTrue(result.getOs().contains("Windows") || result.getOs().contains("Other"));
  }

  @Test
  void shouldHandleEmptyLine() {
    String emptyLine = "";

    LogEntry result = parser.parseLine(emptyLine);

    assertNull(result);
  }

  @Test
  void shouldHandleNullLine() {
    LogEntry result = parser.parseLine(null);

    assertNull(result);
  }
}
