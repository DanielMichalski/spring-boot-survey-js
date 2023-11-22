package com.danielmichalski.surveyjs.survey.model;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SurveyFieldType {
  TEXT("text"),
  RADIO_GROUP("radiogroup"),
  DROPDOWN("dropdown");

  private final String name;

  public static SurveyFieldType fromName(String name) {
    return Arrays.stream(SurveyFieldType.values())
        .filter(fieldType -> fieldType.name.equals(name))
        .findAny()
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("Failed to get survey field type for name %s", name)
        ));
  }

}
