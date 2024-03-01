package com.nal.pdfcvbuilder.services;

import com.nal.pdfcvbuilder.DTOs.UserRequest;
import com.nal.pdfcvbuilder.DTOs.UserResponse;
import com.nal.pdfcvbuilder.entities.User;
import com.nal.pdfcvbuilder.pdfCvBuilderExceptions.ResourceAlreadyExistsException;
import com.nal.pdfcvbuilder.pdfCvBuilderExceptions.UserNotFoundException;
import com.nal.pdfcvbuilder.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {

        if (repository.existsByEmail(userRequest.getEmail())) {
            throw new ResourceAlreadyExistsException("User with email " + userRequest.getEmail() + " already exists");
        }

        User user = repository.save(mapUserRequestToUser(new User(), userRequest));

        return mapUserToUserResponse(user);
    }

    @Override
    public UserResponse getUser(Long userId) {

        User user = repository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with ID " + userId + " not found"));

        return mapUserToUserResponse(user);
    }

    @Override
    public List<UserResponse> getUsers() {
        List<UserResponse> userList = new ArrayList<>();
        repository.findAll().forEach(user -> userList.add(mapUserToUserResponse(user)));
        return userList;
    }

    @Override
    public UserResponse updateUser(Long userId, UserRequest userRequest) {
        User existedUser = repository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with ID " + userId + " not found"));

        User updatedUser = mapUserRequestToUser(existedUser, userRequest);

        return mapUserToUserResponse(repository.save(updatedUser));
    }

    @Override
    public boolean deleteUser(Long userId) {

        repository.deleteById(userId);

        return repository.findById(userId).isEmpty();
    }

    @Override
    public User mapUserRequestToUser(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setNationality(userRequest.getNationality());
        user.setLocation(userRequest.getLocation());
        user.setSummary(userRequest.getSummary());
        user.setObjectives(userRequest.getObjectives());
        user.setProfession(userRequest.getProfession());
        user.setMobile(userRequest.getMobile());
        user.setImage(userRequest.getImage());
        user.setYears(userRequest.getYears());
        user.setData(userRequest.getData());

        return user;
    }

    @Override
    public UserResponse mapUserToUserResponse(User user) {
        return UserResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .profession(user.getProfession())
                .image(user.getImage())
                .build();
    }
}