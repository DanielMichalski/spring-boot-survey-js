package com.danielmichalski.surveyjs.common.errors;

import java.util.List;
import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

  private final List<ValidationError> validationErrors;

  public ValidationException(String message, List<ValidationError> validationErrors) {
    super(message);
    this.validationErrors = validationErrors;
  }
}
