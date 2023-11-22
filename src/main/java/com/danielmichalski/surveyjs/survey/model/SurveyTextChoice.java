package com.danielmichalski.surveyjs.survey.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class SurveyTextChoice extends SurveyChoice {

  public SurveyTextChoice(String value) {
    super(value);
  }

}
