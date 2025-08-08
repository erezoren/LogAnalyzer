package loganalyzer;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import loganalyzer.collectors.BrowserCollectorReporter;
import loganalyzer.collectors.LogCollectorReporter;
import loganalyzer.collectors.OsCollectorReporter;

public class LogFileProcessor {

  private static final String INPUT_FILE = "all.log";

  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    List<LogCollectorReporter> logCollectorReporters = List.of(new OsCollectorReporter(), new BrowserCollectorReporter());
    LogFileReader logFileReader = new LogFileReader(new LogfileParser(), executorService);
    LogReportCreator logReportCreator = new LogReportCreator(logFileReader);
    logReportCreator.createLogReport(INPUT_FILE, logCollectorReporters);
  }
}
