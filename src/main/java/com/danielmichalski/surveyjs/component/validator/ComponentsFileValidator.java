package com.danielmichalski.surveyjs.component.validator;

import com.danielmichalski.surveyjs.common.errors.FileException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ComponentsFileValidator {

  private static final String CSV_EXTENSION = "csv";

  public void validate(MultipartFile file) {
    if (file == null
        || file.isEmpty()
        || !isCsvExtension(file)) {
      throw new FileException("Components file is missing or it is empty");
    }
  }

  private static boolean isCsvExtension(MultipartFile file) {
    return CSV_EXTENSION.equals(StringUtils.getFilenameExtension(file.getOriginalFilename()));
  }

}

