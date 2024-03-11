package com.nal.pdfcvbuilder.services;

import com.nal.pdfcvbuilder.DTOs.UserRequest;
import com.nal.pdfcvbuilder.DTOs.UserResponse;
import com.nal.pdfcvbuilder.entities.User;
import com.nal.pdfcvbuilder.helpers.Response;
import com.nal.pdfcvbuilder.pdfCvBuilderExceptions.ResourceAlreadyExistsException;
import com.nal.pdfcvbuilder.pdfCvBuilderExceptions.UserNotFoundException;
import com.nal.pdfcvbuilder.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
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

    Response responseHelper = null;

    @BeforeEach
    void Init() {
        responseHelper = new Response();
    }

    @Test
    public void createUser_WithNonExistingEmail_ShouldCreateUser() {
        // Given
        User expectedUser = responseHelper.getUser();
        UserRequest userRequest = responseHelper.getUserRequest();

        // When -  action or the behaviour that we are going test
        given(userRepository.existsByEmail(userRequest.getEmail())).willReturn(false);
        given(modelMapper.map(any(UserRequest.class), eq(User.class))).willReturn(expectedUser);
        given(modelMapper.map(any(User.class), eq(UserResponse.class))).willReturn(responseHelper.getUserResponse());
        given(userRepository.save(any(User.class))).willReturn(expectedUser);

        UserResponse createdUser = userService.createUser(userRequest);

        // Then
        verify(userRepository, times(1)).save(any(User.class));
        assertThat(createdUser).isNotNull();
        assertEquals(expectedUser.getId(), createdUser.getId());
        assertEquals(expectedUser.getFirstName(), createdUser.getFirstName());
        assertEquals(expectedUser.getLastName(), createdUser.getLastName());
        assertEquals(expectedUser.getEmail(), createdUser.getEmail());
        assertEquals(expectedUser.getImage(), createdUser.getImage());
        assertEquals(expectedUser.getProfession(), createdUser.getProfession());
    }

    @Test()
    public void createUser_WithExistingEmail_ShouldThrowException() {
        // Given
        UserRequest userRequest = responseHelper.getUserRequest();
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
        User user = responseHelper.getUser();

        // When -  action or the behaviour that we are going test
        given(userRepository.findAll()).willReturn(Collections.singletonList(user));
        given(modelMapper.map(any(User.class), eq(UserResponse.class))).willReturn(responseHelper.getUserResponse());

        List<UserResponse> userList = userService.getUsers();

        // Then
        verify(userRepository, times(1)).findAll();
        assertThat(userList).isNotNull();
        assertThat(userList).hasSize(1);
        assertEquals(user.getId(), userList.get(0).getId());
        assertEquals(user.getFirstName(), userList.get(0).getFirstName());
        assertEquals(user.getLastName(), userList.get(0).getLastName());
        assertEquals(user.getEmail(), userList.get(0).getEmail());
        assertEquals(user.getImage(), userList.get(0).getImage());
        assertEquals(user.getProfession(), userList.get(0).getProfession());
    }

    @Test
    public void getUsers_ShouldReturnEmptyListOfUserResponses() {
        // When -  action or the behaviour that we are going test
        given(userRepository.findAll()).willReturn(Collections.emptyList());

        List<UserResponse> userList = userService.getUsers();

        // Then
        verify(userRepository, times(1)).findAll();
        assertThat(userList).isEmpty();
        assertThat(userList).hasSize(0);
    }


    @Test
    public void getUser_ShouldReturnUserResponse() {

        //Given
        User expectedUser = responseHelper.getUser();

        // When -  action or the behaviour that we are going test
        given(userRepository.findById(expectedUser.getId())).willReturn(Optional.of(expectedUser));
        given(modelMapper.map(any(User.class), eq(UserResponse.class))).willReturn(responseHelper.getUserResponse());

        UserResponse createdUser = userService.getUser(expectedUser.getId());

        // Then
        verify(userRepository, times(1)).findById(expectedUser.getId());
        assertThat(createdUser).isNotNull();
        assertEquals(expectedUser.getId(), createdUser.getId());
        assertEquals(expectedUser.getFirstName(), createdUser.getFirstName());
        assertEquals(expectedUser.getLastName(), createdUser.getLastName());
        assertEquals(expectedUser.getEmail(), createdUser.getEmail());
        assertEquals(expectedUser.getImage(), createdUser.getImage());
        assertEquals(expectedUser.getProfession(), createdUser.getProfession());
    }

    @Test
    public void updateUser_WithExistingUserId_ShouldUpdateUser() {
        // Given
        User expectedUser = responseHelper.getUser();
        expectedUser.setEmail("new@email.de");
        expectedUser.setFirstName("Jack");
        expectedUser.setLastName("Brown");
        UserResponse userResponse = responseHelper.getUserResponse();
        userResponse.setEmail("new@email.de");
        userResponse.setFirstName("Jack");
        userResponse.setLastName("Brown");

        // When -  action or the behaviour that we are going test
        given(userRepository.findById(1L)).willReturn(Optional.of(new User()));
        given(userRepository.save(any(User.class))).willReturn(expectedUser);
        given(modelMapper.map(any(User.class), eq(UserResponse.class))).willReturn(userResponse);

        UserResponse updatedUser = userService.updateUser(1L, new UserRequest());

        // Then
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
        assertThat(updatedUser).isNotNull();
        assertEquals(expectedUser.getId(), updatedUser.getId());
        assertEquals(expectedUser.getFirstName(), updatedUser.getFirstName());
        assertEquals(expectedUser.getLastName(), updatedUser.getLastName());
        assertEquals(expectedUser.getEmail(), updatedUser.getEmail());
        assertEquals(expectedUser.getImage(), updatedUser.getImage());
        assertEquals(expectedUser.getProfession(), updatedUser.getProfession());
    }

    @Test()
    public void updateUser_WithNonExistingUserId_ShouldThrowException() {
        // Given
        UserRequest userRequest = responseHelper.getUserRequest();
        long userId = 1L;
        String exceptionMessage = "User with ID " + userId + " not found";

        // When -  action or the behaviour that we are going test
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        // Then
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.updateUser(userId, userRequest));
        verify(userRepository, times(1)).findById(userId);
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
}