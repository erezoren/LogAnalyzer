package loganalyzer.report;

import java.util.List;

public abstract class ReportPart {

  private final String bold = "\033[1m";
  private final String reset = "\033[0m";

  abstract String getName();

  abstract List<ReportValue> getReport();

  public void print() {
    System.out.println(String.format(bold + "%s:", getName() + reset));
    getReport().forEach(rv -> System.out.println(rv.toString()));
  }
}
