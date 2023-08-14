package com.minnity.report;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class ReportServiceTest {

  @Test
  public void testCalculateNumberOfRequestsPerCompany() {
    RequestLog log1 = mock(RequestLog.class);
    when(log1.getCompanyId()).thenReturn(1);

    RequestLog log2 = mock(RequestLog.class);
    when(log2.getCompanyId()).thenReturn(2);

    RequestLog log3 = mock(RequestLog.class);
    when(log3.getCompanyId()).thenReturn(1);

    RequestLog log4 = mock(RequestLog.class);
    when(log4.getCompanyId()).thenReturn(3);

    List<RequestLog> requestLogs = new ArrayList<>();
    requestLogs.add(log1);
    requestLogs.add(log2);
    requestLogs.add(log3);
    requestLogs.add(log4);

    ReportService reportService = new ReportService();
    Map<Integer, Long> result = reportService.calculateNumberOfRequestsPerCompany(requestLogs);

    assertEquals((Long) 2L, result.get(1));
    assertEquals((Long) 1L, result.get(2));
    assertEquals((Long) 1L, result.get(3));
  }

  @Test
  public void testFindRequestsWithError() {
    RequestLog log1 = mock(RequestLog.class);
    when(log1.getCompanyId()).thenReturn(1);
    when(log1.getRequestStatus()).thenReturn(200);

    RequestLog log2 = mock(RequestLog.class);
    when(log2.getCompanyId()).thenReturn(2);
    when(log2.getRequestStatus()).thenReturn(500);

    RequestLog log3 = mock(RequestLog.class);
    when(log3.getCompanyId()).thenReturn(1);
    when(log3.getRequestStatus()).thenReturn(400);

    RequestLog log4 = mock(RequestLog.class);
    when(log4.getCompanyId()).thenReturn(3);
    when(log4.getRequestStatus()).thenReturn(403);

    List<RequestLog> requestLogs = new ArrayList<>();
    requestLogs.add(log1);
    requestLogs.add(log2);
    requestLogs.add(log3);
    requestLogs.add(log4);

    ReportService reportService = new ReportService();
    Map<Integer, RequestLog> result = reportService.findRequestsWithError(requestLogs);

    assertEquals(3, result.size());
    assertEquals(log2, result.get(2));
    assertEquals(log3, result.get(1));

  }

  @Test
  public void testFindRequestPathWithLongestDurationTime() {
    RequestLog log1 = mock(RequestLog.class);
    when(log1.getRequestPath()).thenReturn("/api/user/orders");
    when(log1.getRequestDuration()).thenReturn(1000L);

    RequestLog log2 = mock(RequestLog.class);
    when(log2.getRequestPath()).thenReturn("/api/user/details");
    when(log2.getRequestDuration()).thenReturn(1000L);

    RequestLog log3 = mock(RequestLog.class);
    when(log3.getRequestPath()).thenReturn("/api/user/orders");
    when(log3.getRequestDuration()).thenReturn(1500L);

    RequestLog log4 = mock(RequestLog.class);
    when(log4.getRequestPath()).thenReturn("/api/user/id");
    when(log4.getRequestDuration()).thenReturn(800L);

    List<RequestLog> requestLogs = new ArrayList<>();
    requestLogs.add(log1);
    requestLogs.add(log2);
    requestLogs.add(log3);
    requestLogs.add(log4);

    ReportService reportService = new ReportService();
    String result = reportService.findRequestPathWithLongestDurationTime(requestLogs);

    assertEquals("/api/user/orders", result);

  }
}