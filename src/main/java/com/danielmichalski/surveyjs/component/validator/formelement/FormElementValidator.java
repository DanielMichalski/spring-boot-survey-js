package com.danielmichalski.surveyjs.component.validator.formelement;

import com.danielmichalski.surveyjs.common.errors.ValidationError;
import com.danielmichalski.surveyjs.survey.model.SurveyFieldType;
import java.util.Optional;
import java.util.Set;

public interface FormElementValidator {

  Optional<ValidationError> validate(String surveyFormEntryKey,
                                     Set<String> availableValues,
                                     String responseValue);

  boolean isApplicable(SurveyFieldType surveyFieldType);
}
