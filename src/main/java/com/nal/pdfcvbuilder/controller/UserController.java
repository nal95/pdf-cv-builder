package com.nal.pdfcvbuilder.controller;

import com.nal.pdfcvbuilder.DTOs.UserRequest;
import com.nal.pdfcvbuilder.DTOs.UserResponse;
import com.nal.pdfcvbuilder.pdfCvBuilderExceptions.ResourceAlreadyExistsException;
import com.nal.pdfcvbuilder.pdfCvBuilderExceptions.UserNotFoundException;
import com.nal.pdfcvbuilder.services.UserImageService;
import com.nal.pdfcvbuilder.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;
    private final UserImageService userImageService;

    public UserController(UserService service, UserImageService userImageService) {
        this.service = service;
        this.userImageService = userImageService;
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

    // TODO: Test this
    @PostMapping("/{userId}/uploadImage")
    public ResponseEntity<Object> saveUserImage(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
        try {
            byte[] imageBytes = userImageService.saveImage(file, userId);

            // Create a Resource object with the image bytes
            ByteArrayResource resource = new ByteArrayResource(imageBytes);

            return ResponseEntity.ok()
                    .contentLength(imageBytes.length)
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);

        } catch (IOException | UserNotFoundException e) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            String exceptionMessage = e.getMessage();

            if(e instanceof IOException) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                exceptionMessage = "Internal Error by saving the image";
            }

            ErrorResponse errorResponse = ErrorResponse.builder(e, status, exceptionMessage).build();

            return new ResponseEntity<>(errorResponse.getBody(), HttpStatus.NOT_FOUND);
        }
    }
}