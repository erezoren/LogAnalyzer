package loganalyzer.collectors;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import loganalyzer.model.LogEntry;
import loganalyzer.report.ClientIpReportPart;
import loganalyzer.report.ReportPart;

public class ClientIpCollectorReporter extends LogCollectorReporter {

  private final Map<String, Integer> collector;

  protected ClientIpCollectorReporter() {
    collector = new ConcurrentHashMap<>();
  }

  @Override
  public void collect(LogEntry logEntry) {
    String clientIp = logEntry.getClientIp();
    if (clientIp != null) {
      collect(clientIp, collector);
    }
  }

  @Override
  public void printReport() {
    ReportPart reportPart = new ClientIpReportPart(collector);
    reportPart.print();
  }

  @Override
  public String analyzerName() {
    return "Client IP Collector Reporter";
  }
}
