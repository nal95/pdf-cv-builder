package com.nal.pdfcvbuilder.services;

import com.nal.pdfcvbuilder.DTOs.ResumeRequest;
import com.nal.pdfcvbuilder.DTOs.ResumeResponse;
import com.nal.pdfcvbuilder.entities.Resume;
import com.nal.pdfcvbuilder.entities.ResumeData;
import com.nal.pdfcvbuilder.entities.User;
import com.nal.pdfcvbuilder.pdfCvBuilderExceptions.UserNotFoundException;
import com.nal.pdfcvbuilder.repositories.ResumeRepository;
import com.nal.pdfcvbuilder.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ResumeServiceImpl implements ResumeService {
    private final UserRepository userRepository;
    private final ResumeRepository repository;
    private final ModelMapper modelMapper;

    public ResumeServiceImpl(UserRepository userRepository, ResumeRepository repository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.repository = repository;
        this.modelMapper = modelMapper;
    }


    @Override
    public ResumeResponse createResume(Long userId, ResumeRequest data) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with ID " + userId + " not found"));

        Resume resume = Resume.builder()
                .user(user)
                .data(modelMapper.map(data, ResumeData.class))
                .build();

        resume = repository.save(resume);

        return modelMapper.map(resume, ResumeResponse.class);
    }

    @Override
    public ResumeResponse getResume(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with ID " + userId + " not found"));

        Resume resume = repository.findResumeByUser(user).orElse(new Resume());

        return modelMapper.map(resume, ResumeResponse.class);
    }

    @Override
    public ResumeResponse updateResume(Long userId, ResumeRequest data) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with ID " + userId + " not found"));

        Resume resume = repository.findResumeByUser(user).orElse(new Resume());

        resume.setData(modelMapper.map(data, ResumeData.class));

        resume = repository.save(resume);

        return modelMapper.map(resume, ResumeResponse.class);
    }

    @Override
    public void deleteResume(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with ID " + userId + " not found"));

        Resume resume = repository.findResumeByUser(user).orElse(new Resume());

        repository.delete(resume);
    }
}
