package loganalyzer.report;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class OsReportPart extends ReportPart {

  final Map<String, Integer> rawReport;

  public OsReportPart(Map<String, Integer> rawReport) {
    this.rawReport = rawReport;
  }

  @Override
  public String getName() {
    return "Operating System";
  }

  @Override
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
