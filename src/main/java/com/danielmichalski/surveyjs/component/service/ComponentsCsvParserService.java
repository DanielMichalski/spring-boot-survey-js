package com.danielmichalski.surveyjs.component.service;


import static java.nio.charset.StandardCharsets.UTF_8;

import com.danielmichalski.surveyjs.component.model.ComponentsRow;
import com.danielmichalski.surveyjs.component.model.ComponentsRows;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVFormat.Builder;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

@Service
class ComponentsCsvParserService {

  ComponentsRows parse(InputStream inputStream) throws IOException {
    CSVFormat csvFormat = Builder.create().setHeader().build();
    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, UTF_8);

    try (
        BufferedReader reader = new BufferedReader(inputStreamReader);
        CSVParser parser = new CSVParser(reader, csvFormat)
    ) {

      return parse(parser);
    }
  }

  private static ComponentsRows parse(CSVParser csvParser) {
    List<String> headers = csvParser.getHeaderNames();
    List<ComponentsRow> rows = csvParser.getRecords().stream()
        .map(CSVRecord::toList)
        .map(ComponentsRow::new)
        .toList();

    return new ComponentsRows(headers, rows);
  }

}

