package com.nal.pdfcvbuilder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nal.pdfcvbuilder.DTOs.ResumeRequest;
import com.nal.pdfcvbuilder.DTOs.ResumeResponse;
import com.nal.pdfcvbuilder.helpers.Response;
import com.nal.pdfcvbuilder.pdfCvBuilderExceptions.UserNotFoundException;
import com.nal.pdfcvbuilder.services.ResumeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ResumeController.class)
class ResumeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResumeService service;

    @Autowired
    private ObjectMapper objectMapper;

    Response responseHelper = null;

    String url = "/resumes/{userId}";

    @BeforeEach
    void Init() {
        responseHelper = new Response();
    }

    @Test
    void createResume_Success() throws Exception {
        // GIven
        ResumeRequest request = responseHelper.getResumeRequest();
        ResumeResponse expectedResumeResponse = responseHelper.getResumeResponse();

        // When
        given(service.createResume(anyLong(), any(ResumeRequest.class)))
                .willReturn(expectedResumeResponse);

        ResultActions response = mockMvc.perform(post(url, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // Then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResumeResponse)));
    }

    @Test
    void createResume_Fail_becauseUserNotFound() throws Exception {
        // Given
        Long userId = 1L;
        ResumeRequest resumeRequest = responseHelper.getResumeRequest();
        String notFoundMessage = "User with ID " + userId + " not found";

        // When
        given(service.createResume(eq(userId), any(ResumeRequest.class)))
                .willThrow(new UserNotFoundException(notFoundMessage));

        ResultActions response = mockMvc.perform(post(url, userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resumeRequest)));

        // Then
        response.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.type", is("about:blank")))
                .andExpect(jsonPath("$.title", is("Not Found")))
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.detail", is(notFoundMessage)));
    }

    @Test
    void getResume_Success() throws Exception {
        // GIven
        ResumeResponse expectedResumeResponse = responseHelper.getResumeResponse();

        // When
        given(service.getResume(anyLong()))
                .willReturn(expectedResumeResponse);

        ResultActions response = mockMvc.perform(get(url, 1L));
        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResumeResponse)));

    }

    @Test
    void updateResume_Success() throws Exception {
        // GIven
        ResumeRequest request = new ResumeRequest();
        ResumeResponse expectedResumeResponse = responseHelper.getResumeResponse();

        // When
        given(service.updateResume(anyLong(), any(ResumeRequest.class)))
                .willReturn(expectedResumeResponse);

        ResultActions response = mockMvc.perform(put(url, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResumeResponse)));
    }

    @Test
    void deleteUser_Success() throws Exception {
        // When
        willDoNothing().given(service).deleteResume(anyLong());
        ResultActions response = mockMvc.perform(delete(url, 1L));

        // Then
        response.andDo(print())
                .andExpect(status().isNoContent());
    }
}

