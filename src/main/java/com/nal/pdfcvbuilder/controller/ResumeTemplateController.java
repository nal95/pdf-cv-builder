package com.nal.pdfcvbuilder.controller;

import com.nal.pdfcvbuilder.DTOs.ResumeResponse;
import com.nal.pdfcvbuilder.DTOs.UserResponse;
import com.nal.pdfcvbuilder.pdfCvBuilderExceptions.UserNotFoundException;
import com.nal.pdfcvbuilder.services.ResumeService;
import com.nal.pdfcvbuilder.services.UserImageService;
import com.nal.pdfcvbuilder.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/users/resumes/template")
public class ResumeTemplateController {

    private final ResumeService resumeService;
    private final UserService userService;

    private final UserImageService userImageService;

    public ResumeTemplateController(ResumeService resumeService, UserService userService, UserImageService userImageService) {
        this.resumeService = resumeService;
        this.userService = userService;
        this.userImageService = userImageService;
    }


    @GetMapping("/{userId}")
    public String viewResume(@PathVariable Long userId, Model model) {
        UserResponse user = userService.getUser(userId);
        ResumeResponse resume = resumeService.getResume(userId);

        if (resume != null && user != null) {
            model.addAttribute("user", user);
            model.addAttribute("resume", resume.getResumeData());
        }

        return "resume";
    }


    @GetMapping("/{userId}/{imagePath}")
    public ResponseEntity<Object> getUserProfileImage(@PathVariable Long userId, @PathVariable String imagePath) {
        try {
            byte[] imageBytes = userImageService.getUserProfileImage(userId);

            ByteArrayResource resource = new ByteArrayResource(imageBytes);

            return ResponseEntity.ok()
                    .contentLength(imageBytes.length)
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);

        } catch (IOException | UserNotFoundException e) {
            ErrorResponse errorResponse = ErrorResponse.builder(e, HttpStatus.NOT_FOUND,
                    (e instanceof IOException) ? "User image not found" : e.getMessage()
            ).build();

            return new ResponseEntity<>(errorResponse.getBody(), HttpStatus.NOT_FOUND);
        }
    }

}


