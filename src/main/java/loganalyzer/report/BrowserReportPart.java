package loganalyzer.report;

import java.util.Map;

public class BrowserReportPart extends ReportPart {

  public BrowserReportPart(Map<String, Integer> rawReport) {
    super(rawReport);
  }

  @Override
  public String getName() {
    return "Browser";
  }
}
