package com.danielmichalski.surveyjs.survey.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public record SurveyDefinition(String logoPosition,
                               List<SurveyPage> pages) {

  public Map<String, SurveyElement> findRequiredSurveyElements() {
    return pages().stream()
        .map(SurveyPage::elements)
        .flatMap(Collection::stream)
        .filter(SurveyElement::isRequired)
        .collect(Collectors.toMap(SurveyElement::name, Function.identity()));
  }

}
