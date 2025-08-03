package loganalyzer;

import java.util.List;
import loganalyzer.collectors.LogCollectorReporter;
import loganalyzer.model.LogEntry;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LogReportCreator {

  final LogFileReader logFileReader;

  public void createLogReport(String path, List<LogCollectorReporter> logCollectorReporters) {
    try {
      logFileReader.readFile(path,
          logEntry -> {
            if (logEntry != null) {
              logCollectorReporters.forEach(collector -> collector.collect(logEntry));
            }
          });
    } catch (Exception ex) {
      System.err.println("Error handling log file: " + ex.getMessage());
    } finally {
      logCollectorReporters.forEach(LogCollectorReporter::print);
    }
  }
}
