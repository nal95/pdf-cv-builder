package com.nal.pdfcvbuilder.controller;

import com.nal.pdfcvbuilder.DTOs.UserRequest;
import com.nal.pdfcvbuilder.DTOs.UserResponse;
import com.nal.pdfcvbuilder.pdfCvBuilderExceptions.ResourceAlreadyExistsException;
import com.nal.pdfcvbuilder.pdfCvBuilderExceptions.UserNotFoundException;
import com.nal.pdfcvbuilder.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {
        return new ResponseEntity<>(service.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUser(@PathVariable Long userId) {
        try {
            UserResponse user = service.getUser(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);

        } catch (UserNotFoundException e) {
            ErrorResponse errorResponse = ErrorResponse.builder(e, HttpStatus.NOT_FOUND,
                    e.getMessage()).build();

            return new ResponseEntity<>(errorResponse.getBody(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable Long userId, @RequestBody UserRequest userRequest) {
        try {
            UserResponse updatedUser = service.updateUser(userId, userRequest);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);

        } catch (UserNotFoundException e) {
            ErrorResponse errorResponse = ErrorResponse.builder(e, HttpStatus.NOT_FOUND,
                    e.getMessage()).build();

            return new ResponseEntity<>(errorResponse.getBody(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long userId) {
        try {
            service.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody UserRequest userRequest) {
        try {
            UserResponse createdUser = service.createUser(userRequest);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);

        } catch (ResourceAlreadyExistsException e) {
            ErrorResponse errorResponse = ErrorResponse.builder(e, HttpStatus.CONFLICT, e.getMessage()).build();

            return new ResponseEntity<>(errorResponse.getBody(), HttpStatus.CONFLICT);
        }
    }
}