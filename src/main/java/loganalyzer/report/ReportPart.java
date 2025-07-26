package loganalyzer.report;

import java.util.List;

public abstract class ReportPart {

  abstract String getName();

  abstract List<ReportValue> getReport();

  void print() {
    System.out.println(String.format("%s:", getName()));
    getReport().forEach(rv -> System.out.println(rv.toString()));
  }

}
