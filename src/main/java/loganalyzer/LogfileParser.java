package loganalyzer;

import io.krakens.grok.api.Grok;
import io.krakens.grok.api.GrokCompiler;
import io.krakens.grok.api.Match;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import loganalyzer.model.LogEntry;
import loganalyzer.model.LogKeys;
import ua_parser.Client;
import ua_parser.Parser;

public class LogfileParser {

  private static final String GROK_HTTP_PATTERN = "%{IP:client_ip} %{DATA:ident} %{DATA:auth} \\[%{HTTPDATE:timestamp}\\] \"%{WORD:method} %{URIPATHPARAM:request} HTTP/%{NUMBER:http_version}\" %{NUMBER:response_code} %{NUMBER:bytes} \"%{DATA:referrer}\" \"%{DATA:user_agent}\" %{NUMBER:request_time:int} %{NUMBER:backend_time:int} %{DATA:dash} %{NUMBER:some_id:int}";
  private final LogFileReader logFileReader;
  private final Grok grok;
  private final Parser userAgentParser;

  public LogfileParser(LogFileReader logFileReader) {
    this.logFileReader = logFileReader;
    GrokCompiler compiler = GrokCompiler.newInstance();
    compiler.registerDefaultPatterns();
    this.grok = compiler.compile(GROK_HTTP_PATTERN);
    this.userAgentParser = new Parser();
  }

  public List<LogEntry> parseFile(String filePath) {
    List<String> logLines = logFileReader.readFile(filePath);
    if (logLines == null || logLines.isEmpty()) {
      return Collections.emptyList();
    }

    List<LogEntry> logEntries = new ArrayList<>();
    for (String line : logLines) {
      parse(line, logEntries);
    }

    return logEntries;
  }

  private void parse(String line, List<LogEntry> logEntries) {
    Match gm = grok.match(line);
    final Map<String, Object> capture = gm.capture();
    if (!capture.isEmpty()) {
      //System.out.println("Paring line");
      String userAgent = capture.get(LogKeys.USER_AGENT).toString();
      String client_ip = capture.get(LogKeys.CLIENT_IP).toString();
      Client client = userAgentParser.parse(userAgent);
      String os = client.os.family;
      String browser = client.userAgent.family;

      LogEntry logEntry = LogEntry.builder()
          .os(os)
          .clientIp(client_ip)
          .browser(browser)
          .build();

      logEntries.add(logEntry);
    }
  }
}
