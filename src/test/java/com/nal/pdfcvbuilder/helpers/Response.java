package com.nal.pdfcvbuilder.helpers;

import com.nal.pdfcvbuilder.DTOs.ResumeRequest;
import com.nal.pdfcvbuilder.DTOs.ResumeResponse;
import com.nal.pdfcvbuilder.DTOs.UserRequest;
import com.nal.pdfcvbuilder.DTOs.UserResponse;
import com.nal.pdfcvbuilder.entities.*;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Set;

public class Response {
    User user;
    Resume resume;
    UserResponse userResponse;
    UserRequest userRequest;
    ResumeResponse resumeResponse;
    ResumeData resumeData;
    ResumeRequest resumeRequest;
    UserImageDetail imageDetail;

    public Response() {
    }

    public User getUser() {
        setUser();
        return user;
    }

    public void setUser() {
        this.user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .password("password")
                .email("john.doe@example.com")
                .nationality("US")
                .location("New York")
                .summary("Summary")
                .title("B. Eng.")
                .profession("Developer")
                .mobile("123456789")
                .image("image.png")
                .years(5)
                .build();
    }

    public Resume getResume() {
        setResume();
        return resume;
    }

    public void setResume() {
        setUser();
        setResumeData();
        this.resume = Resume.builder()
                .id(1L)
                .user(getUser())
                .data(getResumeData())
                .build();
    }

    public UserResponse getUserResponse() {
        setUserResponse();
        return userResponse;
    }

    public void setUserResponse() {
        this.userResponse = UserResponse.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .image("image.png")
                .profession("Developer")
                .build();
    }

    public UserRequest getUserRequest() {
        setUserRequest();
        return userRequest;
    }

    public void setUserRequest() {
        this.userRequest = UserRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .password("password")
                .email("john.doe@example.com")
                .nationality("US")
                .location("New York")
                .summary("Summary")
                .title("Objectives")
                .profession("Developer")
                .mobile("123456789")
                .image("image.png")
                .years(5)
                .build();
    }

    public ResumeResponse getResumeResponse() {
        setResumeResponse();
        return resumeResponse;
    }

    public void setResumeResponse() {
        setUserResponse();
        setResumeData();
        this.resumeResponse = ResumeResponse.builder()
                .userResponse(getUserResponse())
                .resumeData(getResumeData())
                .build();
    }

    public ResumeData getResumeData() {
        setResumeData();
        return resumeData;
    }

    public void setResumeData() {
        this.resumeData = createResumeData();
    }

    public ResumeRequest getResumeRequest() {
        setResumeRequest();
        return resumeRequest;
    }

    public void setResumeRequest() {
        setResumeData();
        this.resumeRequest = ResumeRequest.builder().data(getResumeData()).build();
    }

    public UserImageDetail getImageDetail(String tempDir) {
        setUser();
        setImageDetail(tempDir);
        return imageDetail;
    }

    public void setImageDetail(String tempDir) {
        this.imageDetail = UserImageDetail.builder()
                .imagePath(Paths.get(tempDir))
                .imageName("John_Doe.jpeg")
                .user(getUser())
                .build();
    }

    private ResumeData createResumeData() {
        Network n = new Network("LINKEDIN", "https://linkedin.com/in/johndoe");
        Education e = new Education("University of XYZ", "Computer Science", "Bachelor's", LocalDate.of(2020, 1, 1), LocalDate.of(2024, 1, 1), 4, "Graduated with honors");
        WorkExperience w = new WorkExperience("ABC Inc.", "New York", "https://abcinc.com", "Software Engineer", LocalDate.of(2024, 2, 1), LocalDate.of(2025, 2, 1), 1, "Developed cutting-edge software");
        TechnicalExperience t = new TechnicalExperience("PROGRAMMING", Set.of(Detail.builder().name("java").level(5).build(), Detail.builder().name("JavaScript").level(4).build()));
        return ResumeData.builder()
                .networks((Set.of(n)))
                .educations(Set.of(e))
                .workExperiences(Set.of(w))
                .technicalExperiences(Set.of(t))
                .tools(Set.of("Git", "Jira", "Webpack", "TDD", "MVC"))
                .methodologies(Set.of("Agile", "Scrum"))
                .skills(Set.of("SQL", "Firebase", "Algorithm"))
                .hobbiesAndInterest(Set.of("Sport", "Travel"))
                .trainingsAndCertifications(Set.of("Google Cloud", "Clean Code"))
                .build();

    }
}
