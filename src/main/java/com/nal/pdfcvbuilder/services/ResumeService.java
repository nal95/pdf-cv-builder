package com.nal.pdfcvbuilder.services;

import com.nal.pdfcvbuilder.DTOs.ResumeRequest;
import com.nal.pdfcvbuilder.DTOs.ResumeResponse;

public interface ResumeService {
    ResumeResponse createResume(Long userId, ResumeRequest data);

    ResumeResponse getResume(Long userId);

    ResumeResponse updateResume(Long userId, ResumeRequest data);

    void deleteResume(Long userId);
}
