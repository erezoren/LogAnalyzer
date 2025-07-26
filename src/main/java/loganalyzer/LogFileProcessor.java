package loganalyzer;

import java.util.List;
import loganalyzer.collectors.LogCollectorReporter;
import loganalyzer.collectors.OsCollectorReporter;
import loganalyzer.collectors.BrowserCollectorReporter;

public class LogFileProcessor {

  private static final String PATH = "/Users/erezoren/Downloads/LogAnalyzer/src/main/java/loganalyzer/all.log";

  public static void main(String[] args) {
    List<LogCollectorReporter> logCollectorReporters = List.of(new OsCollectorReporter(), new BrowserCollectorReporter());
    LogFileReader logFileReader = new LogFileReader(new LogfileParser());
    LogReportCreator logReportCreator = new LogReportCreator(logFileReader);
    logReportCreator.createLogReport(PATH, logCollectorReporters);
  }
}
