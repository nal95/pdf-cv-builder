package com.nal.pdfcvbuilder.services;

import com.nal.pdfcvbuilder.DTOs.UserRequest;
import com.nal.pdfcvbuilder.DTOs.UserResponse;
import com.nal.pdfcvbuilder.entities.User;
import com.nal.pdfcvbuilder.pdfCvBuilderExceptions.ResourceAlreadyExistsException;
import com.nal.pdfcvbuilder.pdfCvBuilderExceptions.UserNotFoundException;
import com.nal.pdfcvbuilder.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void createUser_WithNonExistingEmail_ShouldCreateUser() {
        // Given
        UserRequest userRequest = createUserRequest();
        User user = createUser();

        // When -  action or the behaviour that we are going test
        given(userRepository.existsByEmail(userRequest.getEmail())).willReturn(false);
        given(modelMapper.map(any(UserRequest.class), eq(User.class))).willReturn(user);
        given(modelMapper.map(any(User.class), eq(UserResponse.class))).willReturn(createUserResponse());

        user.setId(1L);
        given(userRepository.save(any(User.class))).willReturn(user);

        UserResponse createdUser = userService.createUser(userRequest);

        // Then
        verify(userRepository, times(1)).save(any());
        assertThat(createdUser).isNotNull();
    }

    @Test()
    public void createUser_WithExistingEmail_ShouldThrowException() {
        // Given
        UserRequest userRequest = createUserRequest();
        String exceptionMessage = "User with email " + userRequest.getEmail() + " already exists";

        // When -  action or the behaviour that we are going test
        given(userRepository.existsByEmail(anyString())).willReturn(true);

        // Then
        ResourceAlreadyExistsException exception = assertThrows(
                ResourceAlreadyExistsException.class, () -> userService.createUser(userRequest));

        verify(userRepository, never()).save(any(User.class));
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    public void getUsers_ShouldReturnListOfUserResponses() {

        //Given
        User user = createUser();
        user.setId(1L);

        // When -  action or the behaviour that we are going test
        given(userRepository.findAll()).willReturn(Collections.singletonList(user));
        given(modelMapper.map(any(User.class), eq(UserResponse.class))).willReturn(createUserResponse());

        List<UserResponse> userList = userService.getUsers();

        // Then
        assertThat(userList).isNotNull();
        assertThat(userList).hasSize(1);
        assertEquals(user.getEmail(), userList.get(0).getEmail());
    }

    @Test
    public void getUsers_ShouldReturnEmptyListOfUserResponses() {
        // When -  action or the behaviour that we are going test
        given(userRepository.findAll()).willReturn(Collections.emptyList());

        List<UserResponse> userList = userService.getUsers();

        // Then
        assertThat(userList).isEmpty();
        assertThat(userList).hasSize(0);
    }

    @Test
    public void updateUser_WithExistingUserId_ShouldUpdateUser() {
        // Given
        UserRequest userRequest = createUserRequest();
        userRequest.setEmail("new@email.de");
        userRequest.setFirstName("Jack");
        userRequest.setLastName("Brown");
        User user = createUser();
        UserResponse userResponse = createUserResponse();
        userResponse.setEmail("new@email.de");
        userResponse.setFirstName("Jack");
        userResponse.setLastName("Brown");

        // When -  action or the behaviour that we are going test
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(userRepository.save(user)).willReturn(user);
        given(modelMapper.map(any(User.class), eq(UserResponse.class))).willReturn(userResponse);

        UserResponse updatedUser = userService.updateUser(1L, userRequest);

        // Then
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getEmail()).isEqualTo("new@email.de");
        assertThat(updatedUser.getFirstName()).isEqualTo("Jack");
        assertThat(updatedUser.getLastName()).isEqualTo("Brown");
    }

    @Test()
    public void updateUser_WithNonExistingUserId_ShouldThrowException() {
        // Given
        UserRequest userRequest = createUserRequest();
        long userId = 1L;
        String exceptionMessage = "User with ID " + userId + " not found";

        // When -  action or the behaviour that we are going test
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        // Then
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.updateUser(userId, userRequest));
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    public void deleteUser_WithExistingUserId_ShouldReturnTrue() {
        // When -  action or the behaviour that we are going test
        willDoNothing().given(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        // Then
        verify(userRepository, times(1)).deleteById(1L);
    }


    private User createUser() {
        return User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();
    }

    private UserResponse createUserResponse() {
        return UserResponse.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .profession("Developer")
                .image("image.png")
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
                .build();
    }

}