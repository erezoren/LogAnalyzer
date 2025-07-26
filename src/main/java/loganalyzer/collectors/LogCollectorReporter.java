package loganalyzer.collectors;

import loganalyzer.model.LogEntry;

public abstract class LogCollectorReporter {

  public abstract void collect(LogEntry logEntry);

  public abstract void printReport();

  public void print() {
    System.out.println("");
    printReport();
  }
}
