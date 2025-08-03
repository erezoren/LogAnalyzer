package loganalyzer.collectors;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import loganalyzer.model.LogEntry;
import loganalyzer.report.BrowserReportPart;
import loganalyzer.report.ReportPart;

public class BrowserCollectorReporter extends LogCollectorReporter {

  private final Map<String, Integer> collector;

  public BrowserCollectorReporter() {
    collector = new ConcurrentHashMap<>();
  }

  @Override
  public void collect(LogEntry logEntry) {
    String browser = logEntry.getBrowser();
    if (browser != null) {
      collect(browser, collector);
    }
  }

  @Override
  public void printReport() {
    ReportPart reportPart = new BrowserReportPart(collector);
    reportPart.print();
  }

  @Override
  public String analyzerName() {
    return "Browser Collector Reporter";
  }
}
