package com.danielmichalski.surveyjs.component.service;

import com.danielmichalski.surveyjs.component.model.ComponentsRows;
import com.danielmichalski.surveyjs.component.validator.ComponentsFileValidator;
import com.danielmichalski.surveyjs.component.validator.ComponentsValidator;
import com.danielmichalski.surveyjs.survey.model.SurveyDefinition;
import com.danielmichalski.surveyjs.survey.serivce.SurveyService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComponentsValidationService {

  private final SurveyService surveyService;
  private final ComponentsFileValidator fileValidator;
  private final ComponentsCsvParserService csvParserService;
  private final ComponentsValidator componentsValidator;

  public void validate(MultipartFile file) {
    SurveyDefinition survey = surveyService.getSurveyDefinition();

    try {
      fileValidator.validate(file);
      ComponentsRows components = csvParserService.parse(file.getInputStream());
      componentsValidator.validate(survey, components);
    } catch (IOException e) {
      log.error("Exception while validating component file", e);
      throw new IllegalStateException(e);
    }

  }
}
