package com.danielmichalski.surveyjs.survey.model;

import java.util.List;

public record SurveyPage(String name,
                         List<SurveyElement> elements) {

}
