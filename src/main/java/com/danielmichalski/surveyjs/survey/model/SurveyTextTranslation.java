package com.danielmichalski.surveyjs.survey.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SurveyTextTranslation(@JsonProperty("default") String defaultText,
                                    String de) {

}
