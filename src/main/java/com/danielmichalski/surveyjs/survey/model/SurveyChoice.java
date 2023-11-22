package com.danielmichalski.surveyjs.survey.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonDeserialize(using = SurveyChoiceDeserializer.class)
@Getter
@AllArgsConstructor
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class SurveyChoice {

  private String value;

}
