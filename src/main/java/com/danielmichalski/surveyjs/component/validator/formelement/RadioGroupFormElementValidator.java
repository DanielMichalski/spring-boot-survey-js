package com.danielmichalski.surveyjs.component.validator.formelement;

import com.danielmichalski.surveyjs.survey.model.SurveyFieldType;
import org.springframework.stereotype.Component;

@Component
public class RadioGroupFormElementValidator extends SelectableFormElementValidator {

  @Override
  public boolean isApplicable(SurveyFieldType surveyFieldType) {
    return surveyFieldType == SurveyFieldType.RADIO_GROUP;
  }

}
