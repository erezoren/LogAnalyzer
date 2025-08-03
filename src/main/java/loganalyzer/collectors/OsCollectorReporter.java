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
    String os = logEntry.getOs();
    if (os != null) {
      collect(os, collector);
    }
  }

  @Override
  public void printReport() {
    ReportPart reportPart = new OsReportPart(collector);
    reportPart.print();
  }

  @Override
  public String analyzerName() {
    return "OS Collector Reporter";
  }
}
