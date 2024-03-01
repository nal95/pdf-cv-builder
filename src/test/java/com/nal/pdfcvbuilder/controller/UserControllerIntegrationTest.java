package com.nal.pdfcvbuilder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nal.pdfcvbuilder.DTOs.UserRequest;
import com.nal.pdfcvbuilder.DTOs.UserResponse;
import com.nal.pdfcvbuilder.entities.CVData;
import com.nal.pdfcvbuilder.pdfCvBuilderExceptions.ResourceAlreadyExistsException;
import com.nal.pdfcvbuilder.pdfCvBuilderExceptions.UserNotFoundException;
import com.nal.pdfcvbuilder.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createUser_Success() throws Exception {
        // Given
        UserRequest userRequest = createUserRequest();
        UserResponse expectedUserResponse = createUserResponse();

        // When
        given(userService.createUser(any(UserRequest.class)))
                .willReturn(expectedUserResponse);

        ResultActions response = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)));

        // Then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(expectedUserResponse.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(expectedUserResponse.getLastName())))
                .andExpect(jsonPath("$.email", is(expectedUserResponse.getEmail())))
                .andExpect(jsonPath("$.image", is(expectedUserResponse.getImage())))
                .andExpect(jsonPath("$.profession", is(expectedUserResponse.getProfession())));

    }

    @Test
    void createUser_Conflict() throws Exception {
        // Given
        UserRequest userRequest = createUserRequest();
        String conflictMessage = "User with email " + userRequest.getEmail() + " already exists";

        // When
        given(userService.createUser(any(UserRequest.class)))
                .willThrow(new ResourceAlreadyExistsException(conflictMessage));

        ResultActions response = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)));

        // Then
        response.andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.body.type", is("about:blank")))
                .andExpect(jsonPath("$.body.title", is("Conflict")))
                .andExpect(jsonPath("$.body.status", is(409)))
                .andExpect(jsonPath("$.body.detail", is(conflictMessage)));
    }

    @Test
    void getUser_Success() throws Exception {
        // Given
        Long userId = 1L;
        UserResponse expectedUserResponse = createUserResponse();

        // When
        given(userService.getUser(userId))
                .willReturn(expectedUserResponse);

        ResultActions response = mockMvc.perform(get("/users/{userId}", userId));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(expectedUserResponse.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(expectedUserResponse.getLastName())))
                .andExpect(jsonPath("$.email", is(expectedUserResponse.getEmail())))
                .andExpect(jsonPath("$.image", is(expectedUserResponse.getImage())))
                .andExpect(jsonPath("$.profession", is(expectedUserResponse.getProfession())));
    }

    @Test
    void getUser_NotFound() throws Exception {
        // Given
        Long userId = 1L;
        String notFoundMessage = "User with ID " + userId + " not found";

        // When
        given(userService.getUser(userId))
                .willThrow(new UserNotFoundException(notFoundMessage));

        ResultActions response = mockMvc.perform(get("/users/{userId}", userId));

        // Then
        response.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.body.type", is("about:blank")))
                .andExpect(jsonPath("$.body.title", is("Not Found")))
                .andExpect(jsonPath("$.body.status", is(404)))
                .andExpect(jsonPath("$.body.detail", is(notFoundMessage)));
    }

    @Test
    void updateUser_Success() throws Exception {
        // Given
        Long userId = 1L;
        UserRequest userRequest = createUserRequest();
        UserResponse expectedUserResponse = createUserResponse();

        // When
        given(userService.updateUser(eq(userId), any(UserRequest.class)))
                .willReturn(expectedUserResponse);

        ResultActions response = mockMvc.perform(put("/users/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(expectedUserResponse.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(expectedUserResponse.getLastName())))
                .andExpect(jsonPath("$.email", is(expectedUserResponse.getEmail())))
                .andExpect(jsonPath("$.image", is(expectedUserResponse.getImage())))
                .andExpect(jsonPath("$.profession", is(expectedUserResponse.getProfession())));
    }

    @Test
    void updateUser_NotFound() throws Exception {
        // Given
        Long userId = 1L;
        UserRequest userRequest = createUserRequest();
        String notFoundMessage = "User with ID " + userId + " not found";

        // When
        given(userService.updateUser(eq(userId), any(UserRequest.class)))
                .willThrow(new UserNotFoundException(notFoundMessage));

        ResultActions response = mockMvc.perform(put("/users/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)));

        // Then
        response.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.body.type", is("about:blank")))
                .andExpect(jsonPath("$.body.title", is("Not Found")))
                .andExpect(jsonPath("$.body.status", is(404)))
                .andExpect(jsonPath("$.body.detail", is(notFoundMessage)));
    }

    private UserResponse createUserResponse() {
        return UserResponse.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .image("image.png")
                .profession("Developer")
                .build();
    }

    private UserRequest createUserRequest() {
        return UserRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .password("password")
                .email("john.doe@example.com")
                .nationality("US")
                .location("New York")
                .summary("Summary")
                .objectives("Objectives")
                .profession("Developer")
                .mobile("123456789")
                .image("image.png")
                .years(5)
                .data(new CVData())
                .build();
    }
}