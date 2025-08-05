package loganalyzer.report;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OsReportPartTest {

  private Map<String, Integer> testData;
  private OsReportPart reportPart;

  @BeforeEach
  void setUp() {
    testData = new HashMap<>();
    testData.put("Windows", 60);
    testData.put("macOS", 25);
    testData.put("Linux", 15);

    reportPart = new OsReportPart(testData);
  }

  @Test
  void shouldReturnCorrectName() {
    assertEquals("Operating System", reportPart.getName());
  }

  @Test
  void shouldCalculatePercentagesCorrectly() {
    List<ReportValue> report = reportPart.getReport();

    assertEquals(3, report.size());

    // Should be sorted by percentage descending
    assertEquals("Windows", report.get(0).name);
    assertEquals(0.6, report.get(0).percentage); // 60/100 = 0.6

    assertEquals("macOS", report.get(1).name);
    assertEquals(0.25, report.get(1).percentage); // 25/100 = 0.25

    assertEquals("Linux", report.get(2).name);
    assertEquals(0.15, report.get(2).percentage); // 15/100 = 0.15
  }
}
