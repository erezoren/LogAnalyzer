package loganalyzer.collectors;

import loganalyzer.model.LogEntry;
import loganalyzer.report.Report;

public class OsCollector implements LogCollector {

  @Override
  public void collect(LogEntry logEntry, Report report) {
    if (logEntry.getOs() != null) {
      report.incrementOs(logEntry.getOs());
    }
  }
}
