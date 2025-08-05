package loganalyzer.report;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReportValueTest {

  @Test
  void shouldCreateReportValue() {
    ReportValue reportValue = new ReportValue("Chrome", 0.45);

    assertEquals("Chrome", reportValue.name);
    assertEquals(0.45, reportValue.percentage);
  }

  @Test
  void shouldFormatToStringCorrectly() {
    ReportValue reportValue = new ReportValue("Firefox", 0.35);

    assertEquals("Firefox-0.35%", reportValue.toString());
  }

  @Test
  void shouldHandleZeroPercentage() {
    ReportValue reportValue = new ReportValue("Safari", 0.0);

    assertEquals("Safari-0.0%", reportValue.toString());
  }

  @Test
  void shouldHandleWholeNumberPercentage() {
    ReportValue reportValue = new ReportValue("Edge", 1.0);

    assertEquals("Edge-1.0%", reportValue.toString());
  }
}
