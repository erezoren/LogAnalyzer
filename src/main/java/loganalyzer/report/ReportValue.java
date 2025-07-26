package loganalyzer.report;

import java.util.Comparator;

public class ReportValue {

  String name;
  double percentage;

  public ReportValue(String name, double percentage) {
    this.name = name;
    this.percentage = percentage;
  }

  @Override
  public String toString() {
    return name + "-" + percentage + "%";
  }
}
