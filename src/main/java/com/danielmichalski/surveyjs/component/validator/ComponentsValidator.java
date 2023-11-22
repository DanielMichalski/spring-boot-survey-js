package com.danielmichalski.surveyjs.component.validator;

import static java.lang.String.format;

import com.danielmichalski.surveyjs.common.errors.ValidationError;
import com.danielmichalski.surveyjs.common.errors.ValidationException;
import com.danielmichalski.surveyjs.component.model.ComponentsRows;
import com.danielmichalski.surveyjs.component.validator.formelement.FormElementValidator;
import com.danielmichalski.surveyjs.survey.model.SurveyDefinition;
import com.danielmichalski.surveyjs.survey.model.SurveyElement;
import com.danielmichalski.surveyjs.survey.model.SurveyFieldType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ComponentsValidator {

  private final Set<FormElementValidator> formElementValidators;

  public void validate(SurveyDefinition survey, ComponentsRows components) {
    Map<String, SurveyElement> requiredSurveyElementsByNames = survey.findRequiredSurveyElements();
    List<Map<String, String>> rowsWithAnswers = components.getAllRowsWithAnswers(components);
    validateRows(requiredSurveyElementsByNames, rowsWithAnswers);
  }

  private void validateRows(Map<String, SurveyElement> surveyElementsByNames,
                            List<Map<String, String>> rowsWithAnswers) {
    List<ValidationError> validationErrors = findAllValidationErrors(surveyElementsByNames, rowsWithAnswers);

    if (!validationErrors.isEmpty()) {
      throw new ValidationException("Validation exception", validationErrors);
    }
  }

  private List<ValidationError> findAllValidationErrors(Map<String, SurveyElement> surveyElementsByNames,
                                                        List<Map<String, String>> rowsWithAnswers) {
    return rowsWithAnswers.stream()
        .map(resultRow -> findValidationErrorsForRow(surveyElementsByNames, resultRow))
        .flatMap(Collection::stream)
        .toList();
  }

  private List<ValidationError> findValidationErrorsForRow(Map<String, SurveyElement> surveyElementsByNames,
                                                           Map<String, String> rowWithAnswers) {
    return surveyElementsByNames.entrySet().stream()
        .map(surveyElementEntry -> getValidationError(surveyElementEntry, rowWithAnswers))
        .filter(Objects::nonNull)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .toList();
  }

  private Optional<ValidationError> getValidationError(Map.Entry<String, SurveyElement> surveyElementEntry,
                                                       Map<String, String> rowWithAnswers) {
    String surveyEntryKey = surveyElementEntry.getKey();
    SurveyElement surveyEntryValue = surveyElementEntry.getValue();
    String csvResponseValue = rowWithAnswers.get(surveyEntryKey);

    if (surveyEntryValue.visibleIfFieldDefined()) {
      String visibleIfFieldName = surveyEntryValue.visibleIfFieldName();
      String value = surveyEntryValue.visibleIfValue();

      if (!rowWithAnswers.get(visibleIfFieldName).equals(value)) {
        return Optional.empty();
      }
    }

    SurveyFieldType surveyFieldType = SurveyFieldType.fromName(surveyEntryValue.type());

    return formElementValidators.stream()
        .filter(validator -> validator.isApplicable(surveyFieldType))
        .findFirst()
        .map(validator -> validator.validate(surveyEntryKey, surveyEntryValue.getAllChoicesValues(), csvResponseValue))
        .orElseThrow(() -> new IllegalArgumentException(format(
            "There's no validator for field type %s",
            surveyFieldType
        )));
  }


}