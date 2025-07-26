package loganalyzer;

import java.util.List;
import loganalyzer.collectors.LogCollector;
import loganalyzer.model.LogEntry;
import loganalyzer.report.Report;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LogReportCreator {

  final LogfileParser logfileParser;

  public Report createLogreport(String path, List<LogCollector> logCollectors) {
    Report report = new Report();

    List<LogEntry> logEntries = logfileParser.parseFile(path);
    logEntries
        .forEach(le -> {
          logCollectors
              .forEach(la -> la.collect(le, report));
        });

    return report;
  }
}
