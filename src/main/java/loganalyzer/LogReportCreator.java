package loganalyzer;

import java.util.List;
import loganalyzer.collectors.LogCollectorReporter;
import loganalyzer.model.LogEntry;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LogReportCreator {

  final LogFileReader logFileReader;

  public void createLogReport(String path, List<LogCollectorReporter> logCollectorReporters) {
    logFileReader.readFile(path,
        logEntry -> {
          if (logEntry != null) {
            logCollectorReporters.forEach(collector -> collector.collect(logEntry));
          }
        });

    logCollectorReporters.forEach(LogCollectorReporter::print);
  }
}
