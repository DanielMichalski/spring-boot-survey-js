# Spring Boot SurveyJS

## Table of Contents

* [Prerequisites](#prerequisites)
* [Description](#description)
  * [Hints](#hints)
* [Running the application](#running-the-application)
  * [On Windows](#on-windows)
  * [On MacOS/ Linux](#on-macos-linux)

## Prerequisites

- [Java JDK](https://www.oracle.com/pl/java/technologies/downloads/#java17) version 17+

## Description

We are using a tool called [SurveyJS](https://surveyjs.io/) to enable our regulatory team to create
and edit forms without depending on software developers.
Using a form editor, they can design dynamic forms
consisting of mandatory/optional questions and various controls (dropdowns, radio buttons, etc.)
including validation logic. When they are done, they hand over
a [JSON file](./src/main/resources/survey.json) to us, which serves as the form definition and is
rendered by the SurveyJS client library.

One such form is used by manufacturers to register components they are producing on the platform
(see[survey.json](./src/main/resources/survey.json)
and [how it looks for the user](https://surveyjs.io/published?id=6f4e0ee9-c761-4705-9748-331e677adba3)).
We now faced the challenge to implement a CSV import of multiple products at once.
Since the validation is done by SurveyJS when using the application,
we also had to replicate the validation logic of SurveyJS
for the CSV import in the backend.

This project is an implementation a multipart file upload API endpoint that receives a CSV file (see
examples in[src/test/resources](src/test/resources)) and that returns either:

1. HTTP status 200 if all rows in the CSV file are valid or...
2. HTTP status 400 if at least once component in the CSV file is invalid and a list of errors in the
   response payload.

#### Hints

When completing a survey, SurveyJS generate a JSON object containing the form data:

```json
{
  "component_language": "English",
  "component_name": "My Calibrator",
  "component_regulatory_clearance": "CE IVDR",
  "component_risk_class_IVDR": "B",
  "component_type": "Calibrator"
}
```

A question in the [survey.json](./src/main/resources/survey.json) is only mandatory if both
flag `visibleIf` and
`required` are true.

The values in the CSV files match the `choices` in
the [survey.json](./src/main/resources/survey.json). If a choice is
a string, it's the string itself. If the choice is an object, the value is taken from the `value`
property of the
object. The values from the `text` object are only used for i18n.

You can find more information about designing forms with SurveyJS
[here](https://surveyjs.io/form-library/documentation/design-survey/conditional-logic).

## Running the application

#### On Windows

```bash
## Build application using Maven Wrapper
mvnw.cmd clean install

## Run Spring boot application using Maven Wrapper
mvnw.cmd spring-boot:run
```

#### On MacOS/ Linux

```bash
## Build application using Maven Wrapper
./mvnw clean install

## Run Spring boot application using Maven Wrapper
./mvnw spring-boot:run
```