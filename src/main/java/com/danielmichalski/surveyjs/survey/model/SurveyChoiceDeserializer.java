package com.danielmichalski.surveyjs.survey.model;

import static java.util.Optional.ofNullable;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.IOException;

class SurveyChoiceDeserializer extends JsonDeserializer<SurveyChoice> {

  private static final String VALUE_FIELD_NAME = "value";
  private static final String TEXT_FIELD_NAME = "text";
  public static final String DEFAULT_FIELD_NAME = "default";
  public static final String DE_FIELD_NAME = "de";

  @Override
  public SurveyChoice deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    JsonNode node = jsonParser.getCodec()
        .readTree(jsonParser);

    if (node instanceof TextNode) {
      return buildSurveyTextChoice(node);
    } else {
      return buildSurveyObjectChoice(node);
    }
  }

  private static SurveyTextChoice buildSurveyTextChoice(JsonNode node) {
    return new SurveyTextChoice(node.asText());
  }

  private static SurveyObjectChoice buildSurveyObjectChoice(JsonNode node) {
    String value = node.get(VALUE_FIELD_NAME).asText();

    JsonNode textNode = node.get(TEXT_FIELD_NAME);
    String defaultText = ofNullable(textNode.get(DEFAULT_FIELD_NAME))
        .map(JsonNode::asText)
        .orElse(null);
    String de = ofNullable(textNode.get(DE_FIELD_NAME))
        .map(JsonNode::asText)
        .orElse(null);

    SurveyTextTranslation textTranslation = new SurveyTextTranslation(defaultText, de);
    return new SurveyObjectChoice(value, textTranslation);
  }
}