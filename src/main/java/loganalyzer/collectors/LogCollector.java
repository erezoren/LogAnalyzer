package loganalyzer.collectors;

import loganalyzer.model.LogEntry;
import loganalyzer.report.Report;

public interface LogCollector {

  void collect(LogEntry logEntry, Report report);

}
