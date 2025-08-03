package loganalyzer.collectors;

import java.util.Map;
import loganalyzer.model.LogEntry;

public abstract class LogCollectorReporter {

  public abstract void collect(LogEntry logEntry);

  public abstract void printReport();

  public abstract String analyzerName();

  public void print() {
    System.out.println("");
    printReport();
  }

  void collect(String value, Map<String, Integer> collector) {
    collector.merge(value, 1, Integer::sum);
    System.out.print("\r" + " " + analyzerName() + ":" + value + "-> " + collector.get(value));
  }
}
