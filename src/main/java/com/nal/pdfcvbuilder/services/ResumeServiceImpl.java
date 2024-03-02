package com.nal.pdfcvbuilder.services;

import com.nal.pdfcvbuilder.DTOs.ResumeRequest;
import com.nal.pdfcvbuilder.DTOs.ResumeResponse;
import com.nal.pdfcvbuilder.repositories.ResumeRepository;
import com.nal.pdfcvbuilder.repositories.UserRepository;
import org.modelmapper.ModelMapper;

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
        return null;
    }

    @Override
    public ResumeResponse getResume(Long userId) {
        return null;
    }

    @Override
    public ResumeResponse updateResume(Long userId, ResumeRequest data) {
        return null;
    }

    @Override
    public void deleteResume(Long userId) {

    }
}
