package loganalyzer.report;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public abstract class ReportPart {

  private final String bold = "\033[1m";
  private final String reset = "\033[0m";
  final Map<String, Integer> rawReport;

  protected ReportPart(Map<String, Integer> rawReport) {
    this.rawReport = rawReport;
  }

  abstract String getName();

  public void print() {
    System.out.println(String.format(bold + "%s:", getName() + reset));
    getReport().forEach(rv -> System.out.println(rv.toString()));
  }

  public List<ReportValue> getReport() {
    int totalOperatingSystems = rawReport.values().stream().mapToInt(Integer::intValue).sum();
    List<ReportValue> reportValues = rawReport.entrySet().stream().map(es -> {
      double precent = (double) es.getValue() / totalOperatingSystems;
      double rounded = Math.round(precent * 100.0) / 100.0;
      return new ReportValue(es.getKey(), rounded);
    }).sorted(Comparator.comparingDouble((ReportValue r) -> r.percentage).reversed()).toList();

    return reportValues;
  }
}
