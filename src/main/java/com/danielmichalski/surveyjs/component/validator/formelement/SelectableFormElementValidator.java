package com.danielmichalski.surveyjs.component.validator.formelement;

import static java.lang.String.format;

import com.danielmichalski.surveyjs.common.errors.ValidationError;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public abstract class SelectableFormElementValidator implements FormElementValidator {

  @Override
  public Optional<ValidationError> validate(String surveyFormEntryKey,
                                            Set<String> availableValues,
                                            String responseValue) {
    return !availableValues.contains(responseValue)
           ? buildValidationError(surveyFormEntryKey, availableValues, responseValue)
           : Optional.empty();
  }

  private static Optional<ValidationError> buildValidationError(String surveyFormEntryKey,
                                                                Set<String> availableValues,
                                                                String responseValue) {
    ValidationError validationError = new ValidationError(
        surveyFormEntryKey,
        format("Answer [%s] is incorrect. Valid answers: %s", responseValue, availableValues)
    );
    return Optional.of(validationError);
  }
}
