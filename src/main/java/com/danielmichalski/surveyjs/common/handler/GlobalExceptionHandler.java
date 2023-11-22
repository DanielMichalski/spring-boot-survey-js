package com.danielmichalski.surveyjs.common.handler;

import com.danielmichalski.surveyjs.common.errors.ErrorResponse;
import com.danielmichalski.surveyjs.common.errors.FileException;
import com.danielmichalski.surveyjs.common.errors.ValidationError;
import com.danielmichalski.surveyjs.common.errors.ValidationException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ResponseBody
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponse handleException(Exception ex) {
    log.error(ex.getMessage(), ex);
    return ErrorResponse.builder()
        .code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .message("Unexpected error")
        .build();
  }

  @ResponseBody
  @ExceptionHandler(FileException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleFileException(FileException ex) {
    log.error(ex.getMessage(), ex);
    return ErrorResponse.builder()
        .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
        .status(HttpStatus.BAD_REQUEST.value())
        .message(ex.getMessage())
        .build();
  }

  @ResponseBody
  @ExceptionHandler(ValidationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<List<ValidationError>> handleValidationException(ValidationException ex) {
    return ResponseEntity.badRequest()
        .body(ex.getValidationErrors());
  }

}
