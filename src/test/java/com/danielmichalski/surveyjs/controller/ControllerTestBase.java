package com.danielmichalski.surveyjs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest
public class ControllerTestBase {

  @Autowired
  private MockMvc mockMvc;

  protected ResultActions multipartPost(MockMultipartFile file,
                                        HttpStatus status,
                                        String url,
                                        Object... pathArgs) throws Exception {
    MockMultipartHttpServletRequestBuilder multipart = (MockMultipartHttpServletRequestBuilder) MockMvcRequestBuilders
        .multipart(url, pathArgs)
        .with(request -> {
          request.setMethod(HttpMethod.POST.name());
          return request;
        });

    return mockMvc.perform(multipart.file(file))
        .andExpect(MockMvcResultMatchers.status().is(status.value()));
  }

}
