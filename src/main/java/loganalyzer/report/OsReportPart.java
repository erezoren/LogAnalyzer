package loganalyzer.report;

import java.util.Map;

public class OsReportPart extends ReportPart {

  public OsReportPart(Map<String, Integer> rawReport) {
    super(rawReport);
  }

  @Override
  public String getName() {
    return "Operating System";
  }
}
