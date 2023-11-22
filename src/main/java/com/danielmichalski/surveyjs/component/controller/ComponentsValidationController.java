package com.danielmichalski.surveyjs.component.controller;

import com.danielmichalski.surveyjs.component.service.ComponentsValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/components-validation")
@RequiredArgsConstructor
public class ComponentsValidationController {

  private final ComponentsValidationService componentsValidationService;

  @PostMapping
  public void validate(@RequestBody MultipartFile file) {
    componentsValidationService.validate(file);
  }

}
