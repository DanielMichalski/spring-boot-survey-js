package com.danielmichalski.surveyjs.common.errors;

public record ValidationError(String columnName,
                              String message) {

}
