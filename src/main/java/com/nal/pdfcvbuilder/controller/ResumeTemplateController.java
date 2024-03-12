package com.nal.pdfcvbuilder.controller;

import com.nal.pdfcvbuilder.DTOs.ResumeResponse;
import com.nal.pdfcvbuilder.DTOs.ResumeTemplates;
import com.nal.pdfcvbuilder.DTOs.UserResponse;
import com.nal.pdfcvbuilder.services.ResumeService;
import com.nal.pdfcvbuilder.services.TemplateService;
import com.nal.pdfcvbuilder.services.UserImageService;
import com.nal.pdfcvbuilder.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/templates")
public class ResumeTemplateController {

    // TODO: test this controller

    private final ResumeService resumeService;
    private final UserService userService;
    private final TemplateService templateService;
    private final UserImageService userImageService;

    public ResumeTemplateController(ResumeService resumeService,
                                    UserService userService,
                                    TemplateService templateService,
                                    UserImageService userImageService) {

        this.resumeService = resumeService;
        this.userService = userService;
        this.templateService = templateService;
        this.userImageService = userImageService;
    }

    @GetMapping("{templateName}/{userId}")
    public String viewResume(@PathVariable String templateName, @PathVariable Long userId, Model model) {

        ResumeTemplates res = ResumeTemplates.valueOf(templateName.toUpperCase());

        UserResponse user = userService.getUser(userId);
        ResumeResponse resume = resumeService.getResume(userId);

        if (resume != null && user != null) {
            model.addAttribute("user", user);
            model.addAttribute("resume", resume.getResumeData());
        }

        return res.getTemplatePath();
    }

    @GetMapping("{templateName}/image/{userId}")
    public ResponseEntity<byte[]> getUserProfileImage(@PathVariable String templateName, @PathVariable Long userId) {

        // TODO: handle templateName

        try {

            byte[] style = userImageService.getUserProfileImage(userId);

            return ResponseEntity.ok()
                    .contentLength(style.length)
                    .body(style);

        } catch (IOException e) {
            // TODO: implement LOGGING // (e instanceof IOException) ? log x : log y

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @GetMapping("{templateName}/style.css")
    public ResponseEntity<byte[]> getTemplateStyle(@PathVariable String templateName) {
        try {
            ResumeTemplates res = ResumeTemplates.valueOf(templateName.toUpperCase());

            byte[] style = templateService.getTemplateStyle(res.getTemplateStylePath());
            return ResponseEntity.ok()
                    .contentLength(style.length)
                    .body(style);

        } catch (IOException e) {
            // TODO: implement LOGGING // (e instanceof IOException) ? log x : log y

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }
}




