package com.danielmichalski.surveyjs.common.errors;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {

  private final String message;
  private final int status;
  private final String code;
}
