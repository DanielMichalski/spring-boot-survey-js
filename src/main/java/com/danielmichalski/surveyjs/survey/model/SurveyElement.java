package com.danielmichalski.surveyjs.survey.model;

import static java.util.Optional.ofNullable;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

public record SurveyElement(String type,
                            String name,
                            String visibleIf,
                            SurveyTextTranslation title,
                            SurveyTextTranslation description,
                            boolean isRequired,
                            List<SurveyChoice> choices) {

  private static final String FIELD_NAME_OPEN_CHARACTER = "{";
  private static final String FIELD_NAME_CLOSE_CHARACTER = "}";
  private static final String FIELD_VALUE_OPEN_CHARACTER = "'";
  private static final String FIELD_VALUE_CLOSE_CHARACTER = "'";

  public Set<String> getAllChoicesValues() {
    return ofNullable(choices).stream()
        .flatMap(Collection::stream)
        .map(SurveyChoice::getValue)
        .collect(Collectors.toSet());
  }

  public boolean visibleIfFieldDefined() {
    return StringUtils.isNotEmpty(visibleIf());
  }

  public String visibleIfFieldName() {
    return StringUtils.substringBetween(visibleIf, FIELD_NAME_OPEN_CHARACTER, FIELD_NAME_CLOSE_CHARACTER);
  }

  public String visibleIfValue() {
    return StringUtils.substringBetween(visibleIf, FIELD_VALUE_OPEN_CHARACTER, FIELD_VALUE_CLOSE_CHARACTER);
  }

}
