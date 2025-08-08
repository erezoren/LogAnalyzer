package loganalyzer.report;

import java.util.Map;

public class ClientIpReportPart extends ReportPart {

  public ClientIpReportPart(Map<String, Integer> rawReport) {
    super(rawReport);
  }

  @Override
  public String getName() {
    return "Country by Client IP";
  }

}
