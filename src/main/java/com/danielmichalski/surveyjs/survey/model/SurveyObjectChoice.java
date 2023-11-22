package com.danielmichalski.surveyjs.survey.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class SurveyObjectChoice extends SurveyChoice {

  private final SurveyTextTranslation text;

  public SurveyObjectChoice(String value, SurveyTextTranslation text) {
    super(value);
    this.text = text;
  }
}
