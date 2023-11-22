package com.danielmichalski.surveyjs.component.model;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record ComponentsRows(List<String> headers,
                             List<ComponentsRow> rows) {

  public List<Map<String, String>> getAllRowsWithAnswers(ComponentsRows components) {
    return components.rows().stream()
        .map(ComponentsRow::rowValues)
        .map(rowValues -> getSingleRowWithAnswers(components.headers(), rowValues))
        .toList();
  }

  private static Map<String, String> getSingleRowWithAnswers(List<String> headers, List<String> rowValues) {
    final Iterator<String> componentsHeadersIterator = headers.iterator();

    return rowValues.stream()
        .map(rowValue -> Map.entry(componentsHeadersIterator.next(), rowValue))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }
}
