package loganalyzer.collectors;

import loganalyzer.model.LogEntry;
import loganalyzer.report.Report;

public class ClientIpCollector implements LogCollector {

  @Override
  public void collect(LogEntry logEntry, Report report) {
    if (logEntry.getClientIp() != null) {
      report.incrementClientIp(logEntry.getClientIp());
    }
  }

}
