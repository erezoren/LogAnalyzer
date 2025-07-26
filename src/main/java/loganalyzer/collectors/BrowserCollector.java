package loganalyzer.collectors;

import loganalyzer.model.LogEntry;
import loganalyzer.report.Report;

public class BrowserCollector implements LogCollector {

  @Override
  public void collect(LogEntry logEntry, Report report) {
    if (logEntry.getBrowser()!= null) {
      report.incrementBrowser(logEntry.getBrowser());
    }
  }
}
