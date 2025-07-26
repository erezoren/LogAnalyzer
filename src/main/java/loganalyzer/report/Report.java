package loganalyzer.report;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import loganalyzer.model.LogKeys;

public class Report {

  private final Map<String, Integer> osCollector = new ConcurrentHashMap<>();
  private final Map<String, Integer> ipCollector = new ConcurrentHashMap<>();
  private final Map<String, Integer> browserCollector = new ConcurrentHashMap<>();

  public void incrementOs(String os) {
    increment(osCollector, os);
  }

  public void incrementClientIp(String clientIp) {
    increment(ipCollector, clientIp);
  }

  public void incrementBrowser(String request) {
    increment(browserCollector, request);
  }

  void increment(Map<String, Integer> collector, String value) {
    collector.merge(value, 1, Integer::sum);
  }

  public void printReport() {
    ReportPart osReportPart = new OsReportPart(osCollector);
    osReportPart.print();
    System.out.println("");
    ReportPart browserReportPart = new BrowserReportPart(browserCollector);
    browserReportPart.print();
  }
}
