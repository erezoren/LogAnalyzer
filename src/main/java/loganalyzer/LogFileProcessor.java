package loganalyzer;

import java.util.List;
import loganalyzer.collectors.LogCollector;
import loganalyzer.collectors.OsCollector;
import loganalyzer.collectors.BrowserCollector;
import loganalyzer.report.Report;

public class LogFileProcessor {

  private static final String PATH = "/Users/erezoren/Downloads/LogAnalyzer/src/main/java/loganalyzer/all.log";

  public static void main(String[] args) {
    List<LogCollector> logCollectors = List.of(new OsCollector(), new BrowserCollector());
    LogFileReader logFileReader = new LogFileReader();
    LogfileParser logfileParser = new LogfileParser(logFileReader);
    LogReportCreator logReportCreator = new LogReportCreator(logfileParser);

    Report logreport = logReportCreator.createLogreport(PATH, logCollectors);


    logreport.printReport();
  }
}
