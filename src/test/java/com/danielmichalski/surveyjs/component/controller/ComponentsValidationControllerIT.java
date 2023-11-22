package com.danielmichalski.surveyjs.component.controller;

import static com.danielmichalski.surveyjs.helper.FileHelper.loadFromClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.danielmichalski.surveyjs.controller.ControllerTestBase;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;

@SpringBootTest
class ComponentsValidationControllerIT extends ControllerTestBase {

  private static final String VALID_DIRECTORY = "valid";
  private static final String INVALID_DIRECTORY = "invalid";

  private static final String CONTROLLER_URL = "/api/components-validation";
  public static final String TEXT_CSV_CONTENT_TYPE = "text/csv";

  @Nested
  class ValidateComponentsTests {

    @Test
    void validFile_shouldReturnHttpCode200() throws Exception {
      final String fileName = "valid.csv";
      final MockMultipartFile file = buildMultipartFile(VALID_DIRECTORY, fileName);

      multipartPost(file, HttpStatus.OK, CONTROLLER_URL)
          .andReturn();
    }

    @ParameterizedTest(name = "[{index}] => file[{0}], invalidColumnName[{1}], expectedValidationMessage[{2}]}")
    @MethodSource("provideInvalidFileNames")
    void validFile_shouldReturnHttpCode400AndValidationMessage(String fileName,
                                                               String invalidColumnName,
                                                               String expectedValidationMessage) throws Exception {
      final MockMultipartFile file = buildMultipartFile(INVALID_DIRECTORY, fileName);

      multipartPost(file, HttpStatus.BAD_REQUEST, CONTROLLER_URL)
          .andExpectAll(
              jsonPath("$", hasSize(1)),
              jsonPath("$[0].columnName", equalTo(invalidColumnName)),
              jsonPath("$[0].message", equalTo(expectedValidationMessage))
          );
    }

    private static MockMultipartFile buildMultipartFile(String directoryName, String fileName) throws IOException {
      final InputStream fileStream = loadFromClasspath(String.format("%s/%s", directoryName, fileName));
      return new MockMultipartFile("file", fileName, TEXT_CSV_CONTENT_TYPE, fileStream);
    }

    private static Stream<Arguments> provideInvalidFileNames() {
      return Stream.of(
          Arguments.of(
              "ce-ivdd-missing-fields.csv",
              "component_list_IVDD",
              "Answer [] is incorrect. Valid answers: [A, B, Other]"
          ),
          Arguments.of(
              "ce-ivdr-missing-fields.csv",
              "component_risk_class_IVDR",
              "Answer [] is incorrect. Valid answers: [A, B, C, D]"
          ),
          Arguments.of(
              "ce-mdd-missing-fields.csv",
              "component_language",
              "Answer [CE MDD] is incorrect. Valid answers: [English, German]"
          ),
          Arguments.of(
              "ce-mdr-missing-fields.csv",
              "component_risk_class_MDR",
              "Answer [] is incorrect. Valid answers: [IIa, IIb, Im, III, I, Ir, Is]"
          ),
          Arguments.of(
              "invalid-component-list-IVDD.csv",
              "component_list_IVDD",
              "Answer [IIa] is incorrect. Valid answers: [A, B, Other]"
          ),
          Arguments.of(
              "missing-name.csv",
              "component_name",
              "This field is required"
          )
      );
    }
  }
}