package com.nal.pdfcvbuilder.services;

import com.nal.pdfcvbuilder.DTOs.UserRequest;
import com.nal.pdfcvbuilder.DTOs.UserResponse;
import com.nal.pdfcvbuilder.entities.User;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);

    List<UserResponse> getUsers();

    User mapUserRequestToUser( User user, UserRequest userRequest);
    UserResponse mapUserToUserResponse(User user);

    UserResponse updateUser(Long userId, UserRequest userRequest);

    boolean deleteUser(Long userId);

    UserResponse getUser(Long userId);
}
