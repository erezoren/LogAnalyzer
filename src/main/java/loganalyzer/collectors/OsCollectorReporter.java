package loganalyzer.collectors;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import loganalyzer.model.LogEntry;
import loganalyzer.report.OsReportPart;
import loganalyzer.report.ReportPart;

public class OsCollectorReporter extends LogCollectorReporter {

  private final Map<String, Integer> collector;

  public OsCollectorReporter() {
    collector = new ConcurrentHashMap<>();
  }

  @Override
  public void collect(LogEntry logEntry) {
    if (logEntry.getOs() != null) {
      collector.merge(logEntry.getOs(), 1, Integer::sum);
    }
  }

  @Override
  public void printReport() {
    ReportPart reportPart = new OsReportPart(collector);
    reportPart.print();
  }
}
