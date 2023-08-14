package com.minnity.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportService {

  //task 1: Return number of requests that were made for each company. (e.g. companyId -> requestNumber)
  public Map<Integer, Long> calculateNumberOfRequestsPerCompany(List<RequestLog> requestLogs) {

    Map<Integer, Long> numberOfRequestsPerCompany = new HashMap<>();

    for (RequestLog log : requestLogs) {
      int companyId = log.getCompanyId();
      numberOfRequestsPerCompany.put(companyId, numberOfRequestsPerCompany.getOrDefault(companyId, 0L) + 1);
    }

    return numberOfRequestsPerCompany;
  }

  //task 2: Count and return requests per company that finished with an error HTTP response code (>=400)
  public Map<Integer, RequestLog> findRequestsWithError(List<RequestLog> requestLogs) {
    Map<Integer, RequestLog> requestsWithError = new HashMap<>();

    for (RequestLog log : requestLogs) {
      if (log.getRequestStatus() >= 400) {
        requestsWithError.put(log.getCompanyId(), log);
      }
    }
    return requestsWithError;
  }

  //task 3: find and print API (requests path) that on average takes the longest time to process the request.
  public String findRequestPathWithLongestDurationTime(List<RequestLog> requestLogs) {
    Map<String, List<Long>> pathToDurations = new HashMap<>();

    for (RequestLog log : requestLogs) {
      String requestPath = log.getRequestPath();
      long durationTime = log.getRequestDuration();

      if (!pathToDurations.containsKey(requestPath)) {
        pathToDurations.put(requestPath, new ArrayList<>());
      }

      pathToDurations.get(requestPath).add(durationTime);
    }

    String longestDurationPath = null;
    double longestAvgDuration = 0.0;

    for (Map.Entry<String, List<Long>> entry : pathToDurations.entrySet()) {
      List<Long> durations = entry.getValue();
      double avgDuration = durations.stream().mapToLong(Long::valueOf).average().orElse(0.0);

      if (avgDuration > longestAvgDuration) {
        longestAvgDuration = avgDuration;
        longestDurationPath = entry.getKey();
      }
    }

    return longestDurationPath;
  }
}
