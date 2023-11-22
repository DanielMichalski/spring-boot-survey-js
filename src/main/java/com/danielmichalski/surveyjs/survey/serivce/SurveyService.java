package com.danielmichalski.surveyjs.survey.serivce;

import com.danielmichalski.surveyjs.survey.model.SurveyDefinition;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SurveyService {

  private static final String SURVEY_FILE_LOCATION = "classpath:survey.json";

  private final SurveyDefinition survey;

  public SurveyService(ObjectMapper objectMapper,
                       @Value(SURVEY_FILE_LOCATION) Resource surveyFile) {
    survey = fetchSurveyDefinition(objectMapper, surveyFile);
  }

  public SurveyDefinition getSurveyDefinition() {
    return survey;
  }

  private SurveyDefinition fetchSurveyDefinition(ObjectMapper objectMapper, Resource surveyFile) {
    try {
      return objectMapper.readValue(surveyFile.getFile(), SurveyDefinition.class);
    } catch (IOException e) {
      log.error("Exception while getting survey file", e);
      throw new IllegalStateException(e);
    }
  }

}
