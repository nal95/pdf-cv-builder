package com.nal.pdfcvbuilder.controller;

import com.nal.pdfcvbuilder.pdfCvBuilderExceptions.UserNotFoundException;
import com.nal.pdfcvbuilder.services.UserImageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/users/image")
public class UserImageController {

    // TODO: test this controller
    private final UserImageService userImageService;

    public UserImageController(UserImageService userImageService) {
        this.userImageService = userImageService;
    }


    @PostMapping("image/{userId}")
    public ResponseEntity<Object> saveUserProfileImage(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
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

            if (e instanceof IOException) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                exceptionMessage = "Internal Error by saving the image";
            }

            ErrorResponse errorResponse = ErrorResponse.builder(e, status, exceptionMessage).build();

            return new ResponseEntity<>(errorResponse.getBody(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/image/{userId}")
    public ResponseEntity<ByteArrayResource> getUserProfileImage(@PathVariable Long userId) {

        try {
            byte[] imageBytes = userImageService.getUserProfileImage(userId);

            ByteArrayResource resource = new ByteArrayResource(imageBytes);

            return ResponseEntity.ok()
                    .contentLength(imageBytes.length)
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);

        } catch (IOException | UserNotFoundException e) {
            // TODO: implement LOGGING
            // (e instanceof IOException) ? log x : log y

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }
}
