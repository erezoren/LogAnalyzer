package loganalyzer.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogEntry {
  String os;
  String clientIp; ///Country
  String browser;
}
