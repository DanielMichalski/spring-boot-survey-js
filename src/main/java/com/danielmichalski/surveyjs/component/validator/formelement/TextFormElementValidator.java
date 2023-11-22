package com.danielmichalski.surveyjs.component.validator.formelement;

import com.danielmichalski.surveyjs.common.errors.ValidationError;
import com.danielmichalski.surveyjs.survey.model.SurveyFieldType;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class TextFormElementValidator implements FormElementValidator {

  @Override
  public Optional<ValidationError> validate(String surveyFormEntryKey,
                                            Set<String> availableValues,
                                            String responseValue) {
    boolean isEmptyResponse = responseValue == null || responseValue.isEmpty();

    return isEmptyResponse
           ? buildValidationError(surveyFormEntryKey)
           : Optional.empty();
  }

  @Override
  public boolean isApplicable(SurveyFieldType surveyFieldType) {
    return surveyFieldType == SurveyFieldType.TEXT;
  }

  private static Optional<ValidationError> buildValidationError(String surveyFormEntryKey) {
    ValidationError validationError = new ValidationError(surveyFormEntryKey, "This field is required");
    return Optional.of(validationError);
  }
}
