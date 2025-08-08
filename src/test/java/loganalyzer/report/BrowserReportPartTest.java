package loganalyzer.report;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BrowserReportPartTest {

  private Map<String, Integer> testData;
  private BrowserReportPart reportPart;
  private ByteArrayOutputStream outputStream;
  private PrintStream originalOut;

  @BeforeEach
  void setUp() {
    testData = new HashMap<>();
    testData.put("Chrome", 50);
    testData.put("Firefox", 30);
    testData.put("Safari", 20);

    reportPart = new BrowserReportPart(testData);
    outputStream = new ByteArrayOutputStream();
    originalOut = System.out;
    System.setOut(new PrintStream(outputStream));
  }

  @Test
  void shouldReturnCorrectName() {
    assertEquals("Browser", reportPart.getName());
  }

  @Test
  void shouldCalculatePercentagesCorrectly() {
    List<ReportValue> report = reportPart.getReport();

    assertEquals(3, report.size());

    assertEquals("Chrome", report.get(0).name);
    assertEquals(0.5, report.get(0).percentage); // 50/100 = 0.5

    assertEquals("Firefox", report.get(1).name);
    assertEquals(0.3, report.get(1).percentage); // 30/100 = 0.3

    assertEquals("Safari", report.get(2).name);
    assertEquals(0.2, report.get(2).percentage); // 20/100 = 0.2
  }

  @Test
  void shouldSortByPercentageDescending() {
    Map<String, Integer> unsortedData = new HashMap<>();
    unsortedData.put("Edge", 10);
    unsortedData.put("Chrome", 80);
    unsortedData.put("Firefox", 10);

    BrowserReportPart unsortedReportPart = new BrowserReportPart(unsortedData);
    List<ReportValue> report = unsortedReportPart.getReport();

    assertEquals("Chrome", report.get(0).name);
    assertEquals(0.8, report.get(0).percentage);
  }

  @Test
  void shouldPrintReportCorrectly() {
    reportPart.print();

    String output = outputStream.toString();
    assertTrue(output.contains("Browser"));
    assertTrue(output.contains("Chrome-0.5%"));
    assertTrue(output.contains("Firefox-0.3%"));
    assertTrue(output.contains("Safari-0.2%"));
  }

  @Test
  void shouldHandleEmptyData() {
    BrowserReportPart emptyReportPart = new BrowserReportPart(new HashMap<>());
    List<ReportValue> report = emptyReportPart.getReport();

    assertTrue(report.isEmpty());
  }

  void tearDown() {
    System.setOut(originalOut);
  }
}
