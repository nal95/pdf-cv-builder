package com.nal.pdfcvbuilder.services;

import com.nal.pdfcvbuilder.DTOs.ResumeRequest;
import com.nal.pdfcvbuilder.DTOs.ResumeResponse;
import com.nal.pdfcvbuilder.DTOs.UserResponse;
import com.nal.pdfcvbuilder.entities.Education;
import com.nal.pdfcvbuilder.entities.Resume;
import com.nal.pdfcvbuilder.entities.ResumeData;
import com.nal.pdfcvbuilder.entities.User;
import com.nal.pdfcvbuilder.helpers.Response;
import com.nal.pdfcvbuilder.repositories.ResumeRepository;
import com.nal.pdfcvbuilder.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResumeServiceImplTest {

    @Mock
    private UserRepository userRepository;


    @Mock
    private ResumeRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ResumeServiceImpl service;

    Response responseHelper = null;

    @BeforeEach
    void Init() {
        responseHelper = new Response();
    }

    @Test
    void createResume_WithExistingUser_ShouldCreateResume() {
        // Given
        Resume resume = responseHelper.getResume();
        ResumeRequest request = responseHelper.getResumeRequest();
        ResumeResponse expectedResumeResponse = responseHelper.getResumeResponse();

        // When -  action or the behaviour that we are going test
        given(userRepository.findById(resume.getUser().getId())).willReturn(Optional.of(resume.getUser()));
        given(modelMapper.map(any(ResumeRequest.class), eq(ResumeData.class))).willReturn(resume.getData());
        given(modelMapper.map(any(Resume.class), eq(ResumeResponse.class))).willReturn(expectedResumeResponse);
        given(repository.save(any(Resume.class))).willReturn(resume);

        ResumeResponse actuelResumeResponse = service.createResume(resume.getUser().getId(), request);

        // Then
        verify(repository, times(1)).save(any(Resume.class));
        assertThat(actuelResumeResponse).isNotNull();
        assertEquals(expectedResumeResponse.getResumeData().getEducations(), actuelResumeResponse.getResumeData().getEducations());
        assertEquals(actuelResumeResponse.getResumeData().getEducations().size(), 1);
        assertEquals(actuelResumeResponse.getUserResponse().getId(), 1L);
    }

    @Test
    void getResume() {
        // Given
        Resume resume = responseHelper.getResume();
        ResumeResponse expectedResumeResponse = responseHelper.getResumeResponse();

        // When -  action or the behaviour that we are going test
        given(userRepository.findById(resume.getUser().getId())).willReturn(Optional.of(resume.getUser()));
        given(repository.findResumeByUser(any(User.class))).willReturn(resume);
        given(modelMapper.map(any(Resume.class), eq(ResumeResponse.class))).willReturn(expectedResumeResponse);

        ResumeResponse actuelResumeResponse = service.getResume(resume.getUser().getId());

        // Then
        verify(repository, times(1)).findResumeByUser(resume.getUser());
        assertThat(actuelResumeResponse).isNotNull();
        assertEquals(expectedResumeResponse.getResumeData().getEducations(), actuelResumeResponse.getResumeData().getEducations());
        assertEquals(actuelResumeResponse.getResumeData().getEducations().size(), 1);
        assertEquals(actuelResumeResponse.getUserResponse().getId(), 1L);
    }

    @Test
    void updateResume() {
        // Given
        ResumeRequest request = new ResumeRequest();
        Education e = new Education("University of XYZ", "Computer Science", "Master", LocalDate.of(2020, 1, 1), LocalDate.of(2023, 1, 1), 4, "Graduated with honors");
        ResumeData data = responseHelper.getResumeData();
        data.setEducations(Set.of(e));

        Resume resume = responseHelper.getResume();

        ResumeResponse expectedUpdatedResumeResponse = ResumeResponse.builder()
                .userResponse(UserResponse.builder().id(1L).build())
                .resumeData(data).build();
        expectedUpdatedResumeResponse.setResumeData(data);

        // When -  action or the behaviour that we are going test
        given(userRepository.findById(1L)).willReturn(Optional.of(resume.getUser()));
        given(repository.findResumeByUser(any(User.class))).willReturn(resume);

        resume.setData(data);
        given(modelMapper.map(any(ResumeRequest.class), eq(ResumeData.class))).willReturn(resume.getData());
        given(repository.save(any(Resume.class))).willReturn(resume);
        given(modelMapper.map(any(Resume.class), eq(ResumeResponse.class))).willReturn(expectedUpdatedResumeResponse);

        ResumeResponse actuelResumeResponse = service.updateResume(1L, request);

        // Then
        verify(repository, times(1)).save(any(Resume.class));
        assertThat(actuelResumeResponse).isNotNull();
        assertEquals(expectedUpdatedResumeResponse.getResumeData().getEducations(), actuelResumeResponse.getResumeData().getEducations());
        assertEquals(actuelResumeResponse.getResumeData().getEducations().size(), 1);
        assertEquals(actuelResumeResponse.getUserResponse().getId(), 1L);
    }

    @Test
    void deleteResume_WithExistingUser_ShouldPass() {
        // Given
        Resume resume = responseHelper.getResume();

        // When -  action or the behaviour that we are going test
        given(userRepository.findById(1L)).willReturn(Optional.of(resume.getUser()));
        given(repository.findResumeByUser(resume.getUser())).willReturn(resume);
        doNothing().when(repository).delete(any(Resume.class));

        service.deleteResume(1L);

        // Then
        verify(repository, times(1)).findResumeByUser(resume.getUser());
        verify(repository, times(1)).delete(resume);
    }

//    @Test()
//    public void updateResume_WithNonExistingUserId_ShouldThrowException() {
//        // Given
//        long userId = 1L;
//        ResumeRequest request = createResumeRequest();
//        String exceptionMessage = "User with ID " + userId + " not found";
//
//        // When -  action or the behaviour that we are going test
//        given(userRepository.findById(userId)).willReturn(Optional.empty());
//
//        // Then
//        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> service.updateResume(userId, request));
//        assertEquals(exceptionMessage, exception.getMessage());
//    }
}