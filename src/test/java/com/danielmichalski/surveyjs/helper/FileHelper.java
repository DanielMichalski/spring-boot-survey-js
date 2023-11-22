package com.danielmichalski.surveyjs.helper;

import java.io.IOException;
import java.io.InputStream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ClassPathResource;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FileHelper {

  public static InputStream loadFromClasspath(String classpath) {
    try {
      return new ClassPathResource(classpath).getInputStream();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}