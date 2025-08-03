package loganalyzer;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import loganalyzer.collectors.LogCollectorReporter;
import loganalyzer.collectors.OsCollectorReporter;
import loganalyzer.collectors.BrowserCollectorReporter;

public class LogFileProcessor {

  private static final String PATH = "/Users/erezoren/Downloads/LogAnalyzer/src/main/java/loganalyzer/all.log";

  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    List<LogCollectorReporter> logCollectorReporters = List.of(new OsCollectorReporter(), new BrowserCollectorReporter());
    LogFileReader logFileReader = new LogFileReader(new LogfileParser(), executorService);
    LogReportCreator logReportCreator = new LogReportCreator(logFileReader);
    logReportCreator.createLogReport(PATH, logCollectorReporters);
  }
}
